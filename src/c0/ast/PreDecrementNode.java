package c0.ast;

//"--" 前置減分
public class PreDecrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PreDecrementNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
