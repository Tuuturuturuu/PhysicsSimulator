package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {
	private String id;
	private ForceLaws laws;
	private List<Body> bodyList;

	public BodiesGroup(String id, ForceLaws laws) {

		if (id == null || laws == null)
			throw new IllegalArgumentException("Bodies Group received null parameter");

		else if (!(id.trim().length() > 0))
			throw new IllegalArgumentException("Bodies Gruop received invalid parameter");

		this.id = id;
		this.laws = laws;
		this.bodyList = new ArrayList<Body>();
	}

	public String getId() {
		return this.id;
	}

	void setForceLaws(ForceLaws fl) {
		if (fl == null)
			throw new IllegalArgumentException("Bodies Group received null Force Law");

		this.laws = fl;
	}

	void addBody(Body b) {

		if (b == null)
			throw new IllegalArgumentException("Illegal parameter: Body received is null");

		if (bodyList.contains(b)) {
			throw new IllegalArgumentException("Illegal parameter: Body is already in Bodies Group");
		} else
			bodyList.add(b);
	}

	void advance(double dt) { // DT = TIEMPO REAL POR PASO
		if (dt < 0)
			throw new IllegalArgumentException("Illegal parameter: Time received is negative");

		for (Body bi : bodyList) {
			bi.resetForce();
			laws.apply(bodyList);
			bi.advance(dt);
		}
	}

	public JSONObject getState() {
		JSONObject jo1 = new JSONObject();
		JSONArray ja = new JSONArray();

		jo1.put("id", this.id);

		for (Body bbi : bodyList) {
			ja.put(bbi.getState());// TO STRING?
		}

		jo1.put("bodies", ja);
		return jo1;
	}

	public String toString() {
		return getState().toString();
	}
}
