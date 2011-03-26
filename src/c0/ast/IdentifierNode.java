package c0.ast;

import java.util.List;

//識別子
public class IdentifierNode extends ExpressionNode {
	
	private String name;                     //識別子
	private String identifierType;           //識別子の種類。変数か関数か。
	private int value;                         //グローバル変数の値。int型のままだと、他のデータ型に対応できない。検討が必要
	private List<IdentifierNode> parameters; //引数のリスト
	private BlockNode functionBody;          //関数本体
	private boolean standardFunctionFlag;   //組み込み関数か否かを判別する為のフラグ変数

	@Override
	public void dump() {
		// TODO 自動生成されたメソッド・スタブ

	}
}
