package co.edu.javeriana.car.ast;

import java.util.List;
import java.util.Map;

public class Function implements ASTNode {
	
	private String name;
	private List<String> parameters;
	private List<ASTNode> body;

	public Function(String name, List<String> parameters, List<ASTNode> body) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public List<ASTNode> getBody() {
		return body;
	}

	@Override
	public Object execute(Map<String, Object> symbolTable) {
		
		if( symbolTable.get(this.name) != null ){
			System.out.println("Error: ya se ha declarado una variable "
					+ "o función con el nombre "+this.name);
			System.exit(1);
		}
		
		symbolTable.put(this.name, this);
		return null;
	}

}
