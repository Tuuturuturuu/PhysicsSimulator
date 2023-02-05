package simulator.factories;

import org.json.JSONObject;

import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Body createInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return new StationaryBody();
	}

}
