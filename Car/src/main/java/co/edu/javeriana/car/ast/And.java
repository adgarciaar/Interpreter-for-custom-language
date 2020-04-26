package co.edu.javeriana.car.ast;

import java.util.Map;

public class And implements ASTNode {
	
	private ASTNode expression1;
	private ASTNode expression2;

	public And(ASTNode expression1, ASTNode expression2) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.expression1.execute(symbolTable)==null ||
				this.expression2.execute(symbolTable)==null ){
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}
		
		if( !(this.expression1.execute(symbolTable) instanceof Boolean) || 
				!(this.expression2.execute(symbolTable) instanceof Boolean ) ){	
			System.out.println("Error: la comparación and sólo aplica a datos booleanos");
			System.exit(1);
		}
		
		return ( (boolean)this.expression1.execute(symbolTable) 
				&& (boolean)this.expression2.execute(symbolTable) );
	}

}
