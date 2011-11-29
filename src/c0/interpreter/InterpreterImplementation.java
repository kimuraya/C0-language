package c0.interpreter;

import java.util.List;
import java.util.Stack;

import c0.ast.AssignNode;
import c0.ast.CallNode;
import c0.ast.DivNode;
import c0.ast.EquivalenceNode;
import c0.ast.ExclamationNode;
import c0.ast.ExpressionNode;
import c0.ast.ExpressionStatementNode;
import c0.ast.GreaterThanNode;
import c0.ast.GreaterThanOrEqualNode;
import c0.ast.IdentifierNode;
import c0.ast.IfNode;
import c0.ast.LessThanNode;
import c0.ast.LessThanOrEqualNode;
import c0.ast.LiteralNode;
import c0.ast.LogicalAndNode;
import c0.ast.LogicalOrNode;
import c0.ast.MinusNode;
import c0.ast.ModNode;
import c0.ast.MulNode;
import c0.ast.NotEquivalenceNode;
import c0.ast.ParameterNode;
import c0.ast.PlusNode;
import c0.ast.PostDecrementNode;
import c0.ast.PostIncrementNode;
import c0.ast.PreDecrementNode;
import c0.ast.PreIncrementNode;
import c0.ast.StatementNode;
import c0.ast.UnaryMinusNode;
import c0.util.DataType;
import c0.util.ExecuteStatementResult;
import c0.util.Identifier;
import c0.util.LocalVariable;
import c0.util.NodeType;
import c0.util.StackElement;
import c0.util.StackElementType;
import c0.util.Value;

public class InterpreterImplementation implements Interpreter {
	
	private Stack<StackElement> callStack = null; //局所変数、戻り値、戻り先、ベースポインタを積む
	private Stack<StackElement> operandStack = null; //式の計算に使用する
	
	public InterpreterImplementation(Stack<StackElement> callStack,
			Stack<StackElement> operandStack) {
		super();
		this.callStack = callStack;
		this.operandStack = operandStack;
	}

	public Stack<StackElement> getCallStack() {
		return callStack;
	}

	public void setCallStack(Stack<StackElement> callStack) {
		this.callStack = callStack;
	}

	public Stack<StackElement> getOperandStack() {
		return operandStack;
	}

	public void setOperandStack(Stack<StackElement> operandStack) {
		this.operandStack = operandStack;
	}

	/**
	 * 文の処理
	 * StatementResultFlagがBREAK_STATEMENT_RESULTになっている場合、ループの処理を終了する。
	 * ループの終了後、StatementResultFlagをNORMAL_STATEMENT_RESULTに戻す。
	 * StatementResultFlagがRETURN_STATEMENT_RESULTになっている場合、文の処理を行わない。
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
		
		//局所変数をスタックに詰める
		
		//文を実行する
		
		//局所変数をスタックから破棄する
		
		return null;
	}

	/**
	 * if文
	 */
	@Override
	public ExecuteStatementResult executeIfStatement(StatementNode statementNode) {
		
		IfNode ifNode = (IfNode) statementNode;
		
		//条件式を取り出し、実行する
		
		//trueならthenを実行する
		
		//falseならelseを実行する
		
		return null;
	}

	/**
	 * while文
	 */
	@Override
	public ExecuteStatementResult executeWhileStatement(
			StatementNode statementNode) {
		
		//条件式を取り出し、実行する
		
		//trueの場合、文を実行する
		
		//StatementResultFlagがBREAK_STATEMENT_RESULTになっている場合、ループの処理を終了する。
		//ループの終了後、StatementResultFlagをNORMAL_STATEMENT_RESULTに戻す。
		
		return null;
	}

	/**
	 * for文
	 */
	@Override
	public ExecuteStatementResult executeForStatement(
			StatementNode statementNode) {
		
		//初期化式を実行
		
		//条件式を実行
		
		//文を実行
		
		//後置き式を実行
		
		//条件式がtrueである限り、ループが継続する
		
		//StatementResultFlagがBREAK_STATEMENT_RESULTになっている場合、ループの処理を終了する。
		//ループの終了後、StatementResultFlagをNORMAL_STATEMENT_RESULTに戻す。
		
		return null;
	}

