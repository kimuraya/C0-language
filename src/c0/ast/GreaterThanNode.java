package c0.ast;

//">"
public class GreaterThanNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;

	public GreaterThanNode(ExpressionNode left, ExpressionNode right) {
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
		// TODO 自動生成されたメソッド・スタブ
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\">\" GreaterThanNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}
}
