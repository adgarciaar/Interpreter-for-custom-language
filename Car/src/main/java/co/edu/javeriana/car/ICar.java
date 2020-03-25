package co.edu.javeriana.car;

public interface ICar {
	void color(float red, float green, float blue, float alpha);

	void forward(float amount);

	void backwards(float amount);

	void left(float degrees);
	
	void right(float degrees);
}
