package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class BodiesGroup {
	private String gid;
	private ForceLaws laws;
	private List<Body> bodyList;
	
	BodiesGroup(){
		
	}
	
	BodiesGroup(String gid, ForceLaws laws){
		
		if (gid == null || laws == null || gid.trim().length()>0 ) {
			
			throw new IllegalArgumentException("illegal argument");
		}
		
		this.gid = gid;
		this.laws = laws;
	}
	
	public String getId() {
		return this.gid;
	}
	void setForceLaws(ForceLaws fl) {
		this.laws = fl;
	}
	
	void addBody(Body b) {
		//QUEDA POR HACER
	}
	void advance(double dt) {
		//QUEDA POR HACER
	}
	
	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();

		jo1.put("id", this.gid);
		jo1.put("bodies", this.bodyList);
		//NO ESTOY SEGURA DE QUE SEA ASI EL METER LA LISTA DE CUERPOS

		return jo1;
	}
	
	public String toString() {
		return  getState().toString();
	}
}
