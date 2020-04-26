package co.edu.javeriana.car.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conditional implements ASTNode {
	
	private ASTNode condition;
	private List<ASTNode> body;
	private List<ASTNode> elseBody;

	public Conditional(ASTNode condition, List<ASTNode> body, List<ASTNode> elseBody) {
		super();
		this.condition = condition;
		this.body = body;
		this.elseBody = elseBody;
	}
	
	@Override
	public Object execute( Map<String,Object> symbolTable ) {
		
		if( !(this.condition.execute(symbolTable) instanceof Boolean) ){	
			System.out.println("Error: la condición para el if debe ser booleana");
			System.exit(1);
		}
		
		//crear tabla de símbolos para manejo de variables local
		//y mapa para almacenar las variables que se declaran localmente
		
		Map<String, Object> localSymbolTable = new HashMap<String, Object>(symbolTable);
		Map<String, Object> localDeclaredSymbol = new HashMap<String, Object>();
		
		boolean retorno = false;
		Object retornoFuncion = null; 
		
		//ejecución de las sentencias
		
		if( (boolean)condition.execute( symbolTable ) ){
			
			for(ASTNode n: body){
				
				//n.execute( localSymbolTable );
				Object object = n.execute( localSymbolTable );
				
				if( object != null ){
				
					//si la sentencia ejecutó una declaración
					//(esto se conoce si object es de tipo List)
					if (object instanceof List){
						List<Character> varNameChars = (List<Character>)object;
						String varName = "";
						for (int i = 0; i < varNameChars.size(); i++) {
							varName = varName + varNameChars.get(i);
						}
						localDeclaredSymbol.put(varName, "Declared");
						//System.out.println("Variable declarada localmente: "+varName);
					}else{
						//la sentencia es un retorno
						retorno = true;
						retornoFuncion = object;
						break;
					}
				
				}
			}
			
		}else{
			for(ASTNode n: elseBody){
				
				//n.execute( localSymbolTable );
				Object object = n.execute( localSymbolTable );
				
				if( object != null ){
				
					//si la sentencia ejecutó una declaración
					//(esto se conoce si object es de tipo List)
					if (object instanceof List){
						List<Character> varNameChars = (List<Character>)object;
						String varName = "";
						for (int i = 0; i < varNameChars.size(); i++) {
							varName = varName + varNameChars.get(i);
						}
						localDeclaredSymbol.put(varName, "Declared");
						//System.out.println("Variable declarada localmente: "+varName);
					}else{
						//la sentencia es un retorno
						retorno = true;
						retornoFuncion = object;
						break;
					}
				
				}
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
		
		if (retorno = true){
			return retornoFuncion;
		}else{
			return null;
		}
	}

}
