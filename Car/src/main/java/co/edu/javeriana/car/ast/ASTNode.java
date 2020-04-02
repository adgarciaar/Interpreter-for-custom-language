package co.edu.javeriana.car.ast;

import java.util.Map;

public interface ASTNode {
	public Object execute( Map<String,Object> symbolTable );
}
