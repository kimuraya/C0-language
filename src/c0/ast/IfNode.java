package c0.ast;

//if-else文
public class IfNode extends StatementNode {
	
	private ExpressionNode expression; //条件式
	private StatementNode thenStatement; //then
	private StatementNode elseStatement; //else
	
	public IfNode(Location loc, ExpressionNode expression,
			StatementNode thenStatement, StatementNode elseStatement) {
		super(loc);
		this.expression = expression;
		this.thenStatement = thenStatement;
		this.elseStatement = elseStatement;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("IfNode");
	}
}
