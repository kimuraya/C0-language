package c0.util;

import c0.ast.StatementNode;

//値を管理するクラス
public class Value {
	
	private DataType dataType; //変数のデータ型
	private int integer; //整数
	private int[] integerArray; //整数の配列
	private String stringLiteral; //文字列定数
	private boolean booleanLiteral; //真偽値のリテラル
	private boolean[] booleanArray; //真偽値の配列
	private StatementNode returnAddress; //戻り先
	
	//getter, setter
	public DataType getDataType() {
		return dataType;
	}
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}
	public int getInteger() {
		return integer;
	}
	public void setInteger(int integer) {
		this.integer = integer;
	}
	public int[] getIntegerArray() {
		return integerArray;
	}
	public void setIntegerArray(int[] array) {
		this.integerArray = array;
	}
	public String getStringLiteral() {
		return stringLiteral;
	}
	public void setStringLiteral(String stringLiteral) {
		this.stringLiteral = stringLiteral;
	}
	public boolean isBooleanLiteral() {
		return booleanLiteral;
	}
	public void setBooleanLiteral(boolean booleanLiteral) {
		this.booleanLiteral = booleanLiteral;
	}
	public boolean[] getBooleanArray() {
		return booleanArray;
	}
	public void setBooleanArray(boolean[] booleanArray) {
		this.booleanArray = booleanArray;
	}
	public void setReturnAddress(StatementNode returnAddress) {
		this.returnAddress = returnAddress;
	}
	public StatementNode getReturnAddress() {
		return returnAddress;
	}
}
