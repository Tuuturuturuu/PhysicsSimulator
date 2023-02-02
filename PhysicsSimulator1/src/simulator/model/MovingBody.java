package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{
	
	@Override
	void advance(double dt) {
<<<<<<< HEAD
		
		Vector2D ac = new Vector2D();
		
		if(mass != 0) {
			//REVISAR
			
			Double magnitudAc = ac.magnitude();
			
			magnitudAc = (force.magnitude() / mass);
			//CAMBIA LA POSICION
			pos = pos.plus(vel.scale(dt)).plus(ac.scale(1.0d / 2.0d).scale(dt*dt));
			//CAMBIA LA VELOCIDAD
			vel = vel.plus(ac.scale(dt));
			
		}
		
		
=======
		//HAY QUE CALCULAR PRIMERO LA ACELERACION O SOLP HACERLA EN LA LEY DE LA GRAVITACION Y AQUI SETEARLA SOLO
		//CAMBIA LA POSICION
		pos = pos.plus(vel.scale(dt)).plus(ac.scale(1.0d / 2.0d).scale(dt*dt));
		//CAMBIA LA VELOCIDAD
		vel = vel.plus(ac.scale(dt));
>>>>>>> 9cdf322804edc940e6539eee10c49ccc6b3667b2
	}

}
