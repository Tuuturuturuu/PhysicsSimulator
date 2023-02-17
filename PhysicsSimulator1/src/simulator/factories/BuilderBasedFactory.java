
package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	public BuilderBasedFactory() {
		this._builders = new HashMap<String, Builder<T>>();
		this._buildersInfo = new LinkedList<JSONObject>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();

		for (Builder<T> b : builders) {
			addBuilder(b);
		}
	}

	public void addBuilder(Builder<T> b) {
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo());
	}

	@Override
	public T createInstance(JSONObject info) {

		T instance;

		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		if(!info.has("type"))
				throw new IllegalArgumentException("Missing object type");
		else {
				
			if (_builders.containsKey(info.getString("type"))) {
				
				if(!info.has("data")) 
					instance = _builders.get(info.getString("type")).createInstance(null);
	
				instance = _builders.get(info.getString("type")).createInstance(info.getJSONObject("data"));
	
				if (instance == null) 
					throw new IllegalArgumentException("Invalid value for instance: null");
				else 
					return instance;
			}
				
				
			else
				throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
		}
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
}