package c0.ast;

import c0.util.NodeType;

//"=="
public class EquivalenceNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;

	public EquivalenceNode(ExpressionNode right, ExpressionNode left) {
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
	public void dump(int depth, boolean indentFlag) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
