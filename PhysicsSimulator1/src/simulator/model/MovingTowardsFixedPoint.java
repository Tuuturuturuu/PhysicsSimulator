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
	
	public void apply(List<Body> bodies) { //REVISAR CON DIEGO
		for(Body bi : bodies) {
			Vector2D di = new Vector2D();
			di = di.direction().scale(-g);
			
		}
	}

}
