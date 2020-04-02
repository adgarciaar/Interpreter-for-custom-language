package co.edu.javeriana.car.ast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class While implements ASTNode {
	
	private ASTNode condition;
	private List<ASTNode> body;

	public While(ASTNode condition, List<ASTNode> body) {
		super();
		this.condition = condition;
		this.body = body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( !(this.condition.execute(symbolTable) instanceof Boolean) ){	
			System.out.println("Error: la condición para el if debe ser booleana");
			System.exit(1);
		}
		
		//crear tabla de símbolos para manejo de variables local
		//y mapa para almacenar las variables que se declaran localmente
				
		Map<String, Object> localSymbolTable = new HashMap<String, Object>(symbolTable);
		Map<String, Object> localDeclaredSymbol = new HashMap<String, Object>();
		
		
		//ejecutar el ciclo while
		
		while( (boolean)condition.execute( symbolTable ) ){
			
			//ejecución de las sentencias
			
			for(ASTNode n: body){
				//n.execute( symbolTable );
				Object object = n.execute( localSymbolTable );
				
				//si la sentencia ejecutó una declaración
				if (object instanceof List){
					List<Character> varNameChars = (List<Character>)object;
					String varName = "";
					for (int i = 0; i < varNameChars.size(); i++) {
						varName = varName + varNameChars.get(i);
					}
					localDeclaredSymbol.put(varName, "Declared");
					//System.out.println("Variable declarada localmente: "+varName);
				}
			}
			
			//actualizar variables del nivel superior inmediatamente anterior
			//en caso de que se requieran para la condición del while
			//y también para modificarlas acorde a los cambios sufridos
			
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
		}
		
		return null;
	}

}
