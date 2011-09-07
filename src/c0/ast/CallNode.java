package c0.ast;

import java.util.List;

import c0.interpreter.Visitor;

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

	public ExpressionNode getFunction() {
		return function;
	}

	public void setFunction(ExpressionNode function) {
		this.function = function;
	}

	public List<ExpressionNode> getParameters() {
		return parameters;
	}

	public void setParameters(List<ExpressionNode> parameters) {
		this.parameters = parameters;
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
		
		if (this.parameters != null) {
			System.out.println("parameters");
			
			for(ExpressionNode parameter : this.parameters) {
				parameter.dump(depth, true);
			}
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
