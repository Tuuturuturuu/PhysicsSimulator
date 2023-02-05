package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	
		//AQUI PONGO EL TIPO Y DESC DE LA LEY?
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}

}
