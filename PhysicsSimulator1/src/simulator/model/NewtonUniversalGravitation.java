package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private Double g;
	// NO ES CTE, SE PASA COMO PARAMETRO EN EL CONTRUCTOR PARA LAS GS DE DISTINTOS
	// PLANETAS

	public NewtonUniversalGravitation(double g) {
		if (g <= 0) {
			throw new IllegalArgumentException("Parameter received is negative");
		}
		this.g = g;
	}

	public void apply(List<Body> bodies) {

		for (Body bi : bodies) {

			if (bi.getMass() != 0.0d) {

				Vector2D fi = new Vector2D();// ES LA FI TOTAL

				for (Body bj : bodies) {

					// DIS = |Pi-PJ|
					Double dis = bj.getPosition().distanceTo(bi.getPosition());

					if (dis > 0) {

						fi = fi.plus(bj.getPosition().minus(bi.getPosition()).direction().scale((g * bi.getMass() * bj.getMass() / (dis * (dis)))));

					}
					// NO HAY ELSE CON LA OPCIÓN DE (DIS == 0) PORQ SI LA DISTANCIA DIS (PJ-PI) ES
					// CERO, NO SE SUMA NINGUNA FUERZA, POR LO TANTO NO SE HACE NADA
				}

				bi.addForce(fi);

			} else {

				bi.resetForce();
				bi.resetVelocity();

			}
		}
	}
}
