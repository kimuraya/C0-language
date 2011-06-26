package c0.ast;

import java.util.List;

//関数呼び出し（ネイティブ関数を含む）
public class CallNode extends ExpressionNode {
	
	private ExpressionNode function;
	private List<ExpressionNode> parameters; //引数
	
	public CallNode(ExpressionNode function, List<ExpressionNode> parameters) {
		super();
		this.function = function;
		this.parameters = parameters;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