	/**
	 * break文
	 */
	@Override
	public ExecuteStatementResult executeBreakStatement(
			StatementNode statementNode) {
		
		//StatementResultFlagをBREAK_STATEMENT_RESULTにする
		
		return null;
	}

	/**
	 * return文
	 */
	@Override
	public ExecuteStatementResult executeReturnStatement(
			StatementNode statementNode) {
		
		//StatementResultFlagをRETURN_STATEMENT_RESULTにする
		
		return null;
	}

	/**
	 * 式文
	 */
	@Override
	public ExecuteStatementResult executeExpressionStatement(
			StatementNode statementNode) {
		
		//式を実行する
		ExpressionStatementNode expressionStatementNode = (ExpressionStatementNode) statementNode;
		this.evaluateExpression(expressionStatementNode.getExpression());
		
		//ExecuteStatementResultを作る
		//スタックから値を取り出す
		
		return null;
	}

	/**
	 * 空文
	 */
	@Override
	public ExecuteStatementResult executeEmptyStatement(
			StatementNode statementNode) {
		
		//何も実行しない
		
		return null;
	}

	@Override
	public ExecuteStatementResult executeParameter(StatementNode statementNode) {
		
		//引数の値を返す
		
		return null;
	}

	@Override
	public ExecuteStatementResult executeDeclareVariable(
			StatementNode statementNode) {
		
		//変数宣言
		
		//初期化式がある場合、代入を行う
		
		return null;
	}

	/**
	 * 10進定数
	 */
	@Override
	public void intLiteralExpression(ExpressionNode integer) {
		
		//ノードから定数を取り出す
		LiteralNode integerLiteral = (LiteralNode) integer;
		
		//スタックの要素の作製
		StackElement stackElement = new StackElement();
		stackElement.setStackElementType(StackElementType.Literal);
		stackElement.setValue(integerLiteral.getLiteral());
		
		//オペランドスタックに値を詰める
		this.operandStack.add(stackElement);
		
		return;
	}

	/**
	 * 文字列定数
	 */
	@Override
	public void stringLiteralExpression(ExpressionNode string) {
		
		//ノードから定数を取り出す
		LiteralNode integerLiteral = (LiteralNode) string;
		
		//オペランドスタックに値を詰める
		
	}

	/**
	 * 真偽値定数
	 */
	@Override
	public void booleanLiteralExpression(ExpressionNode bool) {
		
		//ノードから定数を取り出す
		
		//オペランドスタックに値を詰める
		
	}

	/**
	 * 識別子
	 */
	@Override
	public void identifierExpression(ExpressionNode identifier) {
		
		//コールスタックから識別子を探す
		
		//グローバル変数から識別子を探す
		
		//識別子が存在すれば、値を取り出し、オペランドスタックに詰める

	}
	
