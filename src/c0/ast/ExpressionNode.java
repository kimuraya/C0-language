package c0.ast;

import c0.interpreter.Visitor;

/**
 * 式
 */
abstract public class ExpressionNode extends Node {
	
	public ExpressionNode() {
		super();
	}

	abstract public Location location();
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
