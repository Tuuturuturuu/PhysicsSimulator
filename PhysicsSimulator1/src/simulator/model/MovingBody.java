package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	
	@Override
	void advance(double dt) {
		
		Vector2D ac;
		
		if(mass != 0.0d) {

			//CALCULA LA ACELERACION
			ac = force.scale(1.0d/mass);
			
			//CAMBIA LA POSICION
			pos = pos.plus(vel.scale(dt)).plus(ac.scale(1.0d / 2.0d).scale(dt*dt));
			
			//CAMBIA LA VELOCIDAD
			vel = vel.plus(ac.scale(dt));
		}
		
		
	}
}
