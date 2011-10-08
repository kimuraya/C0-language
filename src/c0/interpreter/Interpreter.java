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
	void IntExpression(ExpressionNode integer); //整数定数
	void IdentifierExpression(ExpressionNode identifier);//識別子
	void StringExpression(ExpressionNode stringLiteral); //文字列定数
	
	//二項演算子
	void PlusExpression(ExpressionNode left, ExpressionNode right); //加算式
	void MinusExpression(ExpressionNode left, ExpressionNode right); //減算式
	void DivExpression(ExpressionNode left, ExpressionNode right); //除算式
	void ModExpression(ExpressionNode left, ExpressionNode right); //剰余式
	void MulExpression(ExpressionNode left, ExpressionNode right); //乗算式
	void AssignExpression(ExpressionNode left, ExpressionNode right); //代入式
	
	//関係式
	void LessThanExpression(ExpressionNode left, ExpressionNode right); //小なり比較演算子
	void LessThanOrEqualExpression(ExpressionNode left, ExpressionNode right); //以下比較演算子
	void GreaterThanExpression(ExpressionNode left, ExpressionNode right); //大なり比較演算子
	void GreaterThanOrEqualExpression(ExpressionNode left, ExpressionNode right); //以上比較演算子
	
	//同等式
	void EquivalenceExpression(ExpressionNode left, ExpressionNode right); //同等演算子
	void NotEquivalenceExpression(ExpressionNode left, ExpressionNode right); //不等演算子
	
	//論理式
	void LogicalAndExpression(ExpressionNode left, ExpressionNode right); //条件積演算子
	void LogicalOrExpression(ExpressionNode left, ExpressionNode right); //条件和演算子
	
	//単項式
	void UnaryMinusExpression(ExpressionNode leftValue); //単項マイナス式
	void ExclamationExpression(ExpressionNode leftValue); //論理否定演算子
	void PreIncrementExpression(ExpressionNode leftValue); //前置増分式
	void PreDecrementExpression(ExpressionNode leftValue); //前置減分式
	
	//後置き式
	void ArraySubscriptExpression(ExpressionNode arraySubscriptExpression); //添字式
	void executeFunctionCall(ExpressionNode callNode); //関数呼び出し（ネイティブ関数を含む）
	void PostIncrementExpression(ExpressionNode leftValue); //後置増分式
	void PostDecrementExpression(ExpressionNode leftValue); //後置減分式
}
