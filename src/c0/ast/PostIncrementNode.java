package c0.ast;

//"++" 後置増分
public class PostIncrementNode extends ExpressionNode {
	
	private ExpressionNode leftValue; //左辺値
	
	public PostIncrementNode(ExpressionNode leftValue) {
		super();
		this.leftValue = leftValue;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		// TODO 自動生成されたメソッド・スタブ
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("\"++\" PostIncrementNode");
		
		this.leftValue.dump(depth, true);
	}
}
