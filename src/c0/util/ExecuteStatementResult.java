package c0.util;

public class ExecuteStatementResult {
	
	private StatementResultFlag statementResultFlag;
	private Value value;
	
	public StatementResultFlag getStatementResultFlag() {
		return statementResultFlag;
	}
	
	public void setStatementResultFlag(StatementResultFlag statementResultFlag) {
		this.statementResultFlag = statementResultFlag;
	}
	
	public Value getValue() {
		return value;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
}
