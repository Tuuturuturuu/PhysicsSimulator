package simulator.model;

import org.json.JSONObject;

public class PhysicsSimulator {
	
	PhysicsSimulator(Double t, ForceLaws fl ){
		if(t == null || fl == null)
			throw new IllegalArgumentException("");
	}
	
	public void advance(){
		
	}
	public void addGroup(String id){
		
	}
	public void addBody(Body b){
		
	}
	public void setForceLaws(String id, ForceLaws f) {
		
	}
	public JSONObject getState(){
		
		JSONObject jo = new JSONObject();
		//TERMINAR
		
		return jo;
	}
	public String toString() {
		return getState().toString();
	}
}
