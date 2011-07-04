package c0.ast;

import c0.util.NodeType;

//"="
public class AssignNode extends ExpressionNode {

	private ExpressionNode leftValue; //左辺値
	private ExpressionNode expression; //式

	public AssignNode(ExpressionNode leftValue,
			ExpressionNode expression) {
		super();
		this.leftValue = leftValue;
		this.expression = expression;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("AssignNode");
		
		this.leftValue.dump(depth, true);
		this.expression.dump(depth, true);
	}
}
