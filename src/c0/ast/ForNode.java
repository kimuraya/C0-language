package c0.ast;

import c0.interpreter.Visitor;

//for文
public class ForNode extends StatementNode {
	
	private ExpressionNode initializeExpression; //初期化
	private ExpressionNode conditionalExpression; //条件
	private ExpressionNode updateExpression; //更新
	private StatementNode bodyStatement; //文
	
	public ForNode(Location loc, ExpressionNode initializeExpression,
			ExpressionNode conditionalExpression,
			ExpressionNode updateExpression, StatementNode bodyStatement) {
		super(loc);
		this.initializeExpression = initializeExpression;
		this.conditionalExpression = conditionalExpression;
		this.updateExpression = updateExpression;
		this.bodyStatement = bodyStatement;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ForNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		//初期化式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("initializeExpression");
		
		this.initializeExpression.dump(depth, true);
		
		//条件式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("conditionalExpression");
		
		this.conditionalExpression.dump(depth, true);
		
		//更新式の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("updateExpression");
		
		this.updateExpression.dump(depth, true);
		
		//文の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("bodyStatement");
		
		this.bodyStatement.dump(depth, true);
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
