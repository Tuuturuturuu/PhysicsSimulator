package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {
	private PhysicsSimulator physicsSimulator;
	Factory<Body> factoryBodies;
	Factory<ForceLaws> factoryLaws;

	public Controller(PhysicsSimulator pS, Factory<Body> fb, Factory<ForceLaws> fl) {
		this.physicsSimulator = pS;
		this.factoryBodies = fb;
		this.factoryLaws = fl;
	}

	public void loadData(InputStream in) {//PREGUNTAR A DIEGO

		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		 JSONArray jaG = jsonInput.getJSONArray("groups");
		 JSONArray jaL = jsonInput.getJSONArray("laws");
		 JSONArray jaB = jsonInput.getJSONArray("bodies");
		 
		 for(int i = 0; i < jaB.length(); ++i) {
			 
			 physicsSimulator.addBody(factoryBodies.createInstance(jaB.getJSONObject(i)));
		 }
		 for(int j = 0; j < jaL.length(); ++j) {
			 
			 physicsSimulator.setForceLaws(jaL.getString("id"), jaL.);
		 }
		 for(int i = 0; i < jaB.length(); ++i) {
			 
			 physicsSimulator.addGroup(factory.createInstance(jaG.getJSONObject(i)));
		 }
	}

	public void run(int n, OutputStream out) {
		 JSONObject jo = new JSONObject();
		 JSONArray ja = new JSONArray();
		 
		 ja.put(this.physicsSimulator.getState());
		 
		 for(int i = 0; i < n; ++i) {
			 
			 physicsSimulator.advance();
			 ja.put(physicsSimulator.getState());
			 
		 }
		 
		 jo.put("states", ja);

		 PrintStream ps = new PrintStream(out);
		 ps.println(jo.toString());
		 ps.close();
	}
}
