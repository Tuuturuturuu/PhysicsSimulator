package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

	public MovingBodyBuilder() {

		super("mv_body", "Moving body");

	}

	@Override
	protected Body createInstance(JSONObject data) {
		
		if(!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m") || !data.has("v"))
			throw new IllegalArgumentException("Missing parameter in the creation of the body");
		
		String id = data.getString("id");
		String gid = data.getString("gid");

		JSONArray jav = data.getJSONArray("v");
		Vector2D v = new Vector2D(jav.getDouble(0), jav.getDouble(1));

		JSONArray jap = data.getJSONArray("p");
		Vector2D p = new Vector2D(jap.getDouble(0), jap.getDouble(1));

		Double m = data.getDouble("m");
		
		if(jap.length()!= 2 || jav.length()!= 2) throw new IllegalArgumentException("Vector is not 2D");

		return new MovingBody(id, gid, p, v, m);
	}

}
