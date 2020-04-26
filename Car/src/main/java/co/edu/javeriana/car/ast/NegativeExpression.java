package co.edu.javeriana.car.ast;

import java.util.Map;

public class NegativeExpression implements ASTNode {
	
	private ASTNode negExpression;

	public NegativeExpression(ASTNode negExpression) {
		super();
		this.negExpression = negExpression;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.negExpression.execute(symbolTable)==null ){	
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}
		
		if( !(this.negExpression.execute(symbolTable) instanceof Float) ){	
			System.out.println("Error: dato antecedido por un - debe ser numérico");
			System.exit(1);
		}
		
		return -(float)this.negExpression.execute(symbolTable);
		
	}

}
