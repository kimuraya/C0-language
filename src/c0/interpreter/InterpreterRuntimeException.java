package c0.interpreter;

/**
 * C0言語インタプリタ実行時に発生した例外
 */
public class InterpreterRuntimeException extends Exception {

	public InterpreterRuntimeException(String errorMessage) {
		super(errorMessage);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5557266827814794064L;

}
