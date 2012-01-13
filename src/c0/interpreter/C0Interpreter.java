package c0.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import c0.ast.AstNode;
import c0.ast.CallNode;
import c0.ast.DataTypeNode;
import c0.ast.DeclareVariableNode;
import c0.ast.ExpressionNode;
import c0.ast.IdentifierNode;
import c0.parser.C0Language;
import c0.parser.ParseException;
import c0.util.DataType;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;
import c0.util.LocalScope;
import c0.util.StackElement;
import c0.util.StackElementType;
import c0.util.SymbolTable;
import c0.util.Value;

/**
 * ASTのノードを入力として受け取り、関数を実行する。
 * シンボルテーブルや環境を管理する。
 */
public class C0Interpreter extends InterpreterImplementation {
	
	public C0Interpreter(Stack<StackElement> callStack,
			Stack<StackElement> operandStack, GlobalScope globalScope) {
		super(callStack, operandStack, globalScope);
	}
	
	/**
	 * 引数とファイルのチェックを行い、問題がなければインタプリタを実行する
	 * @param args
	 */
	public static void main(String args[]) {
		
		//mainメソッドの引数をチェック。ファイル名が無ければ、警告を出して終了
		String fileName = null;
		FileReader fileReader = null;
		try {
			
			//引数チェック
			if (args.length >= 1) {
				fileName = args[0];
			} else {
				throw new Exception("引数にファイル名が指定されていません");
			}
			
			File file = null;
			if (fileName != null) {
				file = new File(fileName);
			}
			
			if (file.exists() && file.canRead()) {
				fileReader = new FileReader(fileName);
			} else if (!file.exists()) {
				throw new Exception("ファイルが存在しません");
			} else if (!file.canRead()) {
				throw new Exception("ファイルが読み込めません");
			}
			
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//インタプリタのエントリーポイント
		Stack<StackElement> callStack = new Stack<StackElement>();
		Stack<StackElement> operandStack = new Stack<StackElement>();
		GlobalScope globalScope = new GlobalScope();
		
		C0Interpreter interpreter = new C0Interpreter(callStack, operandStack, globalScope);
		interpreter.interpretation(fileName, fileReader); //実行
	}
	
	/**
	 * インタプリタの実行
	 * @param fileName
	 * @param fileReader
	 */
	private void interpretation(String fileName, FileReader fileReader) {
		
		AstNode program = null; //構文木
		
		//ファイルを渡して、パーサを実行
		C0Language parser = new C0Language(fileReader);
		parser.setFileName(fileName);
		
		//parser.enable_tracing(); //パーサのトレース機能を開始
		
		//構文木の出力
		try {			
			program = parser.file();
			int depth = 0;
			program.dump(depth, true);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//シンボルテーブル
		GlobalScope globalScope = this.getGlobalScope();
		
		//標準関数をシンボルテーブルに登録する
		StandardFunction standardFunction = new StandardFunction();
		standardFunction.addSymbolTable(globalScope);
		
		//Visitorを準備する
		AstVisitor astVisitor = new AstVisitor(globalScope);
		
		//シンボルテーブルの作成
		program.accept(astVisitor);
		
		//シンボルテーブルの出力
		this.outputSymbolTable(astVisitor);
		
		/*
		if (main.getFunctionNode() != null) {
			System.out.println(main.getFunctionNode().getIdentifier().getName());
			System.out.println(main.getFunctionNode().getIdentifier().getIdentifierType());
		}
		*/
		
		//グローバル変数の初期化
		List<DeclareVariableNode> getGlobalVariables = program.getGlobalVariables();
		
		for (DeclareVariableNode globalVariable : getGlobalVariables) {
			
			IdentifierNode identifierNode = globalVariable.getIdentifier();
			Identifier search = identifierNode.getIdentifier(); //この識別子を探す
			boolean searchFlag = false; //識別子が見つかったかどうかを表す。trueなら見つかっている
			Identifier foundGlobalVariable = null;
			DataTypeNode globalVariableDataTypeNode = globalVariable.getDataType();
			DataType globalVariableDataType = globalVariableDataTypeNode.getDataType();
			
			Value value = new Value();
			
			//配列以外の変数の初期化
			if (globalVariable.getExpression() != null && (globalVariableDataType != DataType.INT_ARRAY || globalVariableDataType != DataType.BOOLEAN_ARRAY)) {
				
				//初期化式の実行
				ExpressionNode expression = globalVariable.getExpression();
				this.evaluateExpression(expression);
				StackElement result = this.operandStack.pop();
				value = result.getValue();
				
			//配列の初期化
			//配列の初期化式は認めない。要素数のない配列は作れない
			} else if (globalVariable.getExpression() == null && globalVariableDataTypeNode.getElementNumber() != null &&
					(globalVariableDataType == DataType.INT_ARRAY || globalVariableDataType == DataType.BOOLEAN_ARRAY)) {
				
				//要素数の計算
				//TODO 結果が整数でなければ、例外を投げる
				Value elementNumberValue = new Value();
				ExpressionNode elementNumberExpression = globalVariableDataTypeNode.getElementNumber();
				this.evaluateExpression(elementNumberExpression);
				StackElement result = this.operandStack.pop();
				elementNumberValue = result.getValue();
				
				//配列の生成
				int elementNumber = elementNumberValue.getInteger();
				
				int intArray[];
				boolean  boolArray[];
				if (globalVariableDataType == DataType.INT_ARRAY && elementNumber > 0) {
					
					intArray = new int[elementNumber];
					value.setDataType(DataType.INT_ARRAY);
					value.setIntegerArray(intArray);
					
				} else if (globalVariableDataType == DataType.BOOLEAN_ARRAY && elementNumber > 0) {
					
					boolArray = new boolean[elementNumber];
					value.setDataType(DataType.BOOLEAN_ARRAY);
					value.setBooleanArray(boolArray);
					
				} else if (elementNumber <= 0) {
					//TODO 配列の要素数が0か、0より小さい場合は例外を投げる
				}
			}
			
			//シンボルテーブルの検索
			SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();
			
			if (globalSymbolTable.searchSymbol(search.getName())) {
				searchFlag = true;
				foundGlobalVariable = globalSymbolTable.getSymbol(search.getName());
			}
			
			//計算結果をグローバル変数に代入する
			if (searchFlag) {
				
				//見つかった識別子に対し、代入を実行する
				if (foundGlobalVariable != null) {
					foundGlobalVariable.setLeftValue(value);
				}
				
			} else {
				//識別子がグローバル変数に存在しない場合、例外を投げる
			}
		}
		
		//main関数の引数の処理
		
		//main関数の呼び出し
		Identifier main = globalScope.getGlobalSymbolTable().getSymbol("main");
		IdentifierNode mainFunction = main.getFunctionNode();
		List<ExpressionNode> arguments = new LinkedList<ExpressionNode>();
		this.executeFunctionCall(new CallNode(mainFunction, arguments));
	}
	
	/**
	 * シンボルテーブルの出力
	 * @param astVisitor
	 */
	private void outputSymbolTable(AstVisitor astVisitor) {
		
		System.out.println("シンボルテーブルの出力");
		GlobalScope outputScope = astVisitor.getGlobalScope();
		
		System.out.println("グローバル領域の出力");
		SymbolTable globalSymbolTable = outputScope.getGlobalSymbolTable();
		for (Identifier identifier : globalSymbolTable.getSymbolTable()) {
			System.out.print(identifier.getName() + " ");
		}
		System.out.print("\n");
		
		System.out.println("各スコープとローカル変数の出力");
		for (LocalScope localScope : outputScope.getFunctionScopeList()) {
			System.out.println(localScope.getFunctionName());
			for (SymbolTable localSymbolTable : localScope.getLocalSymbolTableList()) {
				for (Identifier identifier : localSymbolTable.getSymbolTable()) {
					System.out.print(identifier.getName() + " ");
				}
			}
			System.out.println("\n");
		}
		
		return;
	}
}
