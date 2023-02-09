package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		
		super( "nlug", "Newtons law of universal gravitation");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		
		return new NewtonUniversalGravitation(data.getDouble("G"));
	}

}
