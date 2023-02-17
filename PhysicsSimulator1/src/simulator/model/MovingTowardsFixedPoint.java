package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	private Vector2D c;
	private Double g;

	public MovingTowardsFixedPoint(Vector2D c, double g) {

		if (c == null || g <= 0) {
			throw new IllegalArgumentException("Received invalid parameter");
		}
		this.c = c;
		this.g = g;
	}

	public void apply(List<Body> bodies) {

		for (Body bi : bodies) {

			Vector2D di = c.minus(bi.getPosition()).direction();
			Vector2D fi = new Vector2D();


			fi = di.scale(bi.getMass() * g);

			bi.addForce(fi);

		}
	}

}
