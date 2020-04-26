package co.edu.javeriana.car.ast;

import java.util.Map;

public class PositiveExpression implements ASTNode {
	
	private ASTNode posExpression;

	public PositiveExpression(ASTNode posExpression) {
		super();
		this.posExpression = posExpression;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.posExpression.execute(symbolTable)==null ){	
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}
		
		if( !(this.posExpression.execute(symbolTable) instanceof Float) ){	
			System.out.println("Error: dato antecedido por un + debe ser numérico");
			System.exit(1);
		}
		
		return (float)this.posExpression.execute(symbolTable);
		
	}

}
