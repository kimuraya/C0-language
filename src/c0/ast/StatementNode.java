package c0.ast;

abstract public class StatementNode extends Node {
	
	protected Location location;
	
	public StatementNode(Location loc) {
		super();
		this.location = loc;
	}

	public Location location() {
		return this.location;
	}
}
