package c0.ast;

import c0.interpreter.Visitor;
import c0.util.NodeType;

//"!"
public class ExclamationNode extends ExpressionNode {
	
	private ExpressionNode leftValue;

	public ExclamationNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
		this.nodeType = NodeType.EXCLAMATION;
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
		
		System.out.println("\"!\" ExclamationNode");
		
		this.leftValue.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
