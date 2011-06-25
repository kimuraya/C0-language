package c0.interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import c0.ast.AstNode;
import c0.parser.C0Language;
import c0.parser.ParseException;
import c0.util.Identifier;

//ASTのノードを入力として受け取り、関数を実行する。
//シンボルテーブルや環境を管理する
public class Interpreter {
	private List<Identifier> symbolTable; //シンボルテーブル
	private List<Environment> interpreterEnvironment; //環境
	
	public static void main(String args[]) {
		//インタプリタのエントリーポイント
		String fileName = null;
		FileReader fileReader = null;
		AstNode program = null;
		
		//mainメソッドの引数をチェック。ファイル名が無ければ、警告を出して終了
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
		
		//ファイルを渡して、パーサを実行
		C0Language parser = new C0Language(fileReader);
		
		//parser.enable_tracing(); //パーサのトレース機能を開始
		
		//構文木の出力
		try {			
			program = parser.file();
			int depth = 0;
			program.dump(depth);
		} catch (ParseException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
		
		//インタプリタが構文木を入力として受け取り、プログラムを実行する
	}
}