	/**
	 * 二項演算子の前処理。データ型のチェック
	 * @param expression
	 */
	public void binaryOperatorExpressionInit(ExpressionNode expression) {
		
		ExpressionNode left = null;
		ExpressionNode right = null;
		
		//ノードの種類によって、処理を分ける
		switch(expression.getNodeType()) {
			
			case EQUIVALENCE: //"=="
				EquivalenceNode equivalenceNode = (EquivalenceNode) expression;
				left = equivalenceNode.getLeft();
				right = equivalenceNode.getRight();
				break;
			case NOT_EQUIVALENCE: //"!="
				NotEquivalenceNode notEquivalenceNode = (NotEquivalenceNode) expression;
				left = notEquivalenceNode.getLeft();
				right = notEquivalenceNode.getRight();
				break;
			case LESS_THAN: //"<"
				LessThanNode lessThanNode = (LessThanNode) expression;
				left = lessThanNode.getLeft();
				right = lessThanNode.getRight();
				break;
			case LESS_THAN_OR_EQUAL: //"<="
				LessThanOrEqualNode lessThanOrEqualNode = (LessThanOrEqualNode) expression;
				left = lessThanOrEqualNode.getLeft();
				right = lessThanOrEqualNode.getRight();
				break;
			case GREATER_THAN: //">"
				GreaterThanNode greaterThanNode = (GreaterThanNode) expression;
				left = greaterThanNode.getLeft();
				right = greaterThanNode.getRight();
				break;
			case GREATER_THAN_OR_EQUAL: //">="
				GreaterThanOrEqualNode greaterThanOrEqualNode = (GreaterThanOrEqualNode) expression;
				left = greaterThanOrEqualNode.getLeft();
				right = greaterThanOrEqualNode.getRight();
				break;
			case PLUS: //"+"
				PlusNode plusNode = (PlusNode) expression;
				left = plusNode.getLeft();
				right = plusNode.getRight();
				break;
			case MINUS: //"-"
				MinusNode minusNode = (MinusNode) expression;
				left = minusNode.getLeft();
				right = minusNode.getRight();
				break;
			case MUL: //"*"
				MulNode mulNode = (MulNode) expression;
				left = mulNode.getLeft();
				right = mulNode.getRight();
				break;
			case DIV: //"/"
				DivNode divNode = (DivNode) expression;
				left = divNode.getLeft();
				right = divNode.getRight();
				break;
			case MOD: //"%"
				ModNode modNode = (ModNode) expression;
				left = modNode.getLeft();
				right = modNode.getRight();
				break;
		}
		
		//左右の値の式を実行する
		this.evaluateExpression(left);
		this.evaluateExpression(right);
		
		//オペランドスタックから左右の値の計算結果を取り出す
		StackElement stackRight = this.operandStack.pop();
		StackElement stackLeft = this.operandStack.pop();
		
		// TODO この部分は要修正
		// TODO スタックの要素から値を取り出す前にデータ型のチェックする処理を追加する
		Value leftValue = stackLeft.getValue();
		Value rightValue = stackRight.getValue();
		
		//TODO データ型のチェック
		//整数の式
		if ((leftValue.getDataType() == DataType.INT) && (rightValue.getDataType() == DataType.INT)) {
			
			this.integerBinaryOperatorExpression(leftValue.getInteger(), rightValue.getInteger(), expression.getNodeType());
			
		//真偽値の式
		} else if ((leftValue.getDataType() == DataType.BOOLEAN) && (rightValue.getDataType() == DataType.BOOLEAN)) {
			
			this.booleanBinaryOperatorExpression(leftValue.isBooleanLiteral(), rightValue.isBooleanLiteral(), expression.getNodeType());
			
		} else {
			//TODO データ型のチェックに引っかからなかった場合
			//TODO エラーが起こった行数を保存して、例外を投げる
			
		}
		
		
	}
	
