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

	public void loadData(InputStream in) {

		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		JSONArray jaB = jsonInput.getJSONArray("bodies");
		JSONArray jaL = jsonInput.getJSONArray("laws");
		JSONArray jaG = jsonInput.getJSONArray("groups");
		
		//ADD GROUPS		
		for (int k = 0; k < jaG.length(); ++k) {

			physicsSimulator.addGroup(jaG.getString(k));
		}
		//ADD BODIES
		for (int i = 0; i < jaB.length(); ++i) {
			Body b = factoryBodies.createInstance(jaB.getJSONObject(i));

			physicsSimulator.addBody(b);
			
		}
		
		//ADD FORCE LAW
		for (int j = 0; j < jaL.length(); ++j) {
			
			JSONObject joL = jaL.getJSONObject(j);

			physicsSimulator.setForceLaws(joL.getString("id"), factoryLaws.createInstance(joL.getJSONObject("laws")));
			
		}
	}

	public void run(int n, OutputStream out) {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		ja.put(this.physicsSimulator.getState());

		for (int i = 0; i < n; ++i) {

			physicsSimulator.advance();
			ja.put(physicsSimulator.getState());

		}

		jo.put("states", ja);

		PrintStream p = new PrintStream(out);
		p.println(jo.toString());
		p.close();
	}
}
