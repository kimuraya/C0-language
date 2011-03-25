package c0.ast;

abstract public class StatementNode extends Node {
	abstract public void execute(); //文を実行する
	abstract public void dump(); //構文木を表示する
}
