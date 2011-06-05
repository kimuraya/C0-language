package c0.ast;

import java.util.List;
import c0.util.NodeType;

//抽象構文木のルート
//グローバル変数の確保とmain関数の呼び出しを実行する
public class AstNode extends Node {
	private List<DeclareVariableNode> globalVariables; //グローバル変数のリスト
	private List<IdentifierNode> functions; //関数のリスト
	
	public AstNode(List<DeclareVariableNode> globalVariables,
			List<IdentifierNode> functions) {
		super();
		this.globalVariables = globalVariables;
		this.functions = functions;
	}	
}
