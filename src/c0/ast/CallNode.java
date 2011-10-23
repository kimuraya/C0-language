package c0.ast;

import java.util.List;

import c0.interpreter.Visitor;
import c0.util.NodeType;

//関数呼び出し（ネイティブ関数を含む）
public class CallNode extends ExpressionNode {
	
	private ExpressionNode function;
	private List<ExpressionNode> arguments; //引数
	
	public CallNode(ExpressionNode function, List<ExpressionNode> parameters) {
		super();
		this.function = function;
		this.arguments = parameters;
		this.nodeType = NodeType.CALL;
	}

	@Override
	public Location location() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public ExpressionNode getFunction() {
		return function;
	}

	public void setFunction(ExpressionNode function) {
		this.function = function;
	}

	public List<ExpressionNode> getArguments() {
		return arguments;
	}

	public void setArguments(List<ExpressionNode> parameters) {
		this.arguments = parameters;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
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
		
		if (this.arguments != null) {
			System.out.println("parameters");
			
			for(ExpressionNode parameter : this.arguments) {
				parameter.dump(depth, true);
			}
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
