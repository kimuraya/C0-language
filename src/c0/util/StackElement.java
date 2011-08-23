package c0.util;

public class StackElement {
	
	private StackElementType stackElementType = null; //スタックに積む際の要素の種類
	private LocalVariable variable = null; //変数
	private Value value = null; //戻り値と戻り先のアドレス
	private BasePointer basePointer = null; //ベースポインタ
	
	public StackElementType getStackElementType() {
		return stackElementType;
	}
	public void setStackElementType(StackElementType stackElementType) {
		this.stackElementType = stackElementType;
	}
	public LocalVariable getVariable() {
		return variable;
	}
	public void setVariable(LocalVariable variable) {
		this.variable = variable;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	public BasePointer getBasePointer() {
		return basePointer;
	}
	public void setBasePointer(BasePointer basePointer) {
		this.basePointer = basePointer;
	}
}
