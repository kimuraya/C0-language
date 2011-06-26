package c0.ast;

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

	}
}
