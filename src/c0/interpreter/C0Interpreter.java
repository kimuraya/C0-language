package c0.interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import c0.ast.AstNode;
import c0.parser.C0Language;
import c0.parser.ParseException;
import c0.util.Identifier;
import c0.util.StackElement;
import c0.util.SymbolTable;

//ASTのノードを入力として受け取り、関数を実行する。
//シンボルテーブルや環境を管理する
public class C0Interpreter {

	//private SymbolTable symbolTable = null; //シンボルテーブル
	private Stack<StackElement> stack = null; //局所変数、戻り値、戻り先、ベースポインタを積む
	
	public static void main(String args[]) {
		
		//mainメソッドの引数をチェック。ファイル名が無ければ、警告を出して終了
		//例外を使わないコードに書き換える
		String fileName = null;
		FileReader fileReader = null;
		
		try {
			fileName = args[0];
			fileReader = new FileReader(fileName);
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {
			System.out.println("引数にファイル名が指定されていません");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		} catch (FileNotFoundException e) {
			System.out.println("ファイルが開けません");
			System.out.println(e.getMessage());
			e.printStackTrace();
			return;
		}
		
		//インタプリタのエントリーポイント
		C0Interpreter interpreter = new C0Interpreter();
		interpreter.init(); //初期処理
		interpreter.interpretation(fileName, fileReader); //実行
	}
	
	//インタプリタの初期処理
	private void init() {
		//this.symbolTable = new SymbolTable(); //シンボルテーブル
		this.stack = new Stack<StackElement>(); //局所変数、戻り値、戻り先、ベースポインタを積む
	}
	
	//インタプリタの実行
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
		
		//Visitorを準備する
		AstVisitor astVisitor = new AstVisitor();
		
		//シンボルテーブル
		LinkedList<SymbolTable> symbolTableList = new LinkedList<SymbolTable>();
		symbolTableList.add(new SymbolTable());
		
		astVisitor.setSymbolTableList(symbolTableList);
		
		program.accept(astVisitor);
		
		LinkedList<SymbolTable> resultList = astVisitor.getSymbolTableList();
		
		System.out.println("シンボルテーブルの出力");
		
		for (SymbolTable symbolTable : resultList) {
			System.out.print("[");
			for (Identifier identifier : symbolTable.getSymbolTable()) {
				System.out.print(identifier.getName() + " ");
			}
			System.out.print("]");
		}
		
		//System.out.println(this.symbolTable.getSymbolTable());
		
		//インタプリタが構文木を入力として受け取り、プログラムを実行する
	}
}
