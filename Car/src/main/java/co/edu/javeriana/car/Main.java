
package co.edu.javeriana.car;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jpavlich.agent.App;
import org.jpavlich.agent.Executor;
import org.jpavlich.agent.model.Agent;
import org.jpavlich.agent.model.SimpleWorld;
import org.jpavlich.agent.model.World;
import org.jpavlich.agent.view.AppView;
import org.jpavlich.agent.view.DrawingView;
import org.jpavlich.agent.view.SpriteView;
import org.jpavlich.agent.view.WorldView;

import co.edu.javeriana.car.CarLexer;
import co.edu.javeriana.car.CarParser;

public class Main {

	private static final String EXTENSION = "car";

	public static void main(String[] args) {
		App<World, Car> app = initCar();
		app.run(Main::program);
	}

	private static void program(World world, Car car) {
		try {
			String program = "test/test." + EXTENSION;
			System.out.println("Interpreting file " + program);

			CarLexer lexer = new CarLexer(new ANTLRFileStream(program));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CarParser parser = new CarParser(tokens, car);
			
			car.move(400, 300);
			
			parser.program();

			System.out.println("Interpretation finished");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void program2(World world, Car car) {
		car.color(255, 0, 0, 255);
		car.forward(200);
		car.right(90);
		car.color(0, 255, 0, 255);
		car.forward(200);
		car.right(90);
		car.color(0, 0, 255, 255);
		car.forward(200);
		car.right(90);
		car.color(255, 255, 0, 255);
		car.forward(200);
		car.right(90);
		car.color(0, 255, 255, 255);
	}

	private static App<World, Car> initCar() {
		Executor executor = new Executor();
		SimpleWorld world = new SimpleWorld(800, 600);
		Agent agent = new Agent(world);
		Car car = new Car(agent, executor);
		WorldView worldView = new WorldView();
		DrawingView drawing = new DrawingView(agent);
		SpriteView<Agent> agentView = new SpriteView<>(agent, 1, "./images/car.png");
		AppView applet = new AppView(executor, 150, worldView, drawing, agentView);
		return new App<>(world, car, applet);
	}

}
