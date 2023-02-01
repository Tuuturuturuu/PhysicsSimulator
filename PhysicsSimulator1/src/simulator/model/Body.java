package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	
	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D p;
	protected Vector2D f;
	protected Double m;
	
	 Body(){
		 
	 }
	 
	 Body(String id, String gid, Vector2D v, Vector2D p, Double m ){
		 this.id = id;
		 this.gid = gid;
		 this.v = v;
		 this.p = p;
		 this.m = m;
		 
		 //NO SE COMO ACTUALIZAR F A (0,0)
		 //NO SE COMO HACER LA EXCEPCION QUE LANZA EL CONSTRUCTOR
	 }
	 
	public String getId() {
		return id;
	}
	public String getgId() {
		return gid;
	}
	public Vector2D getVelocity() {
		return v;
	}
	public Vector2D getForce() {
		return f;
	}
	public Vector2D getPosition() {
		return p;
	}
	public double getMass() {
		return m;
	}
	
	void addForce(Vector2D f) {
		f.plus(f);
		//NO SE SI HACE FALTA MAS
	}
	void resetForce() {
		//NO SE COMO PONER EL VALOR DEL VECTOR DE FUERZA A (0,0)
	}
	
	//QUEDA ASI? IMPLEMENTACION EN MOVINGBODY?
	abstract void advance(double dt);
	
	public JSONObject getState() {
		
		JSONObject jo = new JSONObject();
		
		jo.put("id",this.id);
		jo.put("m",this.m);
		jo.put("p",this.p);
		jo.put("v",this.v);
		jo.put("f",this.f);
		
		return jo;
	}
	public String toString() {
		return getState().toString();
		
	}
}
