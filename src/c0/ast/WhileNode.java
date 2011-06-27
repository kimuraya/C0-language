package c0.ast;

//while文
public class WhileNode extends StatementNode {
	
	private ExpressionNode conditionalExpression; //条件式
	private StatementNode bodyStatement; //文
	
	public WhileNode(Location loc, ExpressionNode conditionalExpression,
			StatementNode bodyStatement) {
		super(loc);
		this.conditionalExpression = conditionalExpression;
		this.bodyStatement = bodyStatement;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("WhileNode");
		
		//条件式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("conditionalExpression");
		
		this.conditionalExpression.dump(depth, true);
		
		//文の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("bodyStatement");
		
		this.bodyStatement.dump(depth, true);
	}
}
