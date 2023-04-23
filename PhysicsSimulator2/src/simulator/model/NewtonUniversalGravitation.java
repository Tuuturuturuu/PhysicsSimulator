package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private Double g;

	public NewtonUniversalGravitation(double g) {
		if (g <= 0) {
			throw new IllegalArgumentException("Parameter received is negative");
		}
		this.g = g;
	}

	public void apply(List<Body> bodies) {

		for (Body bi : bodies) {

			if (bi.getMass() != 0.0d) {

				Vector2D fi = new Vector2D();

				for (Body bj : bodies) {

					Double dis = bj.getPosition().distanceTo(bi.getPosition());

					if (dis > 0) {

						fi = fi.plus(bj.getPosition().minus(bi.getPosition()).direction()
								.scale((g * bi.getMass() * bj.getMass() / (dis * (dis)))));

					}

				}

				bi.addForce(fi);

			} else {

				bi.resetForce();
				bi.resetVelocity();

			}
		}
	}

	public String toString() {
		return "Newton’s Universal Gravitation with G = " + g;
	}
}
