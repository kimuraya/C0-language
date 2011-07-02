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
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("CallNode");
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("function");
		
		this.function.dump(depth, true);
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("parameters");
		
		for(ExpressionNode parameter : parameters) {
			parameter.dump(depth, true);
		}
	}
}
