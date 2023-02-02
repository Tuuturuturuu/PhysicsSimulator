package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{

	private final Double G = 6.67E-11d;
	
	
	private NewtonUniversalGravitation(Double g) {
		if(g < 0) {
			throw new IllegalArgumentException("G received is negative");
		}
	}
	public void apply(List<Body> bodies) { //REVISAR METODO ENTERO 
		
		for(Body bi : bodies) {
			Vector2D aux = new Vector2D(bi.getPosition());
			for(Body bj: bodies) {
				if(bi.getMass() != 0) {
					Double aux1 = bj.getPosition().distanceTo(bi.getPosition());
					if(aux1 > 0) {
						aux = aux.plus(bj.getPosition().minus(bi.getPosition()).direction().scale((G * bi.getMass() * bj.getMass() / (aux1 * (aux1)))));
						//REVISAR OPERACION
					}
					else if(aux1 == 0.0d) {
						//FALTA PONER QUE SI ES CERO SE INICIALICE FIJ= 0,0
					}
				}
			}
		}
	}
}
