package c0.ast;

import java.util.List;

import c0.interpreter.Visitor;
import c0.util.NodeType;
import c0.util.SymbolTable;

/**
 * 複合文
 */
public class BlockNode extends StatementNode {
	
	private List<DeclareVariableNode> localVariables; //局所変数
	private List<StatementNode> statements; //複合文本体
	private BlockNode outerNestBlock; //外側の入れ子
	private SymbolTable symbolTable; //複合文のシンボルテーブル
	
	public BlockNode(Location loc, List<DeclareVariableNode> localVariables,
			List<StatementNode> statements) {
		super(loc);
		this.localVariables = localVariables;
		this.statements = statements;
		nodeType = NodeType.BLOCK_STATEMENT;
	}

	public List<DeclareVariableNode> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(List<DeclareVariableNode> localVariables) {
		this.localVariables = localVariables;
	}

	public List<StatementNode> getStatements() {
		return statements;
	}

	public void setStatements(List<StatementNode> statements) {
		this.statements = statements;
	}

	public BlockNode getOuterNestBlock() {
		return outerNestBlock;
	}

	public void setOuterNestBlock(BlockNode outerNestBlock) {
		this.outerNestBlock = outerNestBlock;
	}

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	@Override
	public void dump(int depth, boolean indentFlag) {
		
		depth++;
		
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("BlockNode");
		
		this.printFileNameAndLine(depth, indentFlag);
		
		//局所変数の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("localVariables");
		
		for(DeclareVariableNode var : localVariables) {
			var.dump(depth, true);
		}
		
		//ブロック文本体の出力
		if (indentFlag) {
			this.printIndent(depth);
		}
		
		System.out.println("statements");
		
		for(StatementNode statement : statements) {
			statement.dump(depth, true);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
