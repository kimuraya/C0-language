package c0.ast;

import c0.interpreter.Visitor;

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
	
	public ExpressionNode getLeft() {
		return left;
	}

	public void setLeft(ExpressionNode left) {
		this.left = left;
	}

	public ExpressionNode getRight() {
		return right;
	}

	public void setRight(ExpressionNode right) {
		this.right = right;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\"+\" PlusNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
