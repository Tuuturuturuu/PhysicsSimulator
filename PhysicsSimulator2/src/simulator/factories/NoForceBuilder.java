package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {

		super("nf", "No  force");

	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}
	
	public JSONObject getInfo() {
			JSONObject ret = super.getInfo();
			JSONObject data = new JSONObject();//DEJAS EL JSONOBJECT DATA VACIO?
			
			ret.put("data", data);
			return ret;
	}
}
