package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * 以下比較演算子
 * "<="
 */
public class LessThanOrEqualNode extends ExpressionNode {
	
	private ExpressionNode left;
	private ExpressionNode right;

	public LessThanOrEqualNode(ExpressionNode left, ExpressionNode right) {
		super();
		this.left = left;
		this.right = right;
		this.nodeType = NodeType.LESS_THAN_OR_EQUAL;
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
		
		System.out.println("\"<=\" LessThanOrEqualNode");
		
		this.left.dump(depth, true);
		this.right.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
