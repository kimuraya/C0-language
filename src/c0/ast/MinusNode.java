package c0.ast;

//"-"
public class MinusNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;
	
	public MinusNode(ExpressionNode left, ExpressionNode right) {
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
		
		System.out.println("MinusNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}
}
