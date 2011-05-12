package c0.ast;

import c0.util.NodeType;

//"!"
public class ExclamationNode extends ExpressionNode {
	
	private ExpressionNode leftValue;

	public ExclamationNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
