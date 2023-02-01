package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private Vector2D c;
	private Double g;
	
	MovingTowardsFixedPoint(Vector2D c, Double g){
		
		if (c == null || g < 0) {
			throw new IllegalArgumentException("invalid argument");
		}
		this.c = c;
		this.g = g;
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for( Body b : bodies) {
			//AÑADIR LA FUERZA
			//b.setAcceleration(b.pos.direction().scale(-g));
			// b tendria que ser MovingBody pues hi setteas  la acc
		}
	}

}
