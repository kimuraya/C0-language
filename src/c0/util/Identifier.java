package c0.util;

import java.util.List;

import c0.ast.BlockNode;
import c0.ast.IdentifierNode;

public class Identifier {
	
	private String name;                     //識別子の名前
	private IdentifierType identifierType;   //識別子の種類。変数か関数か
	private boolean standardFunctionFlag;   //組み込み関数か否かを判別する為のフラグ変数
	private LeftValue leftValue;             //グローバル変数の値
	private List<Identifier> parameters;     //引数のリスト
	
	public Identifier(String name) {
		super();
		this.name = name;
	}
	
	//getter, setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IdentifierType getIdentifierType() {
		return identifierType;
	}
	public void setIdentifierType(IdentifierType identifierType) {
		this.identifierType = identifierType;
	}
	public LeftValue getLeftValue() {
		return leftValue;
	}
	public void setLeftValue(LeftValue leftValue) {
		this.leftValue = leftValue;
	}
	public List<Identifier> getParameters() {
		return parameters;
	}
	public void setParameters(List<Identifier> parameters) {
		this.parameters = parameters;
	}
	public boolean isStandardFunctionFlag() {
		return standardFunctionFlag;
	}
	public void setStandardFunctionFlag(boolean standardFunctionFlag) {
		this.standardFunctionFlag = standardFunctionFlag;
	}
}
