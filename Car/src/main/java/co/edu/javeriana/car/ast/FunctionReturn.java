package co.edu.javeriana.car.ast;

import java.util.Map;

public class FunctionReturn implements ASTNode {
	
	private ASTNode data;

	public FunctionReturn(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		Object object = data.execute( symbolTable );
		
		if( object == null ){
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}		
		
		return object;
		
	}

}