	/**
	 * 二項演算子の式
	 * 加算式, 減算式, 乗算, 除算式, 剰余式
	 * 小なり比較演算子, 以下比較演算子, 大なり比較演算子, 以上比較演算子
	 * +, -, *, /, %
	 * @param left
	 * @param right
	 * @param expressionType
	 */
	public void integerBinaryOperatorExpression(int left, int right, NodeType expressionType) {
		
		//式を実行する
		int resultInt = 0;
		boolean resultBool = false;
		boolean operationResultInt = false; //演算結果が整数
		boolean operationResultBool = false; //演算結果が真偽値
		
		//ノードの種類によって、処理を分ける
		switch(expressionType) {
			
			case PLUS: //"+"
				resultInt = left + right;
				operationResultInt = true;
				break;
			case MINUS: //"-"
				resultInt = left - right;
				operationResultInt = true;
				break;
			case MUL: //"*"
				resultInt = left * right;
				operationResultInt = true;
				break;
			case DIV: //"/"
				resultInt = left / right;
				operationResultInt = true;
				break;
			case MOD: //"%"
				resultInt = left % right;
				operationResultInt = true;
				break;
			case LESS_THAN: //"<"
				resultBool = left < right;
				operationResultBool = true;
				break;
			case LESS_THAN_OR_EQUAL: //"<="
				resultBool = left <= right;
				operationResultBool = true;
				break;
			case GREATER_THAN: //">"
				resultBool = left > right;
				operationResultBool = true;
				break;
			case GREATER_THAN_OR_EQUAL: //">="
				resultBool = left >= right;
				operationResultBool = true;
				break;
		}
		
		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();
		
		if (operationResultInt) {
			resultValue.setInteger(resultInt);
			resultValue.setDataType(DataType.INT);
		} else if (operationResultBool) {
			resultValue.setBooleanLiteral(resultBool);
			resultValue.setDataType(DataType.BOOLEAN);
		}
		
		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);
		
		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);
		
		return;
	}
	
	/**
	 * 二項演算子の式
	 * 同等演算子, 不等演算子
	 * @param left
	 * @param right
	 * @param expressionType
	 */
	public void booleanBinaryOperatorExpression(boolean left, boolean right, NodeType expressionType) {
		//式を実行する
		boolean result = false;
		
		//ノードの種類によって、処理を分ける
		switch(expressionType) {
			
			case EQUIVALENCE: //"=="
				result = left == right;
				break;
			case NOT_EQUIVALENCE: //"!="
				result = left != right;
				break;
		}
		
		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();
		resultValue.setBooleanLiteral(result);
		resultValue.setDataType(DataType.BOOLEAN);
		
		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);
		
		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);
		
		return;
	}

	/**
	 * "="
	 */
	@Override
	public void assignExpression(ExpressionNode left, ExpressionNode right) {
		
		//式(right)を実行する
		
		//オペランドスタックから値を取り出す
		
		//コールスタックから代入先を探す（局所変数）
		
		//シンボルテーブルから代入先を探す（大域変数）
		
		//代入を実行する

	}

	/**
	 * "<"
	 */
	@Override
	public void lessThanExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "<="
	 */
	@Override
	public void lessThanOrEqualExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * ">"
	 */
	@Override
	public void greaterThanExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * ">="
	 */
	@Override
	public void greaterThanOrEqualExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "=="
	 */
	@Override
	public void equivalenceExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "!="
	 */
	@Override
	public void notEquivalenceExpression(ExpressionNode left,
			ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "&&"
	 */
	@Override
	public void logicalAndExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "||"
	 */
	@Override
	public void logicalOrExpression(ExpressionNode left, ExpressionNode right) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "-" 単項マイナス式
	 */
	@Override
	public void unaryMinusExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "!"
	 */
	@Override
	public void exclamationExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "++" 前置増分
	 */
	@Override
	public void preIncrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "--" 前置減分
	 */
	@Override
	public void preDecrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "++" 後置増分
	 */
	@Override
	public void postIncrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * "--" 後置減分
	 */
	@Override
	public void postDecrementExpression(ExpressionNode leftValue) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * 添字式
	 */
	@Override
	public void arraySubscriptExpression(ExpressionNode arraySubscriptExpression) {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * 式の実行
	 */
	@Override
	public void evaluateExpression(ExpressionNode expression) {
		
		switch(expression.getNodeType()) {
		
			case INT_LITERAL: //10進定数
				this.intLiteralExpression(expression);
				break;
			case STRING_LITERAL: //文字列定数
				this.stringLiteralExpression(expression);
				break;
			case BOOLEAN_LITERAL: //真偽値定数
				this.booleanLiteralExpression(expression);
				break;
			case IDENTIFIER: //識別子
				this.identifierExpression(expression);
				break;
			case ASSIGN: //"="
				AssignNode assignNode = (AssignNode) expression;
				this.assignExpression(assignNode.getLeftValue(), assignNode.getExpression());
				break;
			case EQUIVALENCE: //"=="
				EquivalenceNode equivalenceNode = (EquivalenceNode) expression;
				this.equivalenceExpression(equivalenceNode.getLeft(), equivalenceNode.getRight());
				break;
			case NOT_EQUIVALENCE: //"!="
				NotEquivalenceNode notEquivalenceNode = (NotEquivalenceNode) expression;
				this.notEquivalenceExpression(notEquivalenceNode.getLeft(), notEquivalenceNode.getRight());
				break;
			case LESS_THAN: //"<"
				LessThanNode lessThanNode = (LessThanNode) expression;
				this.lessThanExpression(lessThanNode.getLeft(), lessThanNode.getRight());
				break;
			case LESS_THAN_OR_EQUAL: //"<="
				LessThanOrEqualNode lessThanOrEqualNode = (LessThanOrEqualNode) expression;
				this.lessThanOrEqualExpression(lessThanOrEqualNode.getLeft(), lessThanOrEqualNode.getRight());
				break;
			case GREATER_THAN: //">"
				GreaterThanNode greaterThanNode = (GreaterThanNode) expression;
				this.greaterThanExpression(greaterThanNode.getLeft(), greaterThanNode.getRight());
				break;
			case GREATER_THAN_OR_EQUAL: //">="
				GreaterThanOrEqualNode greaterThanOrEqualNode = (GreaterThanOrEqualNode) expression;
				this.greaterThanOrEqualExpression(greaterThanOrEqualNode.getLeft(), greaterThanOrEqualNode.getRight());
				break;
			case LOGICAL_AND: //"&&"
				LogicalAndNode logicalAndNode = (LogicalAndNode) expression;
				this.logicalAndExpression(logicalAndNode.getLeft(), logicalAndNode.getRight());
				break;
			case LOGICAL_OR: //"||"
				LogicalOrNode logicalOrNode = (LogicalOrNode) expression;
				this.logicalOrExpression(logicalOrNode.getLeft(), logicalOrNode.getRight());
				break;
			case PLUS: //"+"
			case MINUS: //"-"
			case MUL: //"*"
			case DIV: //"/"
			case MOD: //"%"
				this.binaryOperatorExpressionInit(expression);
				break;
			case EXCLAMATION: //"!"
				ExclamationNode exclamationNode = (ExclamationNode) expression;
				this.exclamationExpression(exclamationNode.getLeftValue());
				break;
			case UNARY_MINUS: //"-" 単項マイナス式
				UnaryMinusNode unaryMinusNode = (UnaryMinusNode) expression;
				this.unaryMinusExpression(unaryMinusNode.getLeftValue());
				break;
			case PRE_INCREMENT: //"++" 前置増分
				PreIncrementNode preIncrementNode = (PreIncrementNode) expression;
				this.preIncrementExpression(preIncrementNode.getLeftValue());
				break;
			case PRE_DECREMENT: //"--" 前置減分
				PreDecrementNode preDecrementNode = (PreDecrementNode) expression;
				this.preDecrementExpression(preDecrementNode.getLeftValue());
				break;
			case POST_INCREMENT: //"++" 後置増分
				PostIncrementNode postIncrementNode = (PostIncrementNode) expression;
				this.postIncrementExpression(postIncrementNode.getLeftValue());
				break;
			case POST_DECREMENT: //"--" 後置減分
				PostDecrementNode postDecrementNode = (PostDecrementNode) expression;
				this.postDecrementExpression(postDecrementNode.getLeftValue());
				break;
			case CALL: //関数呼び出し
				CallNode callNode = (CallNode) expression;
				this.executeFunctionCall(callNode);
				break;
		}
	}

	/**
	 * 関数呼び出し
	 */
	@Override
	public void executeFunctionCall(ExpressionNode callNode) {
		
		//呼び出そうとしている関数が標準関数か、ユーザー定義関数かチェックする
		CallNode functionCall = (CallNode) callNode;
		IdentifierNode functionNode = (IdentifierNode) functionCall.getFunction();
		
		//標準関数の呼び出し
		if (functionNode.getIdentifier().isStandardFunctionFlag()) {
			this.executeStandardFunctionCall(functionNode);
			
		//ユーザー定義関数の呼び出し
		} else if (!functionNode.getIdentifier().isStandardFunctionFlag()) {
			
			//式を計算し、引数をスタックに積む
			List<ExpressionNode> arguments = functionCall.getArguments();
			
			//引数の式を計算する
			for (ExpressionNode argument : arguments) {
				this.evaluateExpression(argument);
			}
			
			this.executeUserDefinedFunctionCall(functionNode);
		}
		
		return;
	}


	
	/**
	 * ユーザー定義関数の呼び出し
	 * @param callNode
	 */
	private void executeUserDefinedFunctionCall(IdentifierNode functionNode) {
		
		//式を実行し引数をスタックに詰める
		List<ParameterNode> parameters = functionNode.getParameters();
		
		for (ParameterNode parameter : parameters) {
			
			IdentifierNode identifier = parameter.getIdentifier();
			
			LocalVariable variable;
		}
		
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
	private void executeStandardFunctionCall(IdentifierNode functionNode) {
		
	}
}
