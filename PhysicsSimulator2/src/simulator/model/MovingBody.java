package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {

	public MovingBody(String id, String gid, Vector2D p, Vector2D v, Double m) {
		super(id, gid, p, v, m);
	}

	@Override
	void advance(double dt) {

		Vector2D ac;

		if (mass != 0.0d) {

			// CALCULA LA ACELERACION
			ac = force.scale(1.0d / mass);

			// CAMBIA LA POSICION
			pos = pos.plus(vel.scale(dt).plus(ac.scale(0.5 * dt * dt)));

			// CAMBIA LA VELOCIDAD
			vel = vel.plus(ac.scale(dt));
		}

	}
}
