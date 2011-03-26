package c0.interpreter;

import c0.ast.IdentifierNode;

//環境
//ローカル変数と値を管理する
public class Environment {
	private IdentifierNode localVariable; //ローカル変数
	int value;
}
