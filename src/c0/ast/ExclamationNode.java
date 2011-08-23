package c0.ast;

//"!"
public class ExclamationNode extends ExpressionNode {
	
	private ExpressionNode leftValue;

	public ExclamationNode(ExpressionNode leftValue) {
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
		
		System.out.println("\"!\" ExclamationNode");
		
		this.leftValue.dump(depth, true);
	}

}
