package c0.util;

import java.util.List;

import c0.ast.BlockNode;
import c0.ast.IdentifierNode;

public class Identifier {
	private String name;                     //識別子の名前
	private IdentifierType identifierType;   //識別子の種類。変数か関数か。
	private LeftValue leftValue;                 //グローバル変数の値
	private List<Identifier> parameters;     //引数のリスト
	private BlockNode functionBody;          //関数本体
	private boolean standardFunctionFlag;   //組み込み関数か否かを判別する為のフラグ変数
}
