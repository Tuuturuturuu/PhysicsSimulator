package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		
		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {

		JSONArray jac = data.getJSONArray("c");
		Vector2D c = new Vector2D(jac.getInt(0), jac.getInt(1));

		Double g = data.getDouble("g");
		
		return new MovingTowardsFixedPoint(c, g);
	}

}
