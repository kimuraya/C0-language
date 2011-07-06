package c0.ast;

import c0.util.NodeType;

//"=="
public class EquivalenceNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;

	public EquivalenceNode(ExpressionNode left, ExpressionNode right) {
		super();
		this.left = left;
		this.right = right;		
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\"==\" EquivalenceNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}
}
