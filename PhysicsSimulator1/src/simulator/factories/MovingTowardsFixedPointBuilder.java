package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	private static final Double g = 9.81d;
	private static final Vector2D c = new Vector2D();

	public MovingTowardsFixedPointBuilder() {

		super("mtfp", "Moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		
		if(data.isEmpty())
			return new MovingTowardsFixedPoint(c, g);
		
		else if( !data.has("c") ) {
			
			
			Double g = data.getDouble("g");
			return new MovingTowardsFixedPoint(c, g);
		}
			
		else if(!data.has("g")) {
			JSONArray jac = data.getJSONArray("c");
			Vector2D c = new Vector2D(jac.getInt(0), jac.getInt(1));
			return new MovingTowardsFixedPoint(c, g);

		}
		else {
			JSONArray jac = data.getJSONArray("c");
			Vector2D c = new Vector2D(jac.getInt(0), jac.getInt(1));
	
			Double g = data.getDouble("g");
	
			return new MovingTowardsFixedPoint(c, g);
		}
	}

}
