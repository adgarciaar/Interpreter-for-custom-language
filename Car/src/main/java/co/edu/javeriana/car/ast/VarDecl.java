package co.edu.javeriana.car.ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VarDecl implements ASTNode {
	
	private String name;
	private ASTNode expression;

	public VarDecl(String name) {
		super();
		this.name = name;
		this.expression = null;
	}	

	public VarDecl(String name, ASTNode expression) {
		super();
		this.name = name;
		this.expression = expression;
	}
	
	@Override
	public Object execute( Map<String,Object> symbolTable ) {
		
		if( symbolTable.get(this.name) != null && symbolTable.get(this.name) instanceof Function){			
			
			System.out.println("Error: el nombre "+this.name+" no puede usarse para "
						+ "variable, ya que se declaró una función con ese nombre previamente");
			System.exit(1);
			
		}
		
		//si se está declarando y asignando en una misma línea
		if (this.expression != null){
			symbolTable.put(this.name, this.expression.execute(symbolTable));
			
		//si sólo se está declarando en la línea
		}else{
			symbolTable.put(this.name, new Character('a'));
		}
		
		//retornar una lista de caracteres con el nombre de la variable
		//con el propósito de reconocer cuándo se declara una variable
		//dentro de un bloque if, while o función y actualizarla únicamente
		//en el contexto local
		List<Character> varNameChars = new ArrayList<>();
		for (char ch : this.name.toCharArray()) { 			  
			varNameChars.add(ch); 
        } 
		return varNameChars;
	}

}
