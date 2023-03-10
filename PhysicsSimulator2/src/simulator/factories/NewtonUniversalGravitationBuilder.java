package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	private static final Double G = 6.67E-11d;
	

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newtons law of universal gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		// System.out.println(data);
		if (data.isEmpty() || data == null)
			return new NewtonUniversalGravitation(G);
		else
			return new NewtonUniversalGravitation(data.getDouble("G"));
	}
	
	public JSONObject getInfo() {
		
		JSONObject data = new JSONObject();
		
		data.put("G",  "the gravitational constant (a number)");
		
		return data;
	}

}
