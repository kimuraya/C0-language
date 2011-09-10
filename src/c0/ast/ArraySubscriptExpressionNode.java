package c0.ast;

import c0.interpreter.Visitor;

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
	
	public IdentifierNode getArray() {
		return array;
	}

	public void setArray(IdentifierNode array) {
		this.array = array;
	}

	public ExpressionNode getIndex() {
		return index;
	}

	public void setIndex(ExpressionNode index) {
		this.index = index;
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

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
