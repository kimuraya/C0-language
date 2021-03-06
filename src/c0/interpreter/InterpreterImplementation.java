package c0.interpreter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import c0.ast.ArraySubscriptExpressionNode;
import c0.ast.AssignNode;
import c0.ast.BlockNode;
import c0.ast.CallNode;
import c0.ast.DataTypeNode;
import c0.ast.DeclareVariableNode;
import c0.ast.DivNode;
import c0.ast.EquivalenceNode;
import c0.ast.ExclamationNode;
import c0.ast.ExpressionNode;
import c0.ast.ExpressionStatementNode;
import c0.ast.ForNode;
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
import c0.ast.ReturnNode;
import c0.ast.StatementNode;
import c0.ast.UnaryMinusNode;
import c0.ast.WhileNode;
import c0.util.DataType;
import c0.util.ExecuteStatementResult;
import c0.util.FramePointer;
import c0.util.GlobalScope;
import c0.util.Identifier;
import c0.util.IdentifierType;
import c0.util.LocalVariable;
import c0.util.NodeType;
import c0.util.StackElement;
import c0.util.StackElementType;
import c0.util.StatementResultFlag;
import c0.util.SymbolTable;
import c0.util.Value;

/**
 * インタプリタの実処理
 */
public class InterpreterImplementation implements Interpreter {
	
	private GlobalScope globalScope = null; //グローバル変数を管理する
	private Stack<StackElement> callStack = null; //局所変数、戻り値、戻り先、フレームポインタを積む
	protected Stack<StackElement> operandStack = null; //式の計算に使用する
	protected Properties properties = null; //エラーメッセージ
	
	public InterpreterImplementation(Stack<StackElement> callStack,
			Stack<StackElement> operandStack, GlobalScope globalScope) {
		super();
		this.callStack = callStack;
		this.operandStack = operandStack;
		this.globalScope = globalScope;
	}

	public GlobalScope getGlobalScope() {
		return globalScope;
	}

	public void setGlobalScope(GlobalScope globalScope) {
		this.globalScope = globalScope;
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
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeStatement(StatementNode statementNode) throws InterpreterRuntimeException {
		
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
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeBlockStatement(
			StatementNode statementNode) throws InterpreterRuntimeException {
		
		BlockNode block = (BlockNode) statementNode;
		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);
		
		//局所変数をスタックに詰める
		List<DeclareVariableNode> localVariables = block.getLocalVariables();
		for (DeclareVariableNode declareVariableNode : localVariables) {
			
			//データ型
			DataTypeNode localVariableDataTypeNode = declareVariableNode.getDataType();
			DataType localVariableDataType = localVariableDataTypeNode.getDataType();
			
			//初期化式の実行
			Value value = new Value();
			
			//配列以外の変数の初期化
			if (declareVariableNode.getExpression() != null
					&& (localVariableDataType != DataType.INT_ARRAY || localVariableDataType != DataType.BOOLEAN_ARRAY)) {
				ExpressionNode expression = declareVariableNode.getExpression();
				this.evaluateExpression(expression);
				
				StackElement result = this.operandStack.pop();
				value = result.getValue();
				
			//配列の初期化
			//配列の初期化式は認めない。要素数のない配列は作れない
			} else if (declareVariableNode.getExpression() == null && localVariableDataTypeNode.getElementNumber() != null &&
					(localVariableDataType == DataType.INT_ARRAY || localVariableDataType == DataType.BOOLEAN_ARRAY)) {
				
				//要素数の計算
				Value elementNumberValue = new Value();
				ExpressionNode elementNumberExpression = localVariableDataTypeNode.getElementNumber();
				this.evaluateExpression(elementNumberExpression);
				StackElement result = this.operandStack.pop();
				elementNumberValue = result.getValue();
				
				//結果が整数でなければ、例外を投げる
				if (elementNumberValue.getDataType() != DataType.INT) {
					String errorMessage = this.properties.getProperty("error.IsNotAnIntegerNumberOfElements");
					throw new InterpreterRuntimeException(errorMessage, statementNode);
				}
				
				//配列の生成
				int elementNumber = elementNumberValue.getInteger();

				int intArray[];
				boolean  boolArray[];
				if (localVariableDataType == DataType.INT_ARRAY && elementNumber > 0) {

					intArray = new int[elementNumber];
					value.setDataType(DataType.INT_ARRAY);
					value.setIntegerArray(intArray);

				} else if (localVariableDataType == DataType.BOOLEAN_ARRAY && elementNumber > 0) {

					boolArray = new boolean[elementNumber];
					value.setDataType(DataType.BOOLEAN_ARRAY);
					value.setBooleanArray(boolArray);
					
				//配列の要素数が0か、0より小さい場合は例外を投げる
				} else if (elementNumber <= 0) {
					String errorMessage = this.properties.getProperty("error.LessThanOrEqualToZeroTheNumberOfElements");
					throw new InterpreterRuntimeException(errorMessage, statementNode);
				}
			}
			
			//計算結果をローカル変数にバインドする
			LocalVariable variable = new LocalVariable();
			variable.setVariable(declareVariableNode.getIdentifier().getIdentifier()); //識別子をセットする
			variable.setValue(value); //値をセットする
			
			//コールスタックに引数を詰める
			StackElement variableElement = new StackElement();
			variableElement.setStackElementType(StackElementType.VARIABLE);
			variableElement.setVariable(variable);
			
			//局所変数をコールスタックに詰める
			this.callStack.push(variableElement);
		}
		
		//文を実行する
		List<StatementNode> statements = block.getStatements();
		for (StatementNode statement : statements) {
			
			ret = this.executeStatement(statement);
			
			//return文を実行したら、処理を終える
			if (ret.getStatementResultFlag() == StatementResultFlag.RETURN_STATEMENT_RESULT) {
				
				break;
				
			//ループの中でbreak文を実行したら、処理を終える
			} else if (ret.getStatementResultFlag() == StatementResultFlag.BREAK_STATEMENT_RESULT) {
				
				break;
				
			}
		}

		//局所変数をスタックから破棄する
		if (!this.callStack.isEmpty()) {
			for (int i = localVariables.size() - 1; i >= 0; i--) {
				this.callStack.pop();
			}
		}

		return ret;
	}

	/**
	 * if文
	 * @throws Exception 
	 */
	@Override
	public ExecuteStatementResult executeIfStatement(StatementNode statementNode) throws InterpreterRuntimeException {

		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);
		IfNode ifNode = (IfNode) statementNode;

		//条件式を取り出し、実行する
		ExpressionNode conditionalExpression = ifNode.getConditionalExpression();
		this.evaluateExpression(conditionalExpression);

		//オペランドスタックから計算結果を取り出す
		StackElement stackElement = this.operandStack.pop();

		Value value = stackElement.getValue();

		//計算結果が真偽値でなければ、例外を出す
		if(value.getDataType() != DataType.BOOLEAN) {
			String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
			throw new InterpreterRuntimeException(errorMessage, statementNode);
		}
		
		//trueならthenを実行する
		if (value.isBool()) {

			ret = this.executeStatement(ifNode.getThenStatement());

		//falseならelseを実行する
		} else if (!value.isBool() && ifNode.getElseStatement() != null) {

			ret = this.executeStatement(ifNode.getElseStatement());

		}

		return ret;
	}

