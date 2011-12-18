package c0.util;

//ローカル変数をシンボルテーブルや環境に配備する
public class LocalVariable {
	
	Identifier variable; //変数名
	Value value; //値
	
	public Identifier getVariable() {
		return variable;
	}
	public void setVariable(Identifier variable) {
		this.variable = variable;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
}
