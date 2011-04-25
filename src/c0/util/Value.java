package c0.util;

//値を管理するクラス
public class Value {
	
	private DataType dataType; //変数のデータ型
	private int integer; //整数
	private int[] array; //整数の配列
	private String stringLiteral; //文字列定数
	
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
	public int[] getArray() {
		return array;
	}
	public void setArray(int[] array) {
		this.array = array;
	}
	public String getStringLiteral() {
		return stringLiteral;
	}
	public void setStringLiteral(String stringLiteral) {
		this.stringLiteral = stringLiteral;
	}
}
