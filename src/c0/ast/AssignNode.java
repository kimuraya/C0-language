package c0.ast;

import c0.util.NodeType;

//"="
public class AssignNode extends ExpressionNode {

	private IdentifierNode leftValue; //左辺値
	private ExpressionNode expression; //式

	public AssignNode(IdentifierNode leftValue,
			ExpressionNode expression) {
		super();
		this.leftValue = leftValue;
		this.expression = expression;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
