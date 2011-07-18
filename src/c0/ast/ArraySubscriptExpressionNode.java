package c0.ast;

//添字式
public class ArraySubscriptExpressionNode extends ExpressionNode {
	
	private IdentifierNode array;
	private ExpressionNode index;	

	public ArraySubscriptExpressionNode(IdentifierNode array,
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
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ArraySubscriptExpressionNode");
		
		this.array.dump(depth, true);
		this.index.dump(depth, true);
	}
}
