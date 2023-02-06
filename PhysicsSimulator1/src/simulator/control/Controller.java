package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

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

	Controller(PhysicsSimulator pS, Factory<Body> fb, Factory<ForceLaws> fl) {
		this.physicsSimulator = pS;
		this.factoryBodies = fb;
		this.factoryLaws = fl;
	}

	public void loadData(InputStream in) {// LO PUEDO HACER YA CREO

		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
	}

	public void run(int n, OutputStream out) {// LO PUEDO HACER YA?

	}

}
