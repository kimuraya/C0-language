package c0.ast;

import c0.interpreter.Visitor;

//式文
public class ExpressionStatementNode extends StatementNode {
	
	private ExpressionNode expression; //式
	
	public ExpressionStatementNode(Location loc, ExpressionNode expression) {
		super(loc);
		this.expression = expression;
	}

	public ExpressionNode getExpression() {
		return expression;
	}

	public void setExpression(ExpressionNode expression) {
		this.expression = expression;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ExpressionStatementNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		this.expression.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
