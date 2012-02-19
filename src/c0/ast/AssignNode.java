package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * 代入式
 */
public class AssignNode extends ExpressionNode {

	private ExpressionNode leftValue; //左辺値
	private ExpressionNode expression; //式

	public AssignNode(ExpressionNode leftValue,
			ExpressionNode expression) {
		super();
		this.leftValue = leftValue;
		this.expression = expression;
		this.nodeType = NodeType.ASSIGN;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ExpressionNode getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(ExpressionNode leftValue) {
		this.leftValue = leftValue;
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
		
		System.out.println("\"=\" AssignNode");
		
		this.leftValue.dump(depth, true);
		this.expression.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
