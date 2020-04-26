package co.edu.javeriana.car.ast;

import java.util.Map;

import co.edu.javeriana.car.Car;

public class SetColor implements ASTNode {
	
	private ASTNode color;
	private ASTNode transparency;
	private Car myCar;

	public SetColor(ASTNode color, ASTNode transparency, Car myCar) {
		super();
		this.color = color;
		this.transparency = transparency;
		this.myCar = myCar;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( this.color.execute(symbolTable)==null ){
			System.out.println("Error: función usada como expresión de color no tiene retorno");
			System.exit(1);
		}
		if( this.transparency.execute(symbolTable)==null ){
			System.out.println("Error: función usada como expresión de transparencia no tiene retorno");
			System.exit(1);
		}
		
		if( !(this.transparency.execute(symbolTable) instanceof Float) ){	
			System.out.println("Error: el valor de transparencia debe ser numérico");
			System.exit(1);
		}
		
		if( (float)this.transparency.execute(symbolTable) < 0 
				|| (float)this.transparency.execute(symbolTable) > 1 ){
			System.out.println("Error: el valor de transparencia debe estar entre 0.0 y 1.0");
			System.exit(1);
		}
		
		if( !(this.color.execute(symbolTable) instanceof String) ){	
			System.out.println("Error: color incorrecto (tipo de dato incorrecto)");
			System.exit(1);
		}
		
		String pattern = "#[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]";
		String myColor = (String)this.color.execute(symbolTable);
		if( myColor.matches(pattern) == false ){
			System.out.println("Error: color incorrecto");
			System.exit(1);
		}
		
		String sColor = (String)this.color.execute(symbolTable);
		
		float red = (float)Integer.valueOf( sColor.substring( 1, 3 ), 16 );
		float green = (float)Integer.valueOf( sColor.substring( 3, 5 ), 16 );
		float blue = (float)Integer.valueOf( sColor.substring( 5, 7 ), 16 );
		float alpha = (float)this.transparency.execute(symbolTable);
		
		this.myCar.color( red, green, blue, alpha*255 );
		
		return null;
	}

}
