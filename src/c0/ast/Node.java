package c0.ast;

import c0.interpreter.Visitor;

abstract public class Node {
	
	protected Location location; //ソースファイルの名前とTokenオブジェクトを保持する
	
	abstract public void dump(int depth, boolean indentFlag); //構文木を表示する
	
	//構文木を出力させる際、字下げを調整する
	public void printIndent(int depth) {
		
		String indent = "	";
		
		//字下げをコンソールに出力
		for( ; depth > 0 ; depth--) {
			System.out.print(indent);
		}
	}
	
	//ファイル名と行数をコンソールに出力する
	public void printFileNameAndLine(int depth, boolean indentFlag) {
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("file : " + this.location.getFileName());
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("line : " + location.getToken().beginLine);
	}
	
	//訪問者の受け入れ
	abstract public void accept(Visitor visitor);
}
