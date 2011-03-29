package c0.ast;

import java.util.List;

import c0.util.IdentifierType;
import c0.util.LeftValue;

//識別子
public class IdentifierNode extends ExpressionNode {
	
	private String name;                     //識別子の名前
	private IdentifierType identifierType;   //識別子の種類。変数か関数か。
	private LeftValue value;                 //グローバル変数の値
	private List<IdentifierNode> parameters; //引数のリスト
	private BlockNode functionBody;          //関数本体
	private boolean standardFunctionFlag;   //組み込み関数か否かを判別する為のフラグ変数

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
