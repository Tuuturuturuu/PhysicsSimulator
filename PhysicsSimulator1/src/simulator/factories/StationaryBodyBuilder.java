package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {

	public StationaryBodyBuilder() {

		super("st_body", "Stationary Body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m"))
			throw new IllegalArgumentException("Missing parameter in the creation of the body");
		

		String id = data.getString("id");
		String gid = data.getString("gid");

		JSONArray jap = data.getJSONArray("p");
		Vector2D p = new Vector2D(jap.getDouble(0), jap.getDouble(1));

		Double m = data.getDouble("m");
		
		if(jap.length()!= 2) throw new IllegalArgumentException("Vector is not 2D");
		
		return new StationaryBody(id, gid, p, m);

	}

}
