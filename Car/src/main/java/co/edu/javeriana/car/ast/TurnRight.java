package co.edu.javeriana.car.ast;

import java.util.Map;

import co.edu.javeriana.car.Car;

public class TurnRight implements ASTNode {
	
	private ASTNode numValue;
	private Car myCar;

	public TurnRight(ASTNode numValue, Car myCar) {
		super();
		this.numValue = numValue;
		this.myCar = myCar;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( !(this.numValue.execute(symbolTable) instanceof Float) ){	
			System.out.println("Error: el valor de giro debe ser numérico");
			System.exit(1);
		}
		
		this.myCar.right( (float) this.numValue.execute(symbolTable) );
		return null;
	}

}