	/**
	 * while文
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeWhileStatement(
			StatementNode statementNode) throws InterpreterRuntimeException {

		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);

		WhileNode whileNode = (WhileNode) statementNode;

		//条件式を取り出し、実行する
		ExpressionNode conditionalExpression = whileNode.getConditionalExpression();
		this.evaluateExpression(conditionalExpression);

		//オペランドスタックから計算結果を取り出す
		StackElement stackElement = this.operandStack.pop();

		Value value = stackElement.getValue();
		
		//計算結果が真偽値でなければ、例外を出す
		if(value.getDataType() != DataType.BOOLEAN) {
			String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
			throw new InterpreterRuntimeException(errorMessage, statementNode);
		}

		//trueの場合、文を実行する
		//StatementResultFlagがNORMAL_STATEMENT_RESULTでない場合、ループの処理を終了する。
		while (value.isBool() && ret.getStatementResultFlag() == StatementResultFlag.NORMAL_STATEMENT_RESULT) {

			//条件式を取り出し、実行する
			conditionalExpression = whileNode.getConditionalExpression();
			this.evaluateExpression(conditionalExpression);

			//オペランドスタックから計算結果を取り出す
			stackElement = this.operandStack.pop();

			value = stackElement.getValue();

			//文を実行する
			boolean loopFlag = whileNode.getBodyStatement().isLoopFlag();
			if (value.isBool()) {
				ret = this.executeStatement(whileNode.getBodyStatement());
			} else if (!value.isBool()) {
				break;
			}

			//break文を検知した場合、ループを終了させる
			//ループの終了後、StatementResultFlagをNORMAL_STATEMENT_RESULTに戻す。
			if ((ret.getStatementResultFlag() == StatementResultFlag.BREAK_STATEMENT_RESULT) && loopFlag) {
				ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);
				break;
			}

			if (ret.getStatementResultFlag() == StatementResultFlag.RETURN_STATEMENT_RESULT) {
				break;
			}
		}

		return ret;
	}

	/**
	 * for文
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeForStatement(
			StatementNode statementNode) throws InterpreterRuntimeException {

		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);
		ForNode forNode = (ForNode) statementNode;
		StackElement stackElement = null;
		Value value = new Value();

		//初期化式を実行
		ExpressionNode initializeExpression = forNode.getInitializeExpression();
		
		if (initializeExpression != null) {
			
			this.evaluateExpression(initializeExpression);
			
			//オペランドスタックから計算結果を破棄する
			if (!this.operandStack.isEmpty()) {
				stackElement = this.operandStack.pop();
			}
		}
		
		//条件式を実行
		ExpressionNode conditionalExpression = forNode.getConditionalExpression();
		
		if (conditionalExpression != null) {
			
			this.evaluateExpression(conditionalExpression);
			
			//オペランドスタックから計算結果を取り出す
			if (!this.operandStack.isEmpty()) {
				stackElement = this.operandStack.pop();
				value = stackElement.getValue();
			}
			
			//計算結果が真偽値でなければ、例外を出す
			if(value.getDataType() != DataType.BOOLEAN) {
				String errorMessage = this.properties.getProperty("error.ConditionalExpressionError");
				throw new InterpreterRuntimeException(errorMessage, statementNode);
			}
		}

		//文を実行
		//条件式がtrueである限り、ループが継続する
		while (value.isBool() && ret.getStatementResultFlag() == StatementResultFlag.NORMAL_STATEMENT_RESULT) {

			//条件式を取り出し、実行する
			if (conditionalExpression != null) {
				conditionalExpression = forNode.getConditionalExpression();
				this.evaluateExpression(conditionalExpression);
	
				//オペランドスタックから計算結果を取り出す
				if (!this.operandStack.isEmpty()) {
					stackElement = this.operandStack.pop();
					value = stackElement.getValue();
				}
			}

			//文を実行する
			boolean loopFlag = forNode.getBodyStatement().isLoopFlag();
			if (value.isBool()) {
				ret = this.executeStatement(forNode.getBodyStatement());
			} else if (!value.isBool()) {
				break;
			}

			//break文を検知した場合、ループを終了させる
			//ループの終了後、StatementResultFlagをNORMAL_STATEMENT_RESULTに戻す。
			if ((ret.getStatementResultFlag() == StatementResultFlag.BREAK_STATEMENT_RESULT) && loopFlag) {
				ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);
				break;
			}

			if (ret.getStatementResultFlag() == StatementResultFlag.RETURN_STATEMENT_RESULT) {
				break;
			}

			//後置き式を実行
			ExpressionNode updateExpression = forNode.getUpdateExpression();
			
			if (updateExpression != null) {
				
				this.evaluateExpression(updateExpression);
	
				//オペランドスタックから計算結果を破棄する
				if (!this.operandStack.isEmpty()) {
					stackElement = this.operandStack.pop();
				}
			}
		}

		return ret;
	}

	/**
	 * break文
	 */
	@Override
	public ExecuteStatementResult executeBreakStatement(
			StatementNode statementNode) {

		//StatementResultFlagをBREAK_STATEMENT_RESULTにする
		ExecuteStatementResult executeStatementResult = new ExecuteStatementResult();
		executeStatementResult.setStatementResultFlag(StatementResultFlag.BREAK_STATEMENT_RESULT);

		return executeStatementResult;
	}

	/**
	 * return文
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeReturnStatement(
			StatementNode statementNode) throws InterpreterRuntimeException {

		//StatementResultFlagをRETURN_STATEMENT_RESULTにする
		ExecuteStatementResult executeStatementResult = new ExecuteStatementResult();
		executeStatementResult.setStatementResultFlag(StatementResultFlag.RETURN_STATEMENT_RESULT);
		
		//戻り値を計算する
		ReturnNode returnNode = (ReturnNode) statementNode;
		
		if (returnNode.getExpression() != null) {
			ExpressionNode expression = returnNode.getExpression();
			this.evaluateExpression(expression);
		}

		//オペランドスタックから計算結果を取り出す
		/*
		StackElement stackElement = null;
		Value value = new Value();
		if (!this.operandStack.isEmpty()) {
			stackElement = this.operandStack.pop();
			value = stackElement.getValue();
		}
		*/

		//戻り値をオペランドスタックに詰める

