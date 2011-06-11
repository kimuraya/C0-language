package c0.ast;

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
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
