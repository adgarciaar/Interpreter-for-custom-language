package co.edu.javeriana.car.ast;

import java.util.Map;

public class VarAssign implements ASTNode {
	
	private String name;
	private ASTNode expression;

	public VarAssign(String name, ASTNode expression) {
		super();
		this.name = name;
		this.expression = expression;
	}

	@Override
	public Object execute( Map<String,Object> symbolTable ) {
		
		if( symbolTable.get(this.name)==null ){
			System.out.println("Error: no se ha declarado la variable "+name);
			System.exit(1);
		}
		
		symbolTable.put(this.name, this.expression.execute(symbolTable));
		return null;
	}

}
