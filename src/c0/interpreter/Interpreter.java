package c0.interpreter;

public interface Interpreter {
	
	//関数呼び出し
	void executeFunctionCall();
	
	//文の処理
	void executeStatement(); //複合文の中で使用する
	void executeBlockStatement();
	void executeIfStatement();
	void executeWhileStatement();
	void executeForStatement();
	void executeBreakStatement();
	void executeReturnStatement();
	void executeExpressionStatement();
	
	//式文の中で使用する
	
	
}
