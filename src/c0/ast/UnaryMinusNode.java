package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 *　単項マイナス式
 * "-"
 */
public class UnaryMinusNode extends ExpressionNode {
	
	private ExpressionNode leftValue;
	
	public UnaryMinusNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
		this.nodeType = NodeType.UNARY_MINUS;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ExpressionNode getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(ExpressionNode leftValue) {
		this.leftValue = leftValue;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\"-\" UnaryMinusNode");
		
		this.leftValue.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
