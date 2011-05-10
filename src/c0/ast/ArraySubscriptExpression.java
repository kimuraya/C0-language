package c0.ast;

//添字式
public class ArraySubscriptExpression extends ExpressionNode {
	
	private IdentifierNode array;
	private ExpressionNode index;
	
	public ArraySubscriptExpression(Location location, IdentifierNode array,
			ExpressionNode index) {
		super(location);
		this.array = array;
		this.index = index;
	}

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
