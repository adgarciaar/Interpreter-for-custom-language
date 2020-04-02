package co.edu.javeriana.car.ast;

import java.util.Map;

public class EqualThan implements ASTNode {
	
	private ASTNode expression1;
	private ASTNode expression2;

	public EqualThan(ASTNode expression1, ASTNode expression2) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		if( this.expression1.execute(symbolTable) == this.expression2.execute(symbolTable) ){
			return true;
		}else{
			return false;
		}
	}

}
