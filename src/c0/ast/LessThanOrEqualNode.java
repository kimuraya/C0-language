package c0.ast;

//"<="
public class LessThanOrEqualNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;

	public LessThanOrEqualNode(ExpressionNode right, ExpressionNode left) {
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
