package c0.ast;

abstract public class StatementNode extends Node {
	
	public StatementNode(Location loc) {
		super();
		this.location = loc;
	}

	public Location location() {
		return this.location;
	}
}
