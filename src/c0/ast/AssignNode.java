package c0.ast;

import c0.util.NodeType;

//"="
public class AssignNode extends ExpressionNode {

	private IdentifierNode leftValue; //左辺値
	private ExpressionStatementNode expression; //式

	public AssignNode(Location location, IdentifierNode leftValue,
			ExpressionStatementNode expression) {
		super(location);
		this.leftValue = leftValue;
		this.expression = expression;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
