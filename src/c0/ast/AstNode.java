package c0.ast;

import java.util.List;

import c0.interpreter.Visitor;

/**
 * 抽象構文木のルート
 */
public class AstNode extends Node {
	
	private List<DeclareVariableNode> globalVariables; //外部変数のリスト
	private List<IdentifierNode> functions; //関数のリスト
	
	public AstNode(List<DeclareVariableNode> globalVariables,
			List<IdentifierNode> functions) {
		super();
		this.globalVariables = globalVariables;
		this.functions = functions;
	}

	//ルートを出力し、外部変数と関数を出力する処理を呼び出す
	@Override
	public void dump(int depth, boolean indentFlag) {
		
		System.out.println("AstNode");
		
		//外部変数の出力
		System.out.println("globalVariables");
		
		for(DeclareVariableNode var : globalVariables) {
			var.dump(depth, true);
		}
		
		//関数の出力
		System.out.println("functions");
		
		for(IdentifierNode function : functions) {
			function.dump(depth, true);
		}
	}

	public List<DeclareVariableNode> getGlobalVariables() {
		return globalVariables;
	}

	public void setGlobalVariables(List<DeclareVariableNode> globalVariables) {
		this.globalVariables = globalVariables;
	}

	public List<IdentifierNode> getFunctions() {
		return functions;
	}

	public void setFunctions(List<IdentifierNode> functions) {
		this.functions = functions;
	}
	
	public void accept(Visitor visitor) {
		visitor.visit(this);
    }
}
