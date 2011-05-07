package c0.ast;

//">="
public class GreaterThanOrEqualNode extends ExpressionNode {
	
	private ExpressionNode right;
	private ExpressionNode left;
	
	public GreaterThanOrEqualNode(Location location, ExpressionNode right,
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
