package c0.ast;

//式
abstract public class ExpressionNode extends Node {
	
	protected Location location;
	
	public ExpressionNode(Location location) {
		super();
		this.location = location;
	}

	public Location location() {
		return location;
	}
	
	abstract public void dump(); //構文木を表示する
}
