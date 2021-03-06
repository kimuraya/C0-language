package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

/**
 * 前置増分
 * "++"
 */
public class PreIncrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値

	public PreIncrementNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
		this.nodeType = NodeType.PRE_INCREMENT;
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
		
		System.out.println("\"++\" PreIncrementNode");
		
		this.leftValue.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
