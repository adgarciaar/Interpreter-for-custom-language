package co.edu.javeriana.car.ast;

import java.util.List;
import java.util.Map;

public class Procedure implements ASTNode {
	
	private String name;
	private List<String> parameters;
	private List<ASTNode> body;

	public Procedure(String name, List<String> parameters, List<ASTNode> body) {
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
		symbolTable.put(this.name, this);
		return null;
	}

}
