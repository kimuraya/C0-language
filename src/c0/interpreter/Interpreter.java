package c0.interpreter;

import java.util.List;

import c0.ast.ExpressionNode;
import c0.ast.StatementNode;
import c0.util.ExecuteStatementResult;

public interface Interpreter {
	
	//文の処理
	ExecuteStatementResult executeStatement(StatementNode statementNode);
	ExecuteStatementResult executeBlockStatement(StatementNode statementNode); //複合文
	ExecuteStatementResult executeIfStatement(StatementNode statementNode); //if文
	ExecuteStatementResult executeWhileStatement(StatementNode statementNode); //while文
	ExecuteStatementResult executeForStatement(StatementNode statementNode); //for文
	ExecuteStatementResult executeBreakStatement(StatementNode statementNode); //break文
	ExecuteStatementResult executeReturnStatement(StatementNode statementNode); //return文
	ExecuteStatementResult executeExpressionStatement(StatementNode statementNode); //式文
	ExecuteStatementResult executeEmptyStatement(StatementNode statementNode); //空文
	ExecuteStatementResult executeParameter(StatementNode statementNode); //引数
	ExecuteStatementResult executeDeclareVariable(StatementNode statementNode); //変数宣言
	
	//式の処理
	
	//一次式
	void intLiteralExpression(ExpressionNode integer); //整数定数
	void stringLiteralExpression(ExpressionNode string); //文字列定数
	void booleanLiteralExpression(ExpressionNode bool); //真偽値定数
	void identifierExpression(ExpressionNode identifier);//識別子
	
	//二項演算子
	void plusExpression(ExpressionNode left, ExpressionNode right); //加算式
	void minusExpression(ExpressionNode left, ExpressionNode right); //減算式
	void divExpression(ExpressionNode left, ExpressionNode right); //除算式
	void modExpression(ExpressionNode left, ExpressionNode right); //剰余式
	void mulExpression(ExpressionNode left, ExpressionNode right); //乗算式
	void assignExpression(ExpressionNode left, ExpressionNode right); //代入式
	
	//関係式
	void lessThanExpression(ExpressionNode left, ExpressionNode right); //小なり比較演算子
	void lessThanOrEqualExpression(ExpressionNode left, ExpressionNode right); //以下比較演算子
	void greaterThanExpression(ExpressionNode left, ExpressionNode right); //大なり比較演算子
	void greaterThanOrEqualExpression(ExpressionNode left, ExpressionNode right); //以上比較演算子
	
	//同等式
	void equivalenceExpression(ExpressionNode left, ExpressionNode right); //同等演算子
	void notEquivalenceExpression(ExpressionNode left, ExpressionNode right); //不等演算子
	
	//論理式
	void logicalAndExpression(ExpressionNode left, ExpressionNode right); //条件積演算子
	void logicalOrExpression(ExpressionNode left, ExpressionNode right); //条件和演算子
	
	//単項式
	void unaryMinusExpression(ExpressionNode leftValue); //単項マイナス式
	void exclamationExpression(ExpressionNode leftValue); //論理否定演算子
	void preIncrementExpression(ExpressionNode leftValue); //前置増分式
	void preDecrementExpression(ExpressionNode leftValue); //前置減分式
	
	//後置き式
	void arraySubscriptExpression(ExpressionNode arraySubscriptExpression); //添字式
	void executeFunctionCall(ExpressionNode callNode); //関数呼び出し（ネイティブ関数を含む）
	void postIncrementExpression(ExpressionNode leftValue); //後置増分式
	void postDecrementExpression(ExpressionNode leftValue); //後置減分式
	
	//式の実行
	void evaluateExpression(ExpressionNode expression);
}
