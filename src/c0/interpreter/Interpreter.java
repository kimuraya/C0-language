package c0.interpreter;

import c0.ast.ExpressionNode;
import c0.ast.StatementNode;
import c0.util.ExecuteStatementResult;
import c0.util.NodeType;
import c0.util.Value;

public interface Interpreter {
	
	//文の処理
	ExecuteStatementResult executeStatement(StatementNode statementNode) throws InterpreterRuntimeException; //文の実行
	ExecuteStatementResult executeBlockStatement(StatementNode statementNode) throws InterpreterRuntimeException; //複合文
	ExecuteStatementResult executeIfStatement(StatementNode statementNode) throws InterpreterRuntimeException; //if文
	ExecuteStatementResult executeWhileStatement(StatementNode statementNode) throws InterpreterRuntimeException; //while文
	ExecuteStatementResult executeForStatement(StatementNode statementNode) throws InterpreterRuntimeException; //for文
	ExecuteStatementResult executeBreakStatement(StatementNode statementNode); //break文
	ExecuteStatementResult executeReturnStatement(StatementNode statementNode) throws InterpreterRuntimeException; //return文
	ExecuteStatementResult executeExpressionStatement(StatementNode statementNode) throws InterpreterRuntimeException; //式文
	ExecuteStatementResult executeEmptyStatement(StatementNode statementNode); //空文
	ExecuteStatementResult executeParameter(StatementNode statementNode); //引数
	
	//式の処理
	
	//一次式
	void intLiteralExpression(ExpressionNode integer); //整数定数
	void stringLiteralExpression(ExpressionNode string); //文字列定数
	void booleanLiteralExpression(ExpressionNode bool); //真偽値定数
	void identifierExpression(ExpressionNode identifier) throws InterpreterRuntimeException;//識別子
	
	//二項演算子
	void binaryOperatorExpressionInit(ExpressionNode expression) throws InterpreterRuntimeException; //二項演算子の前処理
	void binaryOperatorExpression(int left, int right, NodeType expressionType); //整数の加算式, 減算式, 乗算, 除算式, 剰余式, 小なり比較演算子, 以下比較演算子, 大なり比較演算子, 以上比較演算子
	void binaryOperatorExpression(boolean left, boolean right, NodeType expressionType); //同等演算子, 不等演算子
	void assignExpression(ExpressionNode expression) throws InterpreterRuntimeException; //代入式
	
	//論理式
	void logicalOperatorExpression(ExpressionNode expression) throws InterpreterRuntimeException; //条件積演算子, 条件和演算子
	
	//単項式
	void unaryOperatorExpressionInit(ExpressionNode expression) throws InterpreterRuntimeException; //単項演算子の前処理
	void unaryOperatorExpression(ExpressionNode expression, int left, NodeType expressionType) throws InterpreterRuntimeException; //単項マイナス式, 前置増分, 前置減分, 後置増分, 後置減分
	public void unaryOperatorExpression(boolean left, NodeType expressionType); //論理否定演算子
	
	//後置き式
	void arraySubscriptExpression(ExpressionNode arraySubscriptExpression) throws InterpreterRuntimeException; //添字式
	void executeFunctionCall(ExpressionNode callNode) throws InterpreterRuntimeException; //関数呼び出し（標準関数を含む）
	
	//式の実行
	void evaluateExpression(ExpressionNode expression) throws InterpreterRuntimeException;
}
