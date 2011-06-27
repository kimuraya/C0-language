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
		
		//thenの出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("thenStatement");
		
		this.thenStatement.dump(depth, true);
		
		//elseの出力
		if(this.elseStatement != null) {
			
			if (indentFlag) {
				this.printIndent(depth);
			}
			
			System.out.println("elseStatement");
			this.elseStatement.dump(depth, true);
		}
		
	}
}
