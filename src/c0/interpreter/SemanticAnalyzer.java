package c0.interpreter;

import java.util.LinkedList;
import java.util.List;

import c0.ast.ArraySubscriptExpressionNode;
import c0.ast.AssignNode;
import c0.ast.AstNode;
import c0.ast.BlockNode;
import c0.ast.BreakNode;
import c0.ast.CallNode;
import c0.ast.DeclareVariableNode;
import c0.ast.DivNode;
import c0.ast.EmptyStatementNode;
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
import c0.util.GlobalScope;
import c0.util.IdentifierType;
import c0.util.LocalScope;
import c0.util.SymbolTable;

public class SemanticAnalyzer implements Visitor {

	@Override
	public void visit(AstNode astNode) {
		
		List<DeclareVariableNode> globalVariables = astNode.getGlobalVariables();
		
		for (DeclareVariableNode declareVariableNode : globalVariables) {
			declareVariableNode.accept(this);
		}
		
		List<IdentifierNode> functions = astNode.getFunctions();
		
		for (IdentifierNode function : functions) {
			function.accept(this);
		}
	}

	@Override
	public void visit(ExpressionNode expressionNode) {
		expressionNode.accept(this);
	}

	@Override
	public void visit(LiteralNode literalNode) {
		
	}

	@Override
	/**
	 * 識別子を走査する
	 */
	public void visit(IdentifierNode identifierNode) {
		
		//関数である場合、複合文を走査する
		
		//変数である場合、生存期間をチェックする
		
	}

	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getLeftValue().accept(this);
		assignNode.getExpression().accept(this);
	}

	@Override
	public void visit(EquivalenceNode equivalenceNode) {
		equivalenceNode.getLeft().accept(this);
		equivalenceNode.getRight().accept(this);
	}

	@Override
	public void visit(NotEquivalenceNode notEquivalenceNode) {
		notEquivalenceNode.getLeft().accept(this);
		notEquivalenceNode.getRight().accept(this);
	}

	@Override
	public void visit(LessThanNode lessThanNode) {
		lessThanNode.getLeft().accept(this);
		lessThanNode.getRight().accept(this);
	}

	@Override
	public void visit(LessThanOrEqualNode lessThanOrEqualNode) {
		lessThanOrEqualNode.getLeft().accept(this);
		lessThanOrEqualNode.getRight().accept(this);
	}

	@Override
	public void visit(GreaterThanNode greaterThanNode) {
		greaterThanNode.getLeft().accept(this);
		greaterThanNode.getRight().accept(this);
	}

	@Override
	public void visit(GreaterThanOrEqualNode greaterThanOrEqualNode) {
		greaterThanOrEqualNode.getLeft().accept(this);
		greaterThanOrEqualNode.getRight().accept(this);
	}

	@Override
	public void visit(LogicalAndNode logicalAndNode) {
		logicalAndNode.getLeft().accept(this);
		logicalAndNode.getRight().accept(this);
	}

	@Override
	public void visit(LogicalOrNode logicalOrNode) {
		logicalOrNode.getLeft().accept(this);
		logicalOrNode.getRight().accept(this);
	}

	@Override
	public void visit(PlusNode plusNode) {
		plusNode.getLeft().accept(this);
		plusNode.getRight().accept(this);
	}

	@Override
	public void visit(MinusNode minusNode) {
		minusNode.getLeft().accept(this);
		minusNode.getRight().accept(this);
	}

	@Override
	public void visit(MulNode mulNode) {
		mulNode.getLeft().accept(this);
		mulNode.getRight().accept(this);
	}

	@Override
	public void visit(DivNode divNode) {
		divNode.getLeft().accept(this);
		divNode.getRight().accept(this);
	}

	@Override
	public void visit(ModNode modNode) {
		modNode.getLeft().accept(this);
		modNode.getRight().accept(this);
	}

	@Override
	public void visit(ExclamationNode exclamationNode) {
		exclamationNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(UnaryMinusNode unaryMinusNode) {
		unaryMinusNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PreIncrementNode preIncrementNode) {
		preIncrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PreDecrementNode preDecrementNode) {
		preDecrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PostIncrementNode postIncrementNode) {
		postIncrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PostDecrementNode postDecrementNode) {
		postDecrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(CallNode callNode) {
		callNode.getFunction().accept(this);
		List<ExpressionNode> parameters = callNode.getArguments();
		
		if (parameters != null) {
			for (ExpressionNode parameter : parameters) {
				parameter.accept(this);
			}
		}
	}
	
	@Override
	public void visit(ArraySubscriptExpressionNode arraySubscriptExpressionNode) {
		arraySubscriptExpressionNode.getArray().accept(this);
		arraySubscriptExpressionNode.getIndex().accept(this);
	}	
	
	@Override
	public void visit(StatementNode statementNode) {
		statementNode.accept(this);
	}

	@Override
	public void visit(BlockNode blockNode) {
		
		//局所変数が宣言されていた場合
		if (blockNode.getLocalVariables() != null) {
			List<DeclareVariableNode> localVariables = blockNode.getLocalVariables();
			
			for (DeclareVariableNode localVariable : localVariables) {
				localVariable.accept(this);
			}
		}
		
		//複合文の中に文がある場合
		if (blockNode.getStatements() != null) {
			List<StatementNode> statements = blockNode.getStatements();
			
			for (StatementNode statement : statements) {
				statement.accept(this);
			}
		}
		
	}

	@Override
	public void visit(IfNode ifNode) {
		ifNode.getConditionalExpression().accept(this);
		ifNode.getThenStatement().accept(this);
		if (ifNode.getElseStatement() != null) {
			ifNode.getElseStatement().accept(this);
		}
	}

	@Override
	public void visit(WhileNode whileNode) {
		whileNode.getConditionalExpression().accept(this);
		whileNode.getBodyStatement().accept(this);
	}

	@Override
	public void visit(ForNode forNode) {
		forNode.getInitializeExpression().accept(this);
		forNode.getConditionalExpression().accept(this);
		forNode.getUpdateExpression().accept(this);
		forNode.getBodyStatement().accept(this);
	}

	@Override
	public void visit(BreakNode breakNode) {
		// TODO break文
	}

	@Override
	public void visit(ReturnNode returnNode) {
		if (returnNode.getExpression() != null) {
			returnNode.getExpression().accept(this);
		}
	}

	@Override
	public void visit(ExpressionStatementNode expressionStatementNode) {
		expressionStatementNode.getExpression().accept(this);
	}

	@Override
	public void visit(EmptyStatementNode emptyStatementNode) {
		// TODO あとで検討する
		
	}

	@Override
	public void visit(DeclareVariableNode declareVariableNode) {
		declareVariableNode.getIdentifier().accept(this);
		
		if (declareVariableNode.getExpression() != null) {
			declareVariableNode.getExpression().accept(this);
		}
	}

	@Override
	public void visit(ParameterNode parameterNode) {
		parameterNode.getIdentifier().accept(this);
	}

}
