package c0.ast;

/**
 * 文
 */
abstract public class StatementNode extends Node {
	
	boolean loopFlag; //trueの場合、for, while文の本体
	
	public StatementNode(Location loc) {
		super();
		this.location = loc;
	}

	public Location location() {
		return this.location;
	}

	public boolean isLoopFlag() {
		return loopFlag;
	}

	public void setLoopFlag(boolean loopFlag) {
		this.loopFlag = loopFlag;
	}
}
