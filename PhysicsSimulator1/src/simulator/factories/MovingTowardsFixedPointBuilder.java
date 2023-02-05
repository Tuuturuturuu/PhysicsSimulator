package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return new MovingTowardsFixedPoint();
	}

}
