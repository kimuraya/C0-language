package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * while文
 */
public class WhileNode extends StatementNode {
	
	private ExpressionNode conditionalExpression; //条件式
	private StatementNode bodyStatement; //文
	
	public WhileNode(Location loc, ExpressionNode conditionalExpression,
			StatementNode bodyStatement) {
		super(loc);
		this.conditionalExpression = conditionalExpression;
		this.bodyStatement = bodyStatement;
		nodeType = NodeType.WHILE_STATEMENT;
	}

	public ExpressionNode getConditionalExpression() {
		return conditionalExpression;
	}

	public void setConditionalExpression(ExpressionNode conditionalExpression) {
		this.conditionalExpression = conditionalExpression;
	}

	public StatementNode getBodyStatement() {
		return bodyStatement;
	}

	public void setBodyStatement(StatementNode bodyStatement) {
		this.bodyStatement = bodyStatement;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("WhileNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		//条件式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("conditionalExpression");
		
		this.conditionalExpression.dump(depth, true);
		
		//文の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("bodyStatement");
		
		this.bodyStatement.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
