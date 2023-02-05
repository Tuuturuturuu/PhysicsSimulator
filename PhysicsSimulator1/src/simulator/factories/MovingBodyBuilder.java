package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body>{

	public MovingBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Body createInstance(JSONObject data) {
		
	//String id, String gid, Vector2D v, Vector2D p, Double m
		//creaar un contrsuctor en moving body?

		return new MovingBody(data.getString("id"), data.getString("gid"),  new Vector2D(), new Vector2D(), data.getDouble("mass"));
	}

}
