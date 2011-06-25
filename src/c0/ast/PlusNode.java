package c0.ast;

//"+"
public class PlusNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;

	public PlusNode(ExpressionNode right, ExpressionNode left) {
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
