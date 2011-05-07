package c0.ast;

import c0.util.NodeType;

//"=="
public class EquivalenceNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;
	
	public EquivalenceNode(Location location, ExpressionNode right,
			ExpressionNode left) {
		super(location);
		this.right = right;
		this.left = left;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
