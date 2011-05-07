package c0.ast;

//"<="
public class LessThanOrEqualNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;
	
	public LessThanOrEqualNode(Location location, ExpressionNode right,
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
