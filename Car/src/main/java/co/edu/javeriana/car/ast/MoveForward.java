package co.edu.javeriana.car.ast;

import java.util.Map;

import co.edu.javeriana.car.Car;

public class MoveForward implements ASTNode {
	
	private ASTNode numValue;
	private Car myCar;

	public MoveForward(ASTNode numValue, Car myCar) {
		super();
		this.numValue = numValue;
		this.myCar = myCar;
	}
	
	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.numValue.execute(symbolTable) == null ){	
			System.out.println("Error: función usada como expresión no tiene retorno");
			System.exit(1);
		}
		
		if( !( this.numValue.execute(symbolTable) instanceof Float) ){	
			System.out.println("Error: el valor de movimiento debe ser numérico");
			System.exit(1);
		}
		
		this.myCar.forward( (float) this.numValue.execute(symbolTable) );
		return null;
	}

}
