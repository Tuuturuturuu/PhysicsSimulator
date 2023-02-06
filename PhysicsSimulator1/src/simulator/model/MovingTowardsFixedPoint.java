package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private Vector2D c;
	private Double g;
	
	public MovingTowardsFixedPoint(Vector2D c, Double g){
		
		if (c == null || g < 0) {
			throw new IllegalArgumentException("Received invalid parameter");
		}
		this.c = c;
		this.g = g;
	}
	
	public void apply(List<Body> bodies) {
		
		for(Body bi : bodies) {
			
			Vector2D di = new Vector2D();
			Vector2D fi = new Vector2D();
			
			di = c.minus(bi.getPosition());
			
			//REVISAR, NO SE SI ES ASI:
			
			fi = di.scale(bi.getMass() * g);
			bi.addForce(fi);
			
			// O ASI:
			//bi.force = di.scale(bi.getMass() * g);
		}
	}

}
