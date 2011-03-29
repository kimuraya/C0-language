package c0.interpreter;

import c0.ast.IdentifierNode;
import c0.util.LeftValue;

//環境
//ローカル変数と値を管理する
public class Environment {
	private IdentifierNode localVariable; //ローカル変数
	private LeftValue value; //ローカル変数の値
}
