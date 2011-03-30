package c0.interpreter;

import java.util.List;
import c0.util.Identifier;

//ASTのノードを入力として受け取り、関数を実行する。
//シンボルテーブルや環境を管理する
public class Interpreter {
	private List<Identifier> symbolTable; //シンボルテーブル
	private List<Environment> interpreterEnvironment; //環境
}
