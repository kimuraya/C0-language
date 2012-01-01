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
import c0.ast.ExpressionNode;
import c0.ast.IdentifierNode;
import c0.parser.C0Language;
import c0.parser.ParseException;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.LocalScope;
import c0.util.StackElement;
import c0.util.SymbolTable;

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
		//this.outputSymbolTable(astVisitor);
		
		
		//デバッグ
		Identifier main = globalScope.getGlobalSymbolTable().getSymbol("main");
		
		/*
		if (main.getFunctionNode() != null) {
			System.out.println(main.getFunctionNode().getIdentifier().getName());
			System.out.println(main.getFunctionNode().getIdentifier().getIdentifierType());
		}
		*/
		
		//main関数の呼び出し
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
