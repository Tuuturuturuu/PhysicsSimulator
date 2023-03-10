package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

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

		if (!jsonInput.has("bodies") || !jsonInput.has("groups"))
			throw new IllegalArgumentException(" There is data missing (bodies, groups or laws)");

		else {

			JSONArray jaG = jsonInput.getJSONArray("groups");

			// ADD GROUPS
			for (int k = 0; k < jaG.length(); ++k) {

				physicsSimulator.addGroup(jaG.getString(k));
			}

			JSONArray jaB = jsonInput.getJSONArray("bodies");

			// ADD BODIES
			for (int i = 0; i < jaB.length(); ++i) {
				Body b = factoryBodies.createInstance(jaB.getJSONObject(i));

				physicsSimulator.addBody(b);

			}

			if (jsonInput.has("laws")) {

				JSONArray jaL = jsonInput.getJSONArray("laws");
				// ADD FORCE LAW
				for (int j = 0; j < jaL.length(); ++j) {

					JSONObject joL = jaL.getJSONObject(j);

					physicsSimulator.setForceLaws(joL.getString("id"),
							factoryLaws.createInstance(joL.getJSONObject("laws")));

				}
			}
		}
	}
	
	//BATCH 
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
	
	// GUI RUN
	public void run (int n) {
		for(int i = 0; i < n; i++) 
			this.physicsSimulator.advance();
	}
	
	public void reset() { this.physicsSimulator.reset();}
	
	public void setDeltaTime(double dt) { this.physicsSimulator.setDeltaTime(dt);}
	
	public void addObserver(SimulatorObserver o) { this.physicsSimulator.addObserver(o);}
	
	public void removeObserver(SimulatorObserver o) { this.physicsSimulator.removeObserver(o);}
	
	public List<JSONObject> getForceLawsInfo(){ return this.factoryLaws.getInfo();}
	
	public void setForcesLaws(String gId, JSONObject info) {
		this.physicsSimulator.setForceLaws(gId, this.factoryLaws.createInstance(info));
	}
}
