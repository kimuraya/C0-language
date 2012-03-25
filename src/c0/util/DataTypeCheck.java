package c0.util;

import c0.ast.ExpressionNode;
import c0.ast.PlusNode;

public class DataTypeCheck {
	
	/**
	 * 二項演算子をチェックする
	 * @param expression
	 */
	private void binaryExpressionCheck(ExpressionNode expression) {
		switch(expression.getNodeType()) {
			case PLUS: //"+"
				PlusNode plusNode = (PlusNode) expression;
				
				
		}
	}
}
