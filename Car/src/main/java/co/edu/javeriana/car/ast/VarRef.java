package co.edu.javeriana.car.ast;

import java.util.Map;

public class VarRef implements ASTNode {
	
	private String name;

	public VarRef(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public Object execute( Map<String,Object> symbolTable ) {
		
		if(symbolTable.get(name) == null){
			System.out.println("Error: no se ha declarado la variable "+name);
			System.exit(1);
		}
		
		if( symbolTable.get(this.name) instanceof Character ){
			System.out.println("Error: no se ha asignado la variable "+name);
			System.exit(1);
		}
		
		return symbolTable.get(name);
	}

}
