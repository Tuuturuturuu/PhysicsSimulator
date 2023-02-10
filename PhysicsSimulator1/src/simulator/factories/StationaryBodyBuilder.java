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

		String id = data.getString("id");
		String gid = data.getString("gid");

		JSONArray jap = data.getJSONArray("p");
		Vector2D p = new Vector2D(jap.getDouble(0), jap.getDouble(1));

		Double m = data.getDouble("m");

		return new StationaryBody(id, gid, p, m);

	}

}
