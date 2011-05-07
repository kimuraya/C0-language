package c0.ast;

import c0.util.NodeType;

//"/"
public class DivNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;
	
	public DivNode(Location location, ExpressionNode right, ExpressionNode left) {
		super(location);
		this.right = right;
		this.left = left;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
