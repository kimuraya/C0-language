package c0.interpreter;

import java.util.List;

import c0.ast.IdentifierNode;

//ASTのノードを入力として受け取り、関数を実行する。
//シンボルテーブルや環境を管理する
public class Interpreter {
	private List<IdentifierNode> symbolTable; //シンボルテーブル
	private List<Environment> interpreterEnvironment; //環境
}
