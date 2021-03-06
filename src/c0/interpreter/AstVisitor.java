package c0.interpreter;

import java.util.LinkedList;
import java.util.List;

import c0.ast.ArraySubscriptExpressionNode;
import c0.ast.AssignNode;
import c0.ast.AstNode;
import c0.ast.BlockNode;
import c0.ast.BreakNode;
import c0.ast.CallNode;
import c0.ast.DataTypeNode;
import c0.ast.DeclareVariableNode;
import c0.ast.DivNode;
import c0.ast.EmptyStatementNode;
import c0.ast.EquivalenceNode;
import c0.ast.ExclamationNode;
import c0.ast.ExpressionNode;
import c0.ast.ExpressionStatementNode;
import c0.ast.ForNode;
import c0.ast.GreaterThanNode;
import c0.ast.GreaterThanOrEqualNode;
import c0.ast.IdentifierNode;
import c0.ast.IfNode;
import c0.ast.LessThanNode;
import c0.ast.LessThanOrEqualNode;
import c0.ast.LiteralNode;
import c0.ast.LogicalAndNode;
import c0.ast.LogicalOrNode;
import c0.ast.MinusNode;
import c0.ast.ModNode;
import c0.ast.MulNode;
import c0.ast.NotEquivalenceNode;
import c0.ast.ParameterNode;
import c0.ast.PlusNode;
import c0.ast.PostDecrementNode;
import c0.ast.PostIncrementNode;
import c0.ast.PreDecrementNode;
import c0.ast.PreIncrementNode;
import c0.ast.ReturnNode;
import c0.ast.StatementNode;
import c0.ast.UnaryMinusNode;
import c0.ast.WhileNode;
import c0.util.DataType;
import c0.util.GlobalScope;
import c0.util.IdentifierType;
import c0.util.LocalScope;
import c0.util.SymbolTable;

/**
 * プログラム全体の起点
 */
public class AstVisitor implements Visitor {
	
	private GlobalScope globalScope = null; //シンボルテーブル
	private String beingProcessedFunctionName = null; //現在処理中の関数名
	private BlockNode beingProcessedBlock = null; //現在処理中の複合文
	
	public AstVisitor(GlobalScope globalScope) {
		super();
		this.globalScope = globalScope;
	}

	@Override
	public void visit(AstNode astNode) {
		
		List<DeclareVariableNode> globalVariables = astNode.getGlobalVariables();
		
		for (DeclareVariableNode declareVariableNode : globalVariables) {
			declareVariableNode.accept(this);
		}
		
		List<IdentifierNode> functions = astNode.getFunctions();
		
		for (IdentifierNode function : functions) {
			function.accept(this);
		}
	}

	@Override
	public void visit(ExpressionNode expressionNode) {
		expressionNode.accept(this);
	}

