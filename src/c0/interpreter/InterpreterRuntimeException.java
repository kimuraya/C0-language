package c0.interpreter;

import c0.ast.StatementNode;

/**
 * C0言語インタプリタ実行時に発生した例外
 */
public class InterpreterRuntimeException extends Exception {
	
	private StatementNode statementNode; //例外が発生した文
	private static final long serialVersionUID = -5557266827814794064L;
	
	public InterpreterRuntimeException(String errorMessage) {
		super(errorMessage);
	}
	
	public InterpreterRuntimeException(String errorMessage, StatementNode statementNode) {
		super(errorMessage);
		this.setStatementNode(statementNode);
	}
	
	public void setStatementNode(StatementNode statementNode) {
		this.statementNode = statementNode;
	}

	public StatementNode getStatementNode() {
		return statementNode;
	}
	
}
