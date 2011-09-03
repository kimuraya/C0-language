package c0.ast;

import c0.interpreter.Visitor;

//return文
public class ReturnNode extends StatementNode {
	
	private ExpressionNode expression; //式
	
	public ReturnNode(Location loc, ExpressionNode expression) {
		super(loc);
		this.expression = expression;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("ReturnNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		if(this.expression != null) {
			this.expression.dump(depth, true);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
