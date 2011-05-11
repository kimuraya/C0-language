package c0.ast;

//"--" 後置減分
public class PostDecrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PostDecrementNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
