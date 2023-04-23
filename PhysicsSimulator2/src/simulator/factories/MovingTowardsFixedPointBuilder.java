package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	private static final Double _g = 9.81d;
	private static final Vector2D _c = new Vector2D();

	public MovingTowardsFixedPointBuilder() {

		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {

		if (data.isEmpty())
			return new MovingTowardsFixedPoint(_c, _g);

		Double g;

		if (data.has("g"))
			g = data.getDouble("g");
		else
			g = _g;

		Vector2D c;

		if (data.has("c")) {
			JSONArray jac = data.getJSONArray("c");
			c = new Vector2D(jac.getDouble(0), jac.getDouble(1));

		} else
			c = _c;

		return new MovingTowardsFixedPoint(c, g);
	}

	public JSONObject getInfo() {

		JSONObject ret = super.getInfo();
		JSONObject data = new JSONObject();

		data.put("c", "the point towards which bodies move (e.g., [100.0,50.0])");
		data.put("g", "the length of the acceleration vector (a number)");

		ret.put("data", data);
		return ret;
	}

}
