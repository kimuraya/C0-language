package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * 大なり比較演算子
 * ">"
 */
public class GreaterThanNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;

	public GreaterThanNode(ExpressionNode left, ExpressionNode right) {
		super();
		this.left = left;
		this.right = right;
		this.nodeType = NodeType.GREATER_THAN;
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
		// TODO 自動生成されたメソッド・スタブ
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\">\" GreaterThanNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
