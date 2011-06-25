package c0.ast;

//"-" 単項マイナス式
public class UnaryMinusNode extends ExpressionNode {
	
	private ExpressionNode leftValue;
	
	public UnaryMinusNode(ExpressionNode leftValue) {
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
