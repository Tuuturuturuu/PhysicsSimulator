
package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.model.Body;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	public BuilderBasedFactory() {
		// Create a HashMap for _builders, a LinkedList _buildersInfo

	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> b : builders) {
			// NO SE SI ES ASI
		}

		// call addBuilder(b) for each builder b in builder
		// ...
	}

	public void addBuilder(Builder<T> b) {
		// add and entry ‘‘ b.getTag() −> b’’ to _builders.
		_builders.put(b.getTypeTag(), b);
		// add b.getInfo () to _buildersInfo
		_buildersInfo.add(b.getInfo());
	}

	@Override
	public T createInstance(JSONObject info) {
		
		T instance;
		
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		// Search for a builder with a tag equals to info . getString ("type"), call its
		for(Builder<T> b : _builders.values()) {
			
			//SI EL TAG DEL CUERPO ES EK MISMO QUE EL TYPE DE INDO
			if(b.getTypeTag().equals(info.getString("type"))) {
				
				instance = b.createInstance(info.getJSONObject("data"));
				
				if(instance == null) {
					
				}
				else {
					return instance;	
				}
						
		}
		
		return null;	
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		//
		// info . has("data") ? info . getJSONObject("data") : new getJSONObject()
		// If no builder is found or thr result is null ...

		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
		
		//NS PK DA ERROR, ASI SE CREA UNA FACTORIA DE CUERPOS:
		ArrayList<Builder<Body>> bodyBuilders = new ArrayList<>();
		
		bodyBuilders.add(new MovingBodyBuilder(null, null));
		
		bodyBuilders.add(new StationaryBodyBuilder(null, null));
		
		Factory<Body> bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);
		}
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
}