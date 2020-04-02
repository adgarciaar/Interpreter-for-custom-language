package co.edu.javeriana.car.ast;

import java.util.Map;

public class Or implements ASTNode {
	
	private ASTNode expression1;
	private ASTNode expression2;

	public Or(ASTNode expression1, ASTNode expression2) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( !(this.expression1.execute(symbolTable) instanceof Boolean) || 
				!(this.expression2.execute(symbolTable) instanceof Boolean ) ){	
			System.out.println("Error: la comparación or sólo aplica a datos booleanos");
			System.exit(1);
		}
		
		return ( (boolean)this.expression1.execute(symbolTable) 
				|| (boolean)this.expression2.execute(symbolTable) );
		
	}

}
