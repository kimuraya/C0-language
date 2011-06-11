package c0.ast;

//添字式
public class ArraySubscriptExpressionNode extends ExpressionNode {
	
	private ExpressionNode array;
	private ExpressionNode index;	

	public ArraySubscriptExpressionNode(ExpressionNode array,
			ExpressionNode index) {
		super();
		this.array = array;
		this.index = index;
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
