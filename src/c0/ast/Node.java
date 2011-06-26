package c0.ast;

import c0.util.NodeType;

abstract public class Node {
	
	protected NodeType nodeType; //構文木の種類を表す列挙体
	
	abstract public void dump(int depth, boolean indentFlag); //構文木を表示する
	
	//構文木を出力させる際、字下げを調整する
	public void printIndent(int depth) {
		
		String indent = "	";
		
		//字下げをコンソールに出力
		for( ; depth > 0 ; depth--) {
			System.out.print(indent);
		}
	}
}
