package co.edu.javeriana.car;

import org.jpavlich.agent.Executor;
import org.jpavlich.agent.model.Agent;
import org.jpavlich.agent.model.AgentProxy;

public class Car extends AgentProxy<Agent> implements ICar {

	public Car(Agent agent, Executor executor) {
		super(agent, executor);
	}
}
