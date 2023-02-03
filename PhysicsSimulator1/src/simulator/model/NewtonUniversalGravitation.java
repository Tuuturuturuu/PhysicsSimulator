package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private final Double G = 6.67E-11d;

	private NewtonUniversalGravitation(Double g) {
		if (g < 0) {
			throw new IllegalArgumentException("Parameter received is negative");
		}
	}

	public void apply(List<Body> bodies) {

		for (Body bi : bodies) {

			if (bi.getMass() != 0.0d) {

				Vector2D fi = new Vector2D();

				for (Body bj : bodies) {

					// ESTO ES |pi-pj|, SI LOS VALORES DAN MAL ELIMINAR DIS Y METERLO EN LA FORMULA DE FI
					Double dis = bj.getPosition().distanceTo(bi.getPosition());

					if (dis > 0) {

						fi = fi.plus(bj.getPosition().minus(bi.getPosition()).direction()
								.scale((G * bi.getMass() * bj.getMass() / (dis * (dis)))));

					}
					//NO HAY ELSE CON LA OPCIÓN DE (DIS== 0) PORQ SI LA DISTANCIA DIS (PJ-PI) ES CERO, 
					//NO SE SUMA NINGUNA FUERZA, POR LO TANTO NO SE HACE NADA
				}

				bi.addForce(fi);

			} else {

				bi.resetForce();
				// LO DE RESETEAR LA ACELERACION ES UN TYPO? SE HACE QUI O SE HACE EN MOVING
				// BODY?
				// DEBERIA TENER ESTE SETTER/RESET EN BODY AUNQ NO VENGA EN EL UML? SI NO COMO
				// PONGO A 0 LA VELOCIDAD?
				bi.resetVelocity();

			}
		}
	}
}
