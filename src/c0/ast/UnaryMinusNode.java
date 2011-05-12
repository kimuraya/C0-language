package c0.ast;

//"-" 単項マイナス式
public class UnaryMinusNode extends ExpressionNode {
	
	private ExpressionNode leftValue;
	
	public UnaryMinusNode(Location location, ExpressionNode leftValue) {
		super(location);
		this.leftValue = leftValue;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
