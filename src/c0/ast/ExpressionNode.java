package c0.ast;

//式
abstract public class ExpressionNode extends Node {
	
	public ExpressionNode() {
		super();
	}

	abstract public Location location();
}
