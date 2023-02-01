package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	private Vector2D a;
	@Override
	void advance(double dt) {
		//HAY QUE CALCULAR PRIMERO LA ACELERACION
			 if (m == 0) {
				 
			 }
			 else
		
		//CAMBIA LA POSICION
		p = p.plus(v.scale(dt)).plus(a.scale(1.0d / 2.0d).scale(dt*dt));
		//CAMBIA LA VELOCIDAD
		v = v.plus(a.scale(dt));
	}

}
