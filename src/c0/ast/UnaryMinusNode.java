package c0.ast;

//"-" 単項マイナス式
public class UnaryMinusNode extends ExpressionNode {
	
	private ExpressionNode expression;
	
	public UnaryMinusNode(Location location, ExpressionNode expression) {
		super(location);
		this.expression = expression;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
