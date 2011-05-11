package c0.ast;

//"++" 後置増分
public class PostIncrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PostIncrementNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
