package co.edu.javeriana.car.ast;

import java.util.Map;

public class GreaterEqualThan implements ASTNode {
	
	private ASTNode expression1;
	private ASTNode expression2;

	public GreaterEqualThan(ASTNode expression1, ASTNode expression2) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( !(this.expression1.execute(symbolTable) instanceof Float) || 
				!(this.expression2.execute(symbolTable) instanceof Float ) ){	
			System.out.println("Error: la comparación >= sólo aplica a datos numéricos");
			System.exit(1);
		}
		
		if( (float)this.expression1.execute(symbolTable) >= (float)this.expression2.execute(symbolTable) ){
			return true;
		}else{
			return false;
		}
	}

}
