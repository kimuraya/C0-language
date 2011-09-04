package c0.interpreter;

import java.util.List;

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
import c0.ast.Node;
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

public class AstVisitor implements Visitor {

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
		// TODO 自動生成されたメソッド・スタブ
		expressionNode.accept(this);
	}

	@Override
	public void visit(LiteralNode literalNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	//識別子をシンボルテーブルに登録する
	public void visit(IdentifierNode identifierNode) {
		System.out.println(identifierNode.getIdentifier().getName());
		
		//引数がある場合
		if(identifierNode.getParameters() != null) {
			List<ParameterNode> Parameters = identifierNode.getParameters();
			for (ParameterNode parameterNode : Parameters) {
				parameterNode.accept(this);
			}
		}
		
		//複合文がある場合
		if(identifierNode.getBlock() != null) {
			identifierNode.getBlock().accept(this);
		}
		
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
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(PlusNode plusNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(MinusNode minusNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(MulNode mulNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(DivNode divNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(ModNode modNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(ExclamationNode exclamationNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(UnaryMinusNode unaryMinusNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(PreIncrementNode preIncrementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(PreDecrementNode preDecrementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(PostIncrementNode postIncrementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(PostDecrementNode postDecrementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(CallNode callNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
	@Override
	public void visit(StatementNode statementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
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
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(WhileNode whileNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(ForNode forNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(BreakNode breakNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(ReturnNode returnNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(ExpressionStatementNode expressionStatementNode) {
		expressionStatementNode.getExpression().accept(this);
	}

	@Override
	public void visit(EmptyStatementNode emptyStatementNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void visit(DeclareVariableNode declareVariableNode) {
		declareVariableNode.getIdentifier().accept(this);
	}

	@Override
	public void visit(ParameterNode parameterNode) {
		parameterNode.getIdentifier().accept(this);
	}	
}
