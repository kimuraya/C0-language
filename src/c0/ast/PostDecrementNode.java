package c0.ast;

//"--" 後置減分
public class PostDecrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PostDecrementNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
