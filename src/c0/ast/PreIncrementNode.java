package c0.ast;

//"++" 前置増分
public class PreIncrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PreIncrementNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
