package c0.interpreter;

import java.util.Stack;

import c0.ast.ExpressionNode;
import c0.ast.StatementNode;
import c0.util.ExecuteStatementResult;
import c0.util.NodeType;
import c0.util.StackElement;

public abstract class InterpreterImplementation implements Interpreter {
	
	private Stack<StackElement> stack = null; //局所変数、戻り値、戻り先、ベースポインタを積む
	
	public InterpreterImplementation(Stack<StackElement> stack) {
		super();
		this.stack = stack;
	}
	
	/**
	 * 文の処理
	 */
	@Override
	public ExecuteStatementResult executeStatement(StatementNode statementNode) {
		
		ExecuteStatementResult executeStatementResult = null;
		
		switch(statementNode.getNodeType()) {
			case BLOCK_STATEMENT:
				executeStatementResult = this.executeBlockStatement(statementNode);
				break;
			case IF_STATEMENT:
				executeStatementResult = this.executeIfStatement(statementNode);
				break;
			case WHILE_STATEMENT:
				executeStatementResult = this.executeWhileStatement(statementNode);
				break;
			case FOR_STATEMENT:
				executeStatementResult = this.executeForStatement(statementNode);
				break;
			case BREAK_STATEMENT:
				executeStatementResult = this.executeBreakStatement(statementNode);
				break;
			case RETURN_STATEMENT:
				executeStatementResult = this.executeReturnStatement(statementNode);
				break;
			case EXPRESSION_STATEMENT:
				executeStatementResult = this.executeExpressionStatement(statementNode);
				break;
			case EMPTY_STATEMENT:
				executeStatementResult = this.executeEmptyStatement(statementNode);
				break;
		}
		
		return executeStatementResult;
	}

	/**
	 * 複合文
	 */
	@Override
	public ExecuteStatementResult executeBlockStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * if文
	 */
	@Override
	public ExecuteStatementResult executeIfStatement(StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * while文
	 */
	@Override
	public ExecuteStatementResult executeWhileStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * for文
	 */
	@Override
	public ExecuteStatementResult executeForStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * break文
	 */
	@Override
	public ExecuteStatementResult executeBreakStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * return文
	 */
	@Override
	public ExecuteStatementResult executeReturnStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * 式文
	 */
	@Override
	public ExecuteStatementResult executeExpressionStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	/**
	 * 空文
	 */
	@Override
	public ExecuteStatementResult executeEmptyStatement(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ExecuteStatementResult executeParameter(StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public ExecuteStatementResult executeDeclareVariable(
			StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void IntExpression(ExpressionNode integer) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void IdentifierExpression(ExpressionNode identifier) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void StringExpression(ExpressionNode stringLiteral) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void PlusExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void MinusExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void DivExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void ModExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void MulExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void AssignExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void LessThanExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void LessThanOrEqualExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void GreaterThanExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void GreaterThanOrEqualExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void EquivalenceExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void NotEquivalenceExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void LogicalAndExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void LogicalOrExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void UnaryMinusExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void ExclamationExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void PreIncrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void PreDecrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void ArraySubscriptExpression(ExpressionNode arraySubscriptExpression) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * 関数呼び出し
	 */
	@Override
	public void executeFunctionCall(ExpressionNode callNode) {
		
		//呼び出そうとしている関数が標準関数か、ユーザー定義関数かチェックする
		
		//標準関数の呼び出し
		
		//ユーザー定義関数の呼び出し
		
	}
	
	

	@Override
	public void PostIncrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void PostDecrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	/**
	 * ユーザー定義関数の呼び出し
	 * @param callNode
	 */
	private void executeUserDefinedFunctionCall(ExpressionNode callNode) {
		
		//引数をスタックに詰める
		
		//呼び出し元の戻り先を保存する
		
		//複合文（関数本体）の実行
		
		//引数をスタックから取り除く
		
		//スタックに戻り値を詰める
		
		//呼び出し元に戻る
		
	}
	
	/**
	 * 標準関数の呼び出し
	 * @param callNode
	 */
	private void executeStandardFunctionCall(ExpressionNode callNode) {
		
	}
}
