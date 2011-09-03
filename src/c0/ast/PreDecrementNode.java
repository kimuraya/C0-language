package c0.ast;

import c0.interpreter.Visitor;

//"--" 前置減分
public class PreDecrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PreDecrementNode(ExpressionNode leftValue) {
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
		
		System.out.println("\"--\" PreDecrementNode");
		
		this.leftValue.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
