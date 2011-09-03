package c0.interpreter;

import c0.ast.*;

public interface Visitor {

	void visit(AstNode astNode);
	void visit(ExpressionNode expressionNode);
	void visit(LiteralNode literalNode);
	void visit(IdentifierNode identifierNode);
	void visit(AssignNode assignNode);
	void visit(EquivalenceNode equivalenceNode);
	void visit(NotEquivalenceNode notEquivalenceNode);
	void visit(LessThanNode lessThanNode);
	void visit(LessThanOrEqualNode lessThanOrEqualNode);
	void visit(GreaterThanNode greaterThanNode);
	void visit(GreaterThanOrEqualNode greaterThanOrEqualNode);
	void visit(LogicalAndNode logicalAndNode);
	void visit(LogicalOrNode logicalOrNode);
	void visit(PlusNode plusNode);
	void visit(MinusNode minusNode);
	void visit(MulNode mulNode);
	void visit(DivNode divNode);
	void visit(ModNode modNode);
	void visit(ExclamationNode exclamationNode);
	void visit(UnaryMinusNode unaryMinusNode);
	void visit(PreIncrementNode preIncrementNode);
	void visit(PreDecrementNode preDecrementNode);
	void visit(PostIncrementNode postIncrementNode);
	void visit(PostDecrementNode postDecrementNode);
	void visit(CallNode callNode);
	void visit(StatementNode statementNode);
	void visit(BlockNode blockNode);
	void visit(IfNode ifNode);
	void visit(WhileNode whileNode);
	void visit(ForNode forNode);
	void visit(BreakNode breakNode);
	void visit(ReturnNode returnNode);
	void visit(ExpressionStatementNode expressionStatementNode);
	void visit(EmptyStatementNode emptyStatementNode);
	void visit(DeclareVariableNode declareVariableNode);
	void visit(ParameterNode parameterNode);
}