		return executeStatementResult;
	}

	/**
	 * 式文
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public ExecuteStatementResult executeExpressionStatement(
			StatementNode statementNode) throws InterpreterRuntimeException {
		
		ExecuteStatementResult executeStatementResult = null;
		try {
			executeStatementResult = new ExecuteStatementResult();
			executeStatementResult.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);

			//式を実行する
			ExpressionStatementNode expressionStatementNode = (ExpressionStatementNode) statementNode;
			this.evaluateExpression(expressionStatementNode.getExpression());

			//スタックが空でなければ、値を取り出す
			if (!this.operandStack.isEmpty()) {
				StackElement stackElement = this.operandStack.pop();
				Value value = stackElement.getValue();
				executeStatementResult.setValue(value);
			}
		} catch (InterpreterRuntimeException e) {
			e.setStatementNode(statementNode); //例外が起こった文を保存する
			throw e;
		}
		
		return executeStatementResult;
	}

	/**
	 * 空文
	 */
	@Override
	public ExecuteStatementResult executeEmptyStatement(
			StatementNode statementNode) {

		//何も実行しない
		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);

		return ret;
	}

	@Override
	public ExecuteStatementResult executeParameter(StatementNode statementNode) {

		//引数の値を返す

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
		stackElement.setStackElementType(StackElementType.LITERAL);
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
		LiteralNode stringLiteral = (LiteralNode) string;

		//スタックの要素の作製
		StackElement stackElement = new StackElement();
		stackElement.setStackElementType(StackElementType.LITERAL);
		stackElement.setValue(stringLiteral.getLiteral());

		//オペランドスタックに値を詰める
		this.operandStack.add(stackElement);

		return;

	}

	/**
	 * 真偽値定数
	 */
	@Override
	public void booleanLiteralExpression(ExpressionNode bool) {

		//ノードから定数を取り出す
		LiteralNode booleanLiteral = (LiteralNode) bool;

		//スタックの要素の作製
		StackElement stackElement = new StackElement();
		stackElement.setStackElementType(StackElementType.LITERAL);
		stackElement.setValue(booleanLiteral.getLiteral());

		//オペランドスタックに値を詰める
		this.operandStack.add(stackElement);

		return;

	}

	/**
	 * 識別子
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public void identifierExpression(ExpressionNode expression) throws InterpreterRuntimeException {
		
		//識別子を取り出す
		IdentifierNode identifierNode = (IdentifierNode) expression;
		Identifier search = identifierNode.getIdentifier(); //この識別子を探す
		LocalVariable foundLocalVariable = null;
		Identifier foundGlobalVariable = null;
		boolean searchFlag = false; //識別子が見つかっかどうかを表す。trueなら見つかっている
		
		//コールスタック（ローカル変数）から識別子を探す
		//フレームポインタにぶつかるまでコールスタックを検索する
		//グローバル変数の代入式など場合、処理をスキップする
		if (this.callStack.size() != 0) {
			for (int i = this.callStack.size() - 1; this.callStack.get(i).getStackElementType() != StackElementType.FRAME_POINTER; i--) {
	
				StackElement variableElement = this.callStack.get(i);
	
				//目的の識別子が見つかった場合、その値を取り出す
				LocalVariable localVariable = variableElement.getVariable();
				String localVariableName = localVariable.getVariable().getName();
	
				if (localVariableName.equals(search.getName())) {
					searchFlag = true;
					foundLocalVariable = localVariable;
					break;
				}
			}
		}

		//グローバル変数（シンボルテーブル）から識別子を探す
		if (!searchFlag) {
			
			SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();
			
			if (globalSymbolTable.searchSymbol(search.getName())) {
				searchFlag = true;
				foundGlobalVariable = globalSymbolTable.getSymbol(search.getName());
			}
		}


		//識別子が存在すれば、値を取り出し、オペランドスタックに詰める
		if (searchFlag) {

			Value resultValue = null;

			//見つかった識別子から値を取り出す
			if (foundLocalVariable != null) {
				resultValue = foundLocalVariable.getValue();
			} else if (foundGlobalVariable != null) {
				resultValue = foundGlobalVariable.getLeftValue();
			}

			//オペランドスタックに値を詰める
			StackElement resultElement = new StackElement();
			resultElement.setStackElementType(StackElementType.VARIABLE);
			resultElement.setValue(resultValue);

			//オペランドスタックに値を詰める
			this.operandStack.push(resultElement);

		} else {
			//識別子がローカル変数にも、グローバル変数にも存在しない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 論理AND, 論理ORの処理
	 * @param expression
	 * @throws InterpreterRuntimeException 
	 */
	public void logicalOperatorExpression(ExpressionNode expression) throws InterpreterRuntimeException {

		ExpressionNode left = null;
		ExpressionNode right = null;
		boolean result = false;

		//ノードの種類によって、処理を分ける
		StackElement stackLeft = null;
		StackElement stackRight = null;
		Value leftValue = null;
		Value rightValue = null;
		switch(expression.getNodeType()) {

			case LOGICAL_AND: //"&&"

				LogicalAndNode logicalAndNode = (LogicalAndNode) expression;
				left = logicalAndNode.getLeft();
				right = logicalAndNode.getRight();

				//左の式を実行
				this.evaluateExpression(left);
				stackLeft = this.operandStack.pop();
				leftValue = stackLeft.getValue();
				
				//左から値を取り出せない場合
				if (leftValue == null) {
					String errorMessage = this.properties.getProperty("error.TheValueOfTheExpressionCanNotBeEjected");
					throw new InterpreterRuntimeException(errorMessage);
				}

				//データ型のチェック
				if (leftValue.getDataType() == DataType.BOOLEAN) {

					//左がtrueだった場合のみ、右を評価する
					if (leftValue.isBool()) {

						//右の式を実行
						this.evaluateExpression(right);
						stackRight = this.operandStack.pop();
						rightValue = stackRight.getValue();
						
						//右から値を取り出せない場合
						if (rightValue == null) {
							String errorMessage = this.properties.getProperty("error.TheValueOfTheExpressionCanNotBeEjected");
							throw new InterpreterRuntimeException(errorMessage);
						}
						
						if (rightValue.getDataType() == DataType.BOOLEAN) {

							//右もtrue？
							if (rightValue.isBool()) {
								result = true; //左右の結果、両方がtrue
							}

						} else {
							//右の結果がbooleanではない
							String errorMessage = this.properties.getProperty("error.TheRightExpressionOfLogicalANDIsNotABooleanValue");
							throw new InterpreterRuntimeException(errorMessage);
						}
					}
					
				} else {
					//左の結果がbooleanではない
					String errorMessage = this.properties.getProperty("error.TheLeftExpressionOfLogicalANDIsNotABooleanValue");
					throw new InterpreterRuntimeException(errorMessage);
				}

				break;

			case LOGICAL_OR: //"||"

				LogicalOrNode logicalOrNode = (LogicalOrNode) expression;
				left = logicalOrNode.getLeft();
				right = logicalOrNode.getRight();

				//左の式を実行
				this.evaluateExpression(left);
				stackLeft = this.operandStack.pop();
				leftValue = stackLeft.getValue();
				
				//左から値を取り出せない場合
				if (leftValue == null) {
					String errorMessage = this.properties.getProperty("error.TheValueOfTheExpressionCanNotBeEjected");
					throw new InterpreterRuntimeException(errorMessage);
				}
				
				//データ型のチェック
				if (leftValue.getDataType() == DataType.BOOLEAN) {

					//左の式の結果を保存
					result = leftValue.isBool();

					//左の結果がtrueではない場合のみ。右を実行
					if (!leftValue.isBool()) {

						//右の式を実行
						this.evaluateExpression(right);
						stackRight = this.operandStack.pop();
						rightValue = stackRight.getValue();
						
						//右から値を取り出せない場合
						if (rightValue == null) {
							String errorMessage = this.properties.getProperty("error.TheValueOfTheExpressionCanNotBeEjected");
							throw new InterpreterRuntimeException(errorMessage);
						}
						
						//左の結果を代入
						if (rightValue.getDataType() == DataType.BOOLEAN) {
							result = rightValue.isBool();
						} else {
							//右の結果がbooleanではない
							String errorMessage = this.properties.getProperty("error.TheRightExpressionOfLogicalORIsNotABooleanValue");
							throw new InterpreterRuntimeException(errorMessage);
						}
					}
				} else {
					//左の結果がbooleanではない
					String errorMessage = this.properties.getProperty("error.TheLeftExpressionOfLogicalORIsNotABooleanValue");
					throw new InterpreterRuntimeException(errorMessage);
				}

				break;
		}

		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();
		resultValue.setBool(result);
		resultValue.setDataType(DataType.BOOLEAN);

		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);

		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);

		return;
	}

	/**
	 * 二項演算子の前処理。データ型のチェック
	 * @param expression
	 * @throws InterpreterRuntimeException 
	 */
	public void binaryOperatorExpressionInit(ExpressionNode expression) throws InterpreterRuntimeException {

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

		Value leftValue = stackLeft.getValue();
		Value rightValue = stackRight.getValue();
		
		//左右の式から値を取り出せない場合
		if (leftValue == null || rightValue == null) {
			String errorMessage = this.properties.getProperty("error.AnAttemptWasMadeToPerformAnOperationThatIsNotDefined");
			throw new InterpreterRuntimeException(errorMessage);
		}

		//データ型のチェック
		
		//整数の式
		if ((leftValue.getDataType() == DataType.INT) && (rightValue.getDataType() == DataType.INT)) {

			this.binaryOperatorExpression(leftValue.getInteger(), rightValue.getInteger(), expression.getNodeType());

		//真偽値の式
		} else if ((leftValue.getDataType() == DataType.BOOLEAN) && (rightValue.getDataType() == DataType.BOOLEAN)) {

			this.binaryOperatorExpression(leftValue.isBool(), rightValue.isBool(), expression.getNodeType());

		} else {
			//データ型のチェックに引っかからなかった場合
			String errorMessage = this.properties.getProperty("error.AnAttemptWasMadeToPerformAnOperationThatIsNotDefined");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 二項演算子の式
	 * 加算式, 減算式, 乗算, 除算式, 剰余式
	 * 小なり比較演算子, 以下比較演算子, 大なり比較演算子, 以上比較演算子
	 * +, -, *, /, %
	 * @param left
	 * @param right
	 * @param expressionType
	 * @throws InterpreterRuntimeException 
	 */
	public void binaryOperatorExpression(int left, int right, NodeType expressionType) throws InterpreterRuntimeException {

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
				
				//0の除算を検知した場合
				if (right == 0) {
					String errorMessage = this.properties.getProperty("error.DivisionByZero");
					throw new InterpreterRuntimeException(errorMessage);
				}
				
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
			case EQUIVALENCE: //"=="
				resultBool = left == right;
				operationResultBool = true;
				break;
			case NOT_EQUIVALENCE: //"!="
				resultBool = left != right;
				operationResultBool = true;
				break;
		}

		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();

		if (operationResultInt) {
			resultValue.setInteger(resultInt);
			resultValue.setDataType(DataType.INT);
		} else if (operationResultBool) {
			resultValue.setBool(resultBool);
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
	public void binaryOperatorExpression(boolean left, boolean right, NodeType expressionType) {

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
		resultValue.setBool(result);
		resultValue.setDataType(DataType.BOOLEAN);

		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);

		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);

		return;
	}

	/**
	 * 単項演算子
	 * @param expression
	 * @throws InterpreterRuntimeException 
	 */
	public void unaryOperatorExpressionInit(ExpressionNode expression) throws InterpreterRuntimeException {
		
		ExpressionNode left = null;

		//ノードの種類によって、処理を分ける
		switch(expression.getNodeType()) {
			case EXCLAMATION: //"!"
				ExclamationNode exclamationNode = (ExclamationNode) expression;
				left = exclamationNode.getLeftValue();
				break;
			case UNARY_MINUS: //"-" 単項マイナス式
				UnaryMinusNode unaryMinusNode = (UnaryMinusNode) expression;
				left = unaryMinusNode.getLeftValue();
				break;
			case PRE_INCREMENT: //"++" 前置増分
				PreIncrementNode preIncrementNode = (PreIncrementNode) expression;
				left = preIncrementNode.getLeftValue();
				break;
			case PRE_DECREMENT: //"--" 前置減分
				PreDecrementNode preDecrementNode = (PreDecrementNode) expression;
				left = preDecrementNode.getLeftValue();
				break;
			case POST_INCREMENT: //"++" 後置増分
				PostIncrementNode postIncrementNode = (PostIncrementNode) expression;
				left = postIncrementNode.getLeftValue();
				break;
			case POST_DECREMENT: //"--" 後置減分
				PostDecrementNode postDecrementNode = (PostDecrementNode) expression;
				left = postDecrementNode.getLeftValue();
				break;
		}

		//左辺値の式を実行する
		this.evaluateExpression(left);

		//オペランドスタックから左辺値の計算結果を取り出す
		StackElement stackLeft = this.operandStack.pop();

		Value leftValue = stackLeft.getValue();
		
		//左辺値から値を取り出せない場合
		if (leftValue == null) {
			String errorMessage = this.properties.getProperty("error.CanNotEjectTheValueFromTheLeftValue");
			throw new InterpreterRuntimeException(errorMessage);
		}

		//データ型のチェック
		//整数の式
		if (leftValue.getDataType() == DataType.INT) {

			this.unaryOperatorExpression(expression, leftValue.getInteger(), expression.getNodeType());

		//真偽値の式
		} else if (leftValue.getDataType() == DataType.BOOLEAN) {

			this.unaryOperatorExpression(leftValue.isBool(), expression.getNodeType());

		} else {
			//データ型のチェックに引っかからなかった場合
			String errorMessage = this.properties.getProperty("error.AnAttemptWasMadeToPerformAnOperationThatIsNotDefined");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 単項演算子の式
	 * @param left
	 * @param expressionType
	 * @throws InterpreterRuntimeException 
	 */
	public void unaryOperatorExpression(ExpressionNode expression, int left, NodeType expressionType) throws InterpreterRuntimeException {

		//式を実行する
		int result = 0; //実行結果

		//ノードの種類によって、処理を分ける
		switch(expressionType) {
			case UNARY_MINUS: //"-" 単項マイナス式
				result = -left;
				break;
			case PRE_INCREMENT: //"++" 前置増分
				result = ++left;
				this.leftValueUpdate(expression, left);
				break;
			case PRE_DECREMENT: //"--" 前置減分
				result = --left;
				this.leftValueUpdate(expression, left);
				break;
			case POST_INCREMENT: //"++" 後置増分
				result = left++;
				this.leftValueUpdate(expression, left);
				break;
			case POST_DECREMENT: //"--" 後置減分
				result = left--;
				this.leftValueUpdate(expression, left);
				break;
		}

		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();

		resultValue.setInteger(result);
		resultValue.setDataType(DataType.INT);

		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);

		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);

		return;
	}

	/**
	 * 左辺値の更新
	 * @throws InterpreterRuntimeException 
	 */
	public void leftValueUpdate(ExpressionNode expression, int leftValue) throws InterpreterRuntimeException {

		ExpressionNode left = null;

		//ノードの種類によって、処理を分ける
		switch(expression.getNodeType()) {
			case PRE_INCREMENT: //"++" 前置増分
				PreIncrementNode preIncrementNode = (PreIncrementNode) expression;
				left = preIncrementNode.getLeftValue();
				break;
			case PRE_DECREMENT: //"--" 前置減分
				PreDecrementNode preDecrementNode = (PreDecrementNode) expression;
				left = preDecrementNode.getLeftValue();
				break;
			case POST_INCREMENT: //"++" 後置増分
				PostIncrementNode postIncrementNode = (PostIncrementNode) expression;
				left = postIncrementNode.getLeftValue();
				break;
			case POST_DECREMENT: //"--" 後置減分
				PostDecrementNode postDecrementNode = (PostDecrementNode) expression;
				left = postDecrementNode.getLeftValue();
				break;
		}

		//左が左辺値でない場合、例外を出す
		Identifier search = null;
		int index = 0; //添字
		
		try {
			
			ArraySubscriptExpressionNode arraySubscriptExpressionNode = null;
			IdentifierNode identifierNode = null;
			
			if (left instanceof IdentifierNode) {
				
				identifierNode = (IdentifierNode) left;
				search = identifierNode.getIdentifier();
				
			} else if (left instanceof ArraySubscriptExpressionNode) {
				
				arraySubscriptExpressionNode = (ArraySubscriptExpressionNode) left;
				identifierNode = arraySubscriptExpressionNode.getArray();
				search = identifierNode.getIdentifier();
				
				//添字を計算する
				Value value = this.getIndexValue(arraySubscriptExpressionNode);
				
				//添字が整数でなかった場合
				if (value.getDataType() != DataType.INT) {
					String errorMessage = this.properties.getProperty("error.IsNotAnIntegerNumberOfElements");
					throw new InterpreterRuntimeException(errorMessage);
				}
				
				index = value.getInteger();
				
				//添字が0より小さいかどうかチェック
				if (index < 0) {
					String errorMessage = this.properties.getProperty("error.NumberOfElementsIsLessThanZero");
					throw new InterpreterRuntimeException(errorMessage);
				}
			}
			
		} catch (java.lang.ClassCastException e) {
			String errorMessage = this.properties.getProperty("error.IncrementAndTheDecrementOperatorIsNecessaryToUseTheIdentifier");
			throw new InterpreterRuntimeException(errorMessage);
		}
		
		LocalVariable foundLocalVariable = null;
		Identifier foundGlobalVariable = null;
		boolean searchFlag = false; //識別子が見つかっかどうかを表す。trueなら見つかっている

		//コールスタックから代入先を探す（局所変数）
		//コールスタック（ローカル変数）から識別子を探す
		//フレームポインタにぶつかるまでコールスタックを検索する
		for (int i = this.callStack.size() - 1; this.callStack.get(i).getStackElementType() != StackElementType.FRAME_POINTER; i--) {

			StackElement variableElement = this.callStack.get(i);

			//目的の識別子が見つかった場合、その値を取り出す
			LocalVariable localVariable = variableElement.getVariable();
			String localVariableName = localVariable.getVariable().getName();

			if (localVariableName.equals(search.getName())) {
				searchFlag = true;
				foundLocalVariable = localVariable;
				break;
			}
		}

		//シンボルテーブルから代入先を探す（大域変数）
		if (!searchFlag) {

			SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();

			if (globalSymbolTable.searchSymbol(search.getName())) {
				searchFlag = true;
				foundGlobalVariable = globalSymbolTable.getSymbol(search.getName());
			}
		}

		//更新する値を作る
		Value value = new Value();
		value.setInteger(leftValue);
		value.setDataType(DataType.INT);

		//左辺値を更新する
		//識別子が存在すれば、値を取り出し、オペランドスタックに詰める
		if (searchFlag) {
			
			//見つかった識別子に対し、代入を実行する
			if (left instanceof IdentifierNode) {
				
				if (foundLocalVariable != null) {
					foundLocalVariable.setValue(value);
				} else if (foundGlobalVariable != null) {
					foundGlobalVariable.setLeftValue(value);
				}
				
			//添字式の更新
			} else if (left instanceof ArraySubscriptExpressionNode) {
				
				if (foundLocalVariable != null) {
					
					Value localVal = foundLocalVariable.getValue();
					
					if (localVal.getDataType() == DataType.INT_ARRAY) {
						
						int[] array = localVal.getIntegerArray();
						
						if (index < array.length) {
							array[index] = leftValue;
						} else {
							//範囲外の要素を参照しようとした
							String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
							throw new InterpreterRuntimeException(errorMessage);
						}
					}
					
					
				} else if (foundGlobalVariable != null) {
					
					Value globalVal = foundGlobalVariable.getLeftValue();
					
					if (globalVal.getDataType() == DataType.INT_ARRAY) {
						
						int[] array = globalVal.getIntegerArray();
						
						if (index < array.length) {
							array[index] = leftValue;
						} else {
							//範囲外の要素を参照しようとした
							String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
							throw new InterpreterRuntimeException(errorMessage);
						}
					}
				}
				
			}
			
		} else {
			//識別子がローカル変数にも、グローバル変数にも存在しない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 単項演算子の式
	 * @param left
	 * @param expressionType
	 */
	public void unaryOperatorExpression(boolean left, NodeType expressionType) {

		//式を実行する
		boolean result = false;

		//ノードの種類によって、処理を分ける
		switch(expressionType) {
			case EXCLAMATION: //"!"
				result = !left;
				break;
		}

		//実行結果をインタプリタで扱える形式にする
		Value resultValue = new Value();

		resultValue.setBool(result);
		resultValue.setDataType(DataType.BOOLEAN);

		StackElement resultElement = new StackElement();
		resultElement.setValue(resultValue);

		//オペランドスタックに値を詰める
		this.operandStack.push(resultElement);

		return;
	}

	/**
	 * 代入式
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public void assignExpression(ExpressionNode expression) throws InterpreterRuntimeException {

		AssignNode assignNode = (AssignNode) expression;
		ExpressionNode left = assignNode.getLeftValue(); //左辺値
		ExpressionNode right = assignNode.getExpression(); //代入される式
		
		Identifier search = null;//この識別子を探す
		ArraySubscriptExpressionNode arraySubscriptExpressionNode = null;
		IdentifierNode identifierNode = null;
		Value value = null;
		int index = 0; //添字
		if (left instanceof IdentifierNode) {

			identifierNode = (IdentifierNode) left;
			search = identifierNode.getIdentifier();

		} else if (left instanceof ArraySubscriptExpressionNode) {

			arraySubscriptExpressionNode = (ArraySubscriptExpressionNode) left;
			identifierNode = arraySubscriptExpressionNode.getArray();
			search = identifierNode.getIdentifier();
			
			//添字を計算する
			value = this.getIndexValue(arraySubscriptExpressionNode);
			
			//添字が整数でなかった場合
			if (value.getDataType() != DataType.INT) {
				String errorMessage = this.properties.getProperty("error.IsNotAnIntegerNumberOfElements");
				throw new InterpreterRuntimeException(errorMessage);
			}
			
			index = value.getInteger();
			
			//添字が0より小さいかどうかチェック
			if (index < 0) {
				String errorMessage = this.properties.getProperty("error.NumberOfElementsIsLessThanZero");
				throw new InterpreterRuntimeException(errorMessage);
			}

		} else {
			//左の式が識別子でも添字式でもない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.TheLeftExpressionIsNotAnLvalue");
			throw new InterpreterRuntimeException(errorMessage);
		}

		LocalVariable foundLocalVariable = null;
		Identifier foundGlobalVariable = null;
		boolean searchFlag = false; //識別子が見つかっかどうかを表す。trueなら見つかっている

		//コールスタックから代入先を探す（局所変数）
		//コールスタック（ローカル変数）から識別子を探す
		//フレームポインタにぶつかるまでコールスタックを検索する
		for (int i = this.callStack.size() - 1; this.callStack.get(i).getStackElementType() != StackElementType.FRAME_POINTER; i--) {

			StackElement variableElement = this.callStack.get(i);

			//目的の識別子が見つかった場合、その値を取り出す
			LocalVariable localVariable = variableElement.getVariable();
			String localVariableName = localVariable.getVariable().getName();

			if (localVariableName.equals(search.getName())) {
				searchFlag = true;
				foundLocalVariable = localVariable;
				break;
			}
		}

		//シンボルテーブルから代入先を探す（大域変数）
		if (!searchFlag) {

			SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();

			if (globalSymbolTable.searchSymbol(search.getName())) {
				searchFlag = true;
				foundGlobalVariable = globalSymbolTable.getSymbol(search.getName());
			}
		}

		//式(right)を実行する
		this.evaluateExpression(right);

		//オペランドスタックから右の値の計算結果を取り出す
		StackElement stackLeft = this.operandStack.pop();

		Value rightValue = stackLeft.getValue();

		//代入を実行する
		//識別子が存在すれば、値を取り出し、オペランドスタックに詰める
		if (searchFlag) {

			Value localVal = null;
			Value globalVal = null;
			if (foundLocalVariable != null) {
				localVal = foundLocalVariable.getValue();
			} else if (foundGlobalVariable != null) {
				globalVal = foundGlobalVariable.getLeftValue();
			}

			//見つかった識別子に対し、代入を実行する

			//ローカル変数の整数配列に対する代入
			if (foundLocalVariable != null && localVal.getDataType() == DataType.INT_ARRAY) {

				int[] array = localVal.getIntegerArray();
				
				if (rightValue.getDataType() == DataType.INT) {
					
					if (index < array.length) {
						array[index] = rightValue.getInteger();
					} else {
						//範囲外の要素を参照しようとした
						String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
						throw new InterpreterRuntimeException(errorMessage);
					}
					
				} else {
					//右辺値が整数でなかった場合、例外を投げる
					String errorMessage = this.properties.getProperty("error.TheDataTypeOfTheValueToAssignToTheRight-handSideIsDifferent");
					throw new InterpreterRuntimeException(errorMessage);
				}

				//代入を行った配列をローカル変数にセット
				localVal.setIntegerArray(array);

			//グローバル変数の整数配列に対する代入
			} else if (foundGlobalVariable != null && globalVal.getDataType() == DataType.INT_ARRAY) {
				
				int[] array = globalVal.getIntegerArray();
				
				if (rightValue.getDataType() == DataType.INT) {
					
					if (index < array.length) {
						array[index] = rightValue.getInteger();
					} else {
						//範囲外の要素を参照しようとした
						String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
						throw new InterpreterRuntimeException(errorMessage);
					}
					
				} else {
					//右辺値が整数でなかった場合、例外を投げる
					String errorMessage = this.properties.getProperty("error.TheDataTypeOfTheValueToAssignToTheRight-handSideIsDifferent");
					throw new InterpreterRuntimeException(errorMessage);
				}

				//代入を行った配列をグローバル変数にセット
				globalVal.setIntegerArray(array);

			//ローカル変数の真偽値配列に対する代入
			} else if (foundLocalVariable != null && localVal.getDataType() == DataType.BOOLEAN_ARRAY) {

				boolean[] array = localVal.getBooleanArray();
				
				if (rightValue.getDataType() == DataType.BOOLEAN) {
					
					if (index < array.length) {
						array[index] = rightValue.isBool();
					} else {
						//範囲外の要素を参照しようとした
						String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
						throw new InterpreterRuntimeException(errorMessage);
					}
					
				} else {
					//右辺値が真偽値でなかった場合、例外を投げる
					String errorMessage = this.properties.getProperty("error.TheDataTypeOfTheValueToAssignToTheRight-handSideIsDifferent");
					throw new InterpreterRuntimeException(errorMessage);
				}

				//代入を行った配列をローカル変数にセット
				localVal.setBooleanArray(array);

			//グローバル変数の真偽値配列に対する代入
			} else if (foundGlobalVariable != null && globalVal.getDataType() == DataType.BOOLEAN_ARRAY) {

				boolean[] array = globalVal.getBooleanArray();
				
				if (rightValue.getDataType() == DataType.BOOLEAN) {
					
					if (index < array.length) {
						array[index] = rightValue.isBool();
					} else {
						//範囲外の要素を参照しようとした
						String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
						throw new InterpreterRuntimeException(errorMessage);
					}
					
				} else {
					//右辺値が真偽値でなかった場合、例外を投げる
					String errorMessage = this.properties.getProperty("error.TheDataTypeOfTheValueToAssignToTheRight-handSideIsDifferent");
					throw new InterpreterRuntimeException(errorMessage);
				}

				//代入を行った配列をローカル変数にセット
				globalVal.setBooleanArray(array);
				
			//ローカル変数への代入
			} else if (foundLocalVariable != null) {

				foundLocalVariable.setValue(rightValue);

			//グローバル変数への代入
			} else if (foundGlobalVariable != null) {

				foundGlobalVariable.setLeftValue(rightValue);

			}

		} else {
			//識別子がローカル変数にも、グローバル変数にも存在しない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 添字式の添字を取り出す
	 * @param arraySubscriptExpressionNode
	 * @return
	 * @throws InterpreterRuntimeException 
	 */
	private Value getIndexValue(ArraySubscriptExpressionNode arraySubscriptExpressionNode) throws InterpreterRuntimeException {

		Value ret = null;
		ExpressionNode indexExpression = arraySubscriptExpressionNode.getIndex();
		this.evaluateExpression(indexExpression);
		StackElement resultElement = this.operandStack.pop();
		ret = resultElement.getValue();
		
		//添字が存在しない場合、例外を投げる
		if (ret == null) {
			String errorMessage = this.properties.getProperty("error.IndexDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}
		
		
		return ret;
	}

	/**
	 * 添字式
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public void arraySubscriptExpression(ExpressionNode expression) throws InterpreterRuntimeException {

		ArraySubscriptExpressionNode arraySubscriptExpressionNode = (ArraySubscriptExpressionNode) expression;

		//添字を計算する
		Value value = new Value();
		ExpressionNode indexExpression = arraySubscriptExpressionNode.getIndex();
		this.evaluateExpression(indexExpression);
		StackElement resultElement = this.operandStack.pop();
		value = resultElement.getValue();
		
		//添字が整数でなかった場合
		if (value.getDataType() != DataType.INT) {
			String errorMessage = this.properties.getProperty("error.IsNotAnIntegerNumberOfElements");
			throw new InterpreterRuntimeException(errorMessage);
		}
		
		int index = value.getInteger();
		
		//添字が0より小さいかどうかチェック
		if (index < 0) {
			String errorMessage = this.properties.getProperty("error.NumberOfElementsIsLessThanZero");
			throw new InterpreterRuntimeException(errorMessage);
		}
		
		//配列を取り出す
		//グローバル変数とローカル変数で処理を分ける
		IdentifierNode arrayNode = arraySubscriptExpressionNode.getArray();
		Identifier arrayIdentifier = arrayNode.getIdentifier();
		Value arrayValue = null;
		DataType arrayDataType = null;

		SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();

		//ローカル変数にある場合
		if (this.searchLocalVariable(arrayIdentifier.getName())) {

			LocalVariable localVariable = this.getLocalVariable(arrayIdentifier.getName());
			arrayValue = localVariable.getValue();
			arrayDataType = arrayValue.getDataType();

		//グローバル変数にある場合
		} else if (globalSymbolTable.searchSymbol(arrayIdentifier.getName())) {

			Identifier globalVariable = globalSymbolTable.getSymbol(arrayIdentifier.getName());
			arrayValue = globalVariable.getLeftValue();
			arrayDataType = arrayValue.getDataType();

		//識別子が見つからない場合、例外を投げる
		} else {
			//識別子がローカル変数にも、グローバル変数にも存在しない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}

		//添字と配列のサイズをチェックする
		
		Value arrayIndexValue = null;
		if (arrayDataType == DataType.INT_ARRAY || arrayDataType == DataType.BOOLEAN_ARRAY) {

			//添字が配列の範囲内なら配列から値を取り出し、オペランドスタックに積む
			if (arrayDataType == DataType.INT_ARRAY ) {

				int[] array = arrayValue.getIntegerArray();

				if (index < array.length) {
					int val = array[index];
					arrayIndexValue = new Value();
					arrayIndexValue.setDataType(DataType.INT);
					arrayIndexValue.setInteger(val);
				} else {
					//範囲外の要素を参照しようとした
					String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
					throw new InterpreterRuntimeException(errorMessage);
				}

			} else if (arrayDataType == DataType.BOOLEAN_ARRAY) {

				boolean[] array = arrayValue.getBooleanArray();

				if (index < array.length) {
					boolean val = array[index];
					arrayIndexValue = new Value();
					arrayIndexValue.setDataType(DataType.BOOLEAN);
					arrayIndexValue.setBool(val);
				} else {
					//範囲外の要素を参照しようとした
					String errorMessage = this.properties.getProperty("error.IsGreaterThanTheBoundsOfTheArrayElement");
					throw new InterpreterRuntimeException(errorMessage);
				}

			} else {
				//配列のデータ型が整数でも真偽値でもない
				String errorMessage = this.properties.getProperty("error.AnAttemptWasMadeToPerformAnOperationThatIsNotDefined");
				throw new InterpreterRuntimeException(errorMessage);
			}

			//スタックの要素の作製
			StackElement stackElement = new StackElement();
			stackElement.setStackElementType(StackElementType.LITERAL);
			stackElement.setValue(arrayIndexValue);

			//オペランドスタックに値を詰める
			this.operandStack.add(stackElement);
		} else {
			//添字式の識別子が配列でなかった場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierIsNotAnArray");
			throw new InterpreterRuntimeException(errorMessage);
		}

		return;
	}

	/**
	 * 識別子がローカル変数として存在するかチェック
	 * @param name
	 * @return
	 */
	private boolean searchLocalVariable(String name) {

		boolean ret = false; //識別子が見つかっかどうかを表す。trueなら見つかっている

		//コールスタック（ローカル変数）から識別子を探す
		//フレームポインタにぶつかるまでコールスタックを検索する
		for (int i = this.callStack.size() - 1; this.callStack.get(i).getStackElementType() != StackElementType.FRAME_POINTER; i--) {

			StackElement variableElement = this.callStack.get(i);

			LocalVariable localVariable = variableElement.getVariable();
			String localVariableName = localVariable.getVariable().getName();

			if (localVariableName.equals(name)) {
				ret = true;
				break;
			}
		}

		return ret;
	}

	/**
	 * コールスタックからローカル変数を取り出す
	 * @param name
	 * @return
	 */
	private LocalVariable getLocalVariable(String name) {

		//識別子を取り出す
		LocalVariable ret = null;
		
		//コールスタック（ローカル変数）から識別子を探す
		//フレームポインタにぶつかるまでコールスタックを検索する
		for (int i = this.callStack.size() - 1; this.callStack.get(i).getStackElementType() != StackElementType.FRAME_POINTER; i--) {

			StackElement variableElement = this.callStack.get(i);

			//目的の識別子が見つかった場合、その値を取り出す
			LocalVariable localVariable = variableElement.getVariable();
			String localVariableName = localVariable.getVariable().getName();

			if (localVariableName.equals(name)) {
				ret = localVariable;
				break;
			}
		}
		
		return ret;
	}

	/**
	 * 式の実行
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public void evaluateExpression(ExpressionNode expression) throws InterpreterRuntimeException {

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
			case ARRAY_SUBSCRIPT: //添字式
				this.arraySubscriptExpression(expression);
				break;
			case ASSIGN: //"="
				this.assignExpression(expression);
				break;
			case LOGICAL_AND: //"&&"
			case LOGICAL_OR: //"||"
				this.logicalOperatorExpression(expression);
				break;
			case EQUIVALENCE: //"=="
			case NOT_EQUIVALENCE: //"!="
			case LESS_THAN: //"<"
			case LESS_THAN_OR_EQUAL: //"<="
			case GREATER_THAN: //">"
			case GREATER_THAN_OR_EQUAL: //">="
			case PLUS: //"+"
			case MINUS: //"-"
			case MUL: //"*"
			case DIV: //"/"
			case MOD: //"%"
				this.binaryOperatorExpressionInit(expression);
				break;
			case EXCLAMATION: //"!"
			case UNARY_MINUS: //"-" 単項マイナス式
			case PRE_INCREMENT: //"++" 前置増分
			case PRE_DECREMENT: //"--" 前置減分
			case POST_INCREMENT: //"++" 後置増分
			case POST_DECREMENT: //"--" 後置減分
				this.unaryOperatorExpressionInit(expression);
				break;
			case CALL: //関数呼び出し
				this.executeFunctionCall(expression);
				break;
		}
	}

	/**
	 * 関数呼び出し
	 * @throws InterpreterRuntimeException 
	 */
	@Override
	public void executeFunctionCall(ExpressionNode expression) throws InterpreterRuntimeException {

		CallNode callNode = (CallNode) expression;
		IdentifierNode functionNode = (IdentifierNode) callNode.getFunction();
		Identifier function = null;

		//関数名を元にシンボルテーブルを検索する
		SymbolTable globalSymbolTable = this.getGlobalScope().getGlobalSymbolTable();

		if (globalSymbolTable.searchSymbol(functionNode.getIdentifier().getName())) {
			function = globalSymbolTable.getSymbol(functionNode.getIdentifier().getName());
		} else {
			//指定した関数が存在しない場合、例外を投げる
			String errorMessage = this.properties.getProperty("error.IdentifierDoesNotExist");
			throw new InterpreterRuntimeException(errorMessage);
		}
		
		//見つかった識別子が関数であるかどうかチェック
		if (function.getIdentifierType() != IdentifierType.FUNCTION) {
			String errorMessage = this.properties.getProperty("error.UsingTheIdentifierThatIsNotAFunction");
			throw new InterpreterRuntimeException(errorMessage);
		}

		//関数呼び出しに引数が存在する場合のみ、引数を取り出す
		List<ParameterNode> parameters = null;
		if (function.getFunctionNode() != null && function.getFunctionNode().getParameters() != null) {
			parameters = function.getFunctionNode().getParameters();
		}
		
		//呼び出そうとしている関数が標準関数か、ユーザー定義関数かチェックする
		//関数呼び出しと関数の引数の数が異なる場合、例外を投げるようにする

		//標準関数の呼び出し
		if (function.isStandardFunctionFlag()) {

			//引数を取り出す
			List<ExpressionNode> arguments = callNode.getArguments();
			this.executeStandardFunctionCall(function, arguments);

		//ユーザー定義関数の呼び出し
		} else if (!function.isStandardFunctionFlag()) {

			//式を計算し、引数をスタックに積む
			List<ExpressionNode> arguments = callNode.getArguments();
			List<StackElement> variableElements = new ArrayList<StackElement>();

			//引数がある場合のみ、処理を行う
			if (parameters != null) {

				//引数の式を計算する
				for (int i = 0; i < arguments.size(); i++) {

					ExpressionNode argument = arguments.get(i);
					ParameterNode parameter = parameters.get(i);

					//式の実行
					this.evaluateExpression(argument);
					StackElement result = this.operandStack.pop();
					Value value = result.getValue();

					//計算結果を引数（ローカル変数）にバインドする
					LocalVariable variable = new LocalVariable();
					variable.setVariable(parameter.getIdentifier().getIdentifier()); //識別子をローカル変数にセットする
					variable.setValue(value); //値をセットする

					//引数を作る
					StackElement variableElement = new StackElement();
					variableElement.setStackElementType(StackElementType.VARIABLE);
					variableElement.setVariable(variable);

					//引数をリストに追加
					variableElements.add(variableElement);
				}
			}

			//コールスタックにフレームポインタを詰める
			FramePointer framePointer = new FramePointer();
			StackElement frameElement = new StackElement();
			frameElement.setStackElementType(StackElementType.FRAME_POINTER);
			frameElement.setFramePointer(framePointer);
			this.callStack.push(frameElement);

			//フレームポインタをコールスタックに詰めた後、引数を詰める。
			//フレームポインタを先に詰めると、引数の計算が出来ない
			if (parameters != null) {
				for (StackElement variableElement : variableElements) {
					this.callStack.push(variableElement);
				}
			}

			functionNode = function.getFunctionNode(); //識別子から構文木上のリンクを取り出す

			//ユーザー定義関数の呼び出し
			this.executeUserDefinedFunctionCall(functionNode);

			//フレームポインタと引数をコールスタックから除去
			if (!this.callStack.isEmpty()) {

				if (arguments != null) {
					for (int i = arguments.size(); i >= 0; i--) {
						this.callStack.pop();
					}
				} else if (arguments == null) {
					this.callStack.pop(); //フレームポインタのみ除去する
				}

			}

		}

		return;
	}

	/**
	 * ユーザー定義関数の呼び出し
	 * @param callNode
	 * @throws InterpreterRuntimeException 
	 */
	private void executeUserDefinedFunctionCall(IdentifierNode functionNode) throws InterpreterRuntimeException {

		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);

		//複合文（関数本体）の実行
		BlockNode block = (BlockNode) functionNode.getBlock();

		ret = this.executeBlockStatement(block);

		return;
	}

	/**
	 * 標準関数の呼び出し
	 * @param callNode
	 * @throws InterpreterRuntimeException 
	 */
	private void executeStandardFunctionCall(Identifier function, List<ExpressionNode> arguments) throws InterpreterRuntimeException {

		ExecuteStatementResult ret = new ExecuteStatementResult();
		ret.setStatementResultFlag(StatementResultFlag.NORMAL_STATEMENT_RESULT);

		LinkedList<Value> valueList = new LinkedList<Value>();
		//String standardFunctionName = functionNode.getIdentifier().getStandardFunctionName(); //呼び出そうとしている関数名
		String standardFunctionName = function.getStandardFunctionName(); //標準関数を実行するメソッド名
		StandardFunction standardFunction = new StandardFunction(); //標準関数
		standardFunction.setOperandStack(this.operandStack); //標準関数にオペランドスタックをセット
		
		//引数の式を計算する
		
		if (arguments != null) {
			for (ExpressionNode argument : arguments) {
	
				//式の実行
				this.evaluateExpression(argument);
	
				//結果を変数として、コールスタックに詰める
				StackElement result = this.operandStack.pop();
				Value value = result.getValue();
	
				valueList.add(value);
			}
		}

		//関数名からstandardFunctionNameを取り出し、名前でメソッドを呼び出す
		Class thisClass = standardFunction.getClass();
		Method[] methods = thisClass.getMethods();

		for (Method method : methods) {

			//必要なメソッドの実行が終わったら、処理を終える
			if (method.getName().equals(standardFunctionName)) {
				try {
					method.invoke(standardFunction, valueList);
				} catch (IllegalArgumentException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}

	}
}