	@Override
	public void visit(LiteralNode literalNode) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	/**
	 * 識別子をシンボルテーブルに登録する
	 * 変数と関数で処理を分ける
	 */
	public void visit(IdentifierNode identifierNode) {
		
		//関数を登録する場合
		if ((identifierNode.getIdentifier().getIdentifierType() == IdentifierType.FUNCTION) && (identifierNode.getBlock() != null)) {
			
			//シンボルテーブルに登録済みかチェックし、未登録の物を登録する
			if(!this.globalScope.getGlobalSymbolTable().searchSymbol(identifierNode.getIdentifier().getName())) {
				
				//実際の登録処理
				this.globalScope.getGlobalSymbolTable().addSymbol(identifierNode.getIdentifier());
				
				//System.out.println("関数の出力");
				//System.out.println(identifierNode.getIdentifier().getName());
			}
			
			//関数ごとにスコープを新規作成し、処理中の関数名を保存する
			LocalScope newFunctionScope = new LocalScope();
			newFunctionScope.setFunctionName(identifierNode.getIdentifier().getName());
			this.beingProcessedFunctionName = identifierNode.getIdentifier().getName();
			newFunctionScope.getLocalSymbolTableList().add(new SymbolTable()); //新しいスコープにシンボルテーブルを追加する
			
			this.globalScope.getFunctionScopeList().add(newFunctionScope);
			
			//引数と複合文の局所変数は新しいシンボルテーブルに登録する
			
			//引数がある場合
			if(identifierNode.getParameters() != null) {
				List<ParameterNode> Parameters = identifierNode.getParameters();
				for (ParameterNode parameterNode : Parameters) {
					parameterNode.accept(this);
				}
			}
			
			//複合文がある場合
			if(identifierNode.getBlock() != null) {
				this.beingProcessedBlock = null; //関数本体は外側の複合文を持たない為、リンクをnullにする
				identifierNode.getBlock().accept(this);
			}
		}
		
		//一番最初に処理する関数の引数をグローバル変数として登録してしまう
		
		//変数を登録する場合
		if (identifierNode.getIdentifier().getIdentifierType() == IdentifierType.VARIABLE) {
			
			boolean registeredFlag = false; //登録済みならtrue
			
			//ローカル変数のチェック
			//関数のスコープを1つずつチェックする
			addSymbol:
			for (LocalScope localScope : this.globalScope.getFunctionScopeList()) {
				
				//現在処理中の関数のスコープを手に入れる
				if (localScope.getFunctionName().equals(this.beingProcessedFunctionName)) {
					
					//シンボルテーブルに登録済みかチェックし、未登録の物を登録する
					//下位から上位のシンボルテーブルをチェックする
					for (int index = localScope.getLocalSymbolTableList().size();  index < 0; index--) {
						
						SymbolTable symbolTable = localScope.getLocalSymbolTableList().get(index);
						
						//登録済みなら、処理を止める
						if (symbolTable.searchSymbol(identifierNode.getIdentifier().getName())) {
							registeredFlag = true;
							break addSymbol;
						}
					}
				}
			}
			
			//関数のスコープにない場合、または関数がまだ登録されていなかった場合
			//大域変数のシンボルテーブルをチェックする
			if (!registeredFlag) {
				
				//大域変数に無いシンボルで、かつ、関数がまだ処理されていない場合のみ、登録を行う
				if (!this.globalScope.getGlobalSymbolTable().searchSymbol(identifierNode.getIdentifier().getName())
						&& this.beingProcessedFunctionName == null) {
					this.globalScope.getGlobalSymbolTable().addSymbol(identifierNode.getIdentifier());
					//System.out.println("グローバル変数の出力");
					//System.out.println(identifierNode.getIdentifier().getName());
				} else {
					//ここまで到達したら、局所変数にも大域変数にも登録されていない
					for (LocalScope localScope : this.globalScope.getFunctionScopeList()) {
						//現在処理中の関数のスコープを手に入れる
						if (localScope.getFunctionName().equals(this.beingProcessedFunctionName)) {
							LinkedList<SymbolTable> localSymbolTableList = localScope.getLocalSymbolTableList();
							localSymbolTableList.getLast().addSymbol(identifierNode.getIdentifier());
							//System.out.println("ローカル変数の出力");
							//System.out.println(identifierNode.getIdentifier().getName());
						}
					}
				}
			}
		}
	}

	@Override
	public void visit(AssignNode assignNode) {
		assignNode.getLeftValue().accept(this);
		assignNode.getExpression().accept(this);
	}

	@Override
	public void visit(EquivalenceNode equivalenceNode) {
		equivalenceNode.getLeft().accept(this);
		equivalenceNode.getRight().accept(this);
	}

	@Override
	public void visit(NotEquivalenceNode notEquivalenceNode) {
		notEquivalenceNode.getLeft().accept(this);
		notEquivalenceNode.getRight().accept(this);
	}

	@Override
	public void visit(LessThanNode lessThanNode) {
		lessThanNode.getLeft().accept(this);
		lessThanNode.getRight().accept(this);
	}

	@Override
	public void visit(LessThanOrEqualNode lessThanOrEqualNode) {
		lessThanOrEqualNode.getLeft().accept(this);
		lessThanOrEqualNode.getRight().accept(this);
	}

	@Override
	public void visit(GreaterThanNode greaterThanNode) {
		greaterThanNode.getLeft().accept(this);
		greaterThanNode.getRight().accept(this);
	}

	@Override
	public void visit(GreaterThanOrEqualNode greaterThanOrEqualNode) {
		greaterThanOrEqualNode.getLeft().accept(this);
		greaterThanOrEqualNode.getRight().accept(this);
	}

	@Override
	public void visit(LogicalAndNode logicalAndNode) {
		logicalAndNode.getLeft().accept(this);
		logicalAndNode.getRight().accept(this);
	}

	@Override
	public void visit(LogicalOrNode logicalOrNode) {
		logicalOrNode.getLeft().accept(this);
		logicalOrNode.getRight().accept(this);
	}

	@Override
	public void visit(PlusNode plusNode) {
		plusNode.getLeft().accept(this);
		plusNode.getRight().accept(this);
	}

	@Override
	public void visit(MinusNode minusNode) {
		minusNode.getLeft().accept(this);
		minusNode.getRight().accept(this);
	}

	@Override
	public void visit(MulNode mulNode) {
		mulNode.getLeft().accept(this);
		mulNode.getRight().accept(this);
	}

	@Override
	public void visit(DivNode divNode) {
		divNode.getLeft().accept(this);
		divNode.getRight().accept(this);
	}

	@Override
	public void visit(ModNode modNode) {
		modNode.getLeft().accept(this);
		modNode.getRight().accept(this);
	}

