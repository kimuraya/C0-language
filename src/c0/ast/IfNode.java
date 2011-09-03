package c0.ast;

import c0.interpreter.Visitor;

//if-else文
public class IfNode extends StatementNode {
	
	private ExpressionNode conditionalExpression; //条件式
	private StatementNode thenStatement; //then
	private StatementNode elseStatement; //else
	
	public IfNode(Location loc, ExpressionNode expression,
			StatementNode thenStatement, StatementNode elseStatement) {
		super(loc);
		this.conditionalExpression = expression;
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
		
		this.printFileNameAndLine(depth, indentFlag);
		
		//条件式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("conditionalExpression");
		
		this.conditionalExpression.dump(depth, true);
		
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

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
