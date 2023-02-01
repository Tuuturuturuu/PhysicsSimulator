package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	private Vector2D ac;
	
	@Override
	void advance(double dt) {
		//HAY QUE CALCULAR PRIMERO LA ACELERACION O SOLP HAVERLA EN LA LEY DE LA GRAVITACION Y AQUI SETEARLA SOLO
			Vector2D acc = new NewtonUniversalGravitation.apply();
		//CAMBIA LA POSICION
		pos = pos.plus(vel.scale(dt)).plus(ac.scale(1.0d / 2.0d).scale(dt*dt));
		//CAMBIA LA VELOCIDAD
		vel = vel.plus(ac.scale(dt));
	}
	
	void setAcceleration(Vector2D a) {
		 this.ac = a;
		 
	 }

}
