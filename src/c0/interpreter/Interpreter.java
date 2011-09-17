package c0.interpreter;

public interface Interpreter {
	
	//文の処理
	void executeStatement(); //複合文の中で使用する
	void executeBlockStatement(); //複合文
	void executeIfStatement(); //if文
	void executeWhileStatement(); //while文
	void executeForStatement(); //for文
	void executeBreakStatement(); //break文
	void executeReturnStatement(); //return文
	void executeExpressionStatement(); //式文
	void executeEmptyStatement(); //空文
	void executeParameter(); //引数
	void executeDeclareVariable(); //変数宣言
	
	//式の処理
	
	//一次式
	void IntExpression(); //整数定数
	void IdentifierExpression();//識別子
	void StringExpression(); //文字列定数
	
	//二項演算子
	void PlusExpression(); //加算式
	void MinusExpression(); //減算式
	void DivExpression(); //除算式
	void ModExpression(); //剰余式
	void MulExpression(); //乗算式
	void AssignExpression(); //代入式
	
	//関係式
	void LessThanExpression(); //小なり比較演算子
	void LessThanOrEqualExpression(); //以下比較演算子
	void GreaterThanExpression(); //大なり比較演算子
	void GreaterThanOrEqualExpression(); //以上比較演算子
	
	//同等式
	void EquivalenceExpression(); //同等演算子
	void NotEquivalenceExpression(); //不等演算子
	
	//論理式
	void LogicalAndExpression(); //条件積演算子
	void LogicalOrExpression(); //条件和演算子
	
	//単項式
	void UnaryMinusExpression(); //単項マイナス式
	void ExclamationExpression(); //論理否定演算子
	void PreIncrementExpression(); //前置増分式
	void PreDecrementExpression(); //前置減分式
	
	//後置き式
	void ArraySubscriptExpression(); //添字式
	void executeFunctionCall(); //関数呼び出し（ネイティブ関数を含む）
	void PostIncrementExpression(); //後置増分式
	void PostDecrementExpression(); //後置減分式
}
