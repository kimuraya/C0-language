package c0.ast;

import c0.util.NodeType;

abstract public class Node {
	protected NodeType nodeType; //構文木の種類を表す列挙体
	
	abstract public void dump(); //構文木を表示する
}
