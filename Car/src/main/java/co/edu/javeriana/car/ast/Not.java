package co.edu.javeriana.car.ast;

import java.util.Map;

public class Not implements ASTNode {
	
	private ASTNode expression;

	public Not(ASTNode expression) {
		super();
		this.expression = expression;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( !(this.expression.execute(symbolTable) instanceof Boolean) ){	
			System.out.println("Error: el operador not sólo aplica a datos booleanos");
			System.exit(1);
		}
		
		return !(boolean)this.expression.execute(symbolTable);
	}

}
