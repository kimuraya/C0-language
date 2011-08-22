package c0.util;

public class StackElement {
	StackElementType stackElementType; //スタックに積む際の要素の種類
	LocalVariable variable; //変数
	Value value; //戻り値と戻り先のアドレス
	BasePointer basePointer; //ベースポインタ
}
