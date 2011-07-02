package c0.ast;

//"+"
public class PlusNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;

	public PlusNode(ExpressionNode left, ExpressionNode right) {
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
		
		System.out.println("PlusNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}
}
