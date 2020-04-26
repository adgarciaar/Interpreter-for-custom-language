package co.edu.javeriana.car.ast;

import java.util.Map;

public class EqualThan implements ASTNode {
	
	private ASTNode expression1;
	private ASTNode expression2;

	public EqualThan(ASTNode expression1, ASTNode expression2) {
		super();
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.expression1.execute(symbolTable)==null ||
				this.expression2.execute(symbolTable)==null ){
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}
		
		int dataType = 0;
		
		if( (this.expression1.execute(symbolTable) instanceof Float)
				&& (this.expression2.execute(symbolTable) instanceof Float)){	
			dataType = 1;
		}else{
			
			if( (this.expression1.execute(symbolTable) instanceof String)
					&& (this.expression2.execute(symbolTable) instanceof String)){
				dataType = 2;
			}else{
				
				if( (this.expression1.execute(symbolTable) instanceof Boolean)
						&& (this.expression2.execute(symbolTable) instanceof Boolean)){
					dataType = 3;
				}else{
					System.out.println("Error: comparación = no se puede realizar");
					System.exit(1);
				}
				
			}
			
		}
		
		boolean returnValue = false;
		
		switch(dataType) {
		
		  case 1:
			  
			if( (float)this.expression1.execute(symbolTable) == (float)this.expression2.execute(symbolTable) ){
				returnValue = true;
			}
			
		    break;
		    
		  case 2:
			  
			String str1 = (String)this.expression1.execute(symbolTable);
			String str2 = (String)this.expression2.execute(symbolTable);
		    
			if( str1.equals(str2) ){
				returnValue = true;
			}
			  
		    break;
		    
		  case 3:
			  
			if( (boolean)this.expression1.execute(symbolTable) == (boolean)this.expression2.execute(symbolTable) ){
				returnValue = true;
			}
			  
			break;
		}

		return returnValue;
		
	}

}