	@Override
	public void visit(ExclamationNode exclamationNode) {
		exclamationNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(UnaryMinusNode unaryMinusNode) {
		unaryMinusNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PreIncrementNode preIncrementNode) {
		preIncrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PreDecrementNode preDecrementNode) {
		preDecrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PostIncrementNode postIncrementNode) {
		postIncrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(PostDecrementNode postDecrementNode) {
		postDecrementNode.getLeftValue().accept(this);
	}

	@Override
	public void visit(CallNode callNode) {
		callNode.getFunction().accept(this);
		List<ExpressionNode> parameters = callNode.getArguments();
		
		if (parameters != null) {
			for (ExpressionNode parameter : parameters) {
				parameter.accept(this);
			}
		}
	}
	
	@Override
	public void visit(ArraySubscriptExpressionNode arraySubscriptExpressionNode) {
		arraySubscriptExpressionNode.getArray().accept(this);
		arraySubscriptExpressionNode.getIndex().accept(this);
	}	
	
	@Override
	public void visit(StatementNode statementNode) {
		statementNode.accept(this);
	}

	@Override
	public void visit(BlockNode blockNode) {
		
		//外側の複合文がある場合、リンクを更新する
		if (this.beingProcessedBlock != null) {
			blockNode.setOuterNestBlock(this.beingProcessedBlock);
		}
		
		//現在処理中の複合文を更新
		this.beingProcessedBlock = blockNode;
		
		//複合文に入る度、シンボルテーブルを追加する
		for (LocalScope localScope : this.globalScope.getFunctionScopeList()) {
			
			//現在処理中の関数のスコープを手に入れる
			if (localScope.getFunctionName().equals(this.beingProcessedFunctionName)) {
				
				//処理中のスコープにシンボルテーブルを追加する
				SymbolTable symbolTable = new SymbolTable();
				localScope.getLocalSymbolTableList().add(symbolTable);
				
				//複合文に局所変数のシンボルテーブルを保持させる
				blockNode.setSymbolTable(symbolTable);
			}
		}
		
		//局所変数が宣言されていた場合
		if (blockNode.getLocalVariables() != null) {
			List<DeclareVariableNode> localVariables = blockNode.getLocalVariables();
			
			for (DeclareVariableNode localVariable : localVariables) {
				localVariable.accept(this);
			}
		}
		
		//複合文の中に文がある場合
		if (blockNode.getStatements() != null) {
			List<StatementNode> statements = blockNode.getStatements();
			
			for (StatementNode statement : statements) {
				statement.accept(this);
			}
		}
		
	}
	
	@Override
	public void visit(IfNode ifNode) {
		ifNode.getConditionalExpression().accept(this);
		ifNode.getThenStatement().accept(this);
		if (ifNode.getElseStatement() != null) {
			ifNode.getElseStatement().accept(this);
		}
	}

	@Override
	public void visit(WhileNode whileNode) {
		whileNode.getConditionalExpression().accept(this);
		whileNode.getBodyStatement().accept(this);
	}

	@Override
	public void visit(ForNode forNode) {
		
		if (forNode.getInitializeExpression() != null) {
			forNode.getInitializeExpression().accept(this);
		}
		
		if (forNode.getConditionalExpression() != null) {
			forNode.getConditionalExpression().accept(this);
		}
		
		if (forNode.getUpdateExpression() != null) {
			forNode.getUpdateExpression().accept(this);
		}
		
		forNode.getBodyStatement().accept(this);
	}

	@Override
	public void visit(BreakNode breakNode) {
		// TODO あとで検討する
		
	}

	@Override
	public void visit(ReturnNode returnNode) {
		if (returnNode.getExpression() != null) {
			returnNode.getExpression().accept(this);
		}
	}

	@Override
	public void visit(ExpressionStatementNode expressionStatementNode) {
		expressionStatementNode.getExpression().accept(this);
	}

	@Override
	public void visit(EmptyStatementNode emptyStatementNode) {
		// TODO あとで検討する
		
	}

	@Override
	public void visit(DeclareVariableNode declareVariableNode) {
		
		//識別子のデータ型をセットする
		DataTypeNode dataTypeNode = declareVariableNode.getDataType();
		DataType dataType = dataTypeNode.getDataType();
		declareVariableNode.getIdentifier().getIdentifier().setDataType(dataType);
		
		declareVariableNode.getIdentifier().accept(this);
		
		if (declareVariableNode.getExpression() != null) {
			declareVariableNode.getExpression().accept(this);
		}
	}

	@Override
	public void visit(ParameterNode parameterNode) {
		
		//引数のデータ型をセットする
		DataTypeNode dataTypeNode = parameterNode.getDataType();
		DataType dataType = dataTypeNode.getDataType();
		parameterNode.getIdentifier().getIdentifier().setDataType(dataType);
		
		parameterNode.getIdentifier().accept(this);
	}

	public GlobalScope getGlobalScope() {
		return globalScope;
	}

	public void setGlobalScope(GlobalScope globalScope) {
		this.globalScope = globalScope;
	}
}
