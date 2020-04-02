package co.edu.javeriana.car.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteProcedure implements ASTNode {
	
	private String name;
	private List<ASTNode> arguments;

	public ExecuteProcedure(String name, List<ASTNode> arguments) {
		super();
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		//recuperar el objeto del procedimiento
		
		Procedure procedure = (Procedure)symbolTable.get(this.name);
		List<String> parameters = procedure.getParameters();
		List<ASTNode> body = procedure.getBody();
		
		if( parameters.size() != this.arguments.size() ) {
			System.out.println("Error: número de argumentos inválido para el procedimiento "+this.name);
			System.exit(1);
		}	
		
		//crear tabla de símbolos para manejo de variables local
		//y mapa para almacenar las variables que se declaran localmente
								
		Map<String, Object> localSymbolTable = new HashMap<String, Object>(symbolTable);
		Map<String, Object> localDeclaredSymbol = new HashMap<String, Object>();
			
		//agregar los parámetros y argumentos al mapa con variables locales
		//si algún parámetro se llama igual que una variable del contexto superior
		//entonces ésta se reemplaza por el valor del argumento respectivo
		int i = 0;			
		for( ASTNode n : this.arguments ){
			localSymbolTable.put(parameters.get(i), n.execute(symbolTable));
			localDeclaredSymbol.put(parameters.get(i), "Declared");
			i++;
		}
		
		//ejecutar las sentencias			
		for( ASTNode n : body ){
			//n.execute(localSymbolTable);
			Object object = n.execute( localSymbolTable );
			
			//si la sentencia ejecutó una declaración
			if (object instanceof List){
				List<Character> varNameChars = (List<Character>)object;
				String varName = "";
				for (int j = 0; j < varNameChars.size(); j++) {
					varName = varName + varNameChars.get(j);
				}
				localDeclaredSymbol.put(varName, "Declared");
				//System.out.println("Variable declarada localmente: "+varName);
			}
		}
		
		//actualizar variables del nivel superior inmediatamente anterior
		
		for( Map.Entry<String, Object> entry : localSymbolTable.entrySet() ) {
			
			//si la variable no se declaró localmente 			
			//y si esa variable existe en la tabla de símbolos del contexto superior
					
			//entonces se puede actualizar una variable del mismo nombre 
			//en el contexto inmediatamente superior
					
			if ( localDeclaredSymbol.get( entry.getKey() ) == null 
					&& symbolTable.get( entry.getKey() ) != null ){
				symbolTable.put( entry.getKey(), entry.getValue() );
			}			
					
		}
		
		return null;
	}

}
