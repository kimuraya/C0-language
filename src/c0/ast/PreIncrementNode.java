package c0.ast;

import c0.interpreter.Visitor;

//"++" 前置増分
public class PreIncrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値

	public PreIncrementNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
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
		
		System.out.println("\"++\" PreIncrementNode");
		
		this.leftValue.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
