package c0.ast;

import c0.util.NodeType;

//"!"
public class ExclamationNode extends ExpressionNode {
	
	private ExpressionNode expression;

	public ExclamationNode(Location location, ExpressionNode expression) {
		super(location);
		this.expression = expression;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
