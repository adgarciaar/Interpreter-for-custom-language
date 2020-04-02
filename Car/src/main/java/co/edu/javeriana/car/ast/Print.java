package co.edu.javeriana.car.ast;

import java.util.Map;

public class Print implements ASTNode {
	
	private ASTNode data;

	public Print(ASTNode data) {
		super();
		this.data = data;
	}

	@Override
	public Object execute( Map<String,Object> symbolTable ) {
		System.out.println( data.execute( symbolTable ) );
		return null;
	}

}
