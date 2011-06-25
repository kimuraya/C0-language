package c0.ast;

import c0.util.NodeType;

//"/"
public class DivNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;

	public DivNode(ExpressionNode right, ExpressionNode left) {
		super();
		this.right = right;
		this.left = left;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
