package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class PhysicsSimulator {

	private Double t;
	private ForceLaws fl;
	private List<Body> bodyList;
	private Double tiempoActual;
	private Map<String, BodiesGroup> map;
	private List<String> listOrderedGroupIds;

	public PhysicsSimulator(Double t, ForceLaws fl) {
		if (t == null || fl == null)
			throw new IllegalArgumentException("");

		this.fl = fl;
		this.t = t;
		this.bodyList = new ArrayList<Body>();
		this.tiempoActual = 0.0d;

		// PREGUNTAR SI AQUI ES UN MAP A SECAS (DA ERROR) O HASHMAP
		this.map = new HashMap<String, BodiesGroup>();
	}

	public void advance() {

		// HAY QUE PRIMERO APLICARLE UNA LEY?
		for (Body bi : bodyList) {
			bi.advance(t); // TIEMPO REAL POR PASO
		}
		// INCREMENTA ELTIEMPO ACTUAL EN T SEGUNDOS
		this.tiempoActual += t;

	}

	public void addGroup(String id) {

		BodiesGroup bodiesGroup = new BodiesGroup(id, this.fl);

		if (map.containsKey(id))
			throw new IllegalArgumentException("Bodies Group already has that id");
		else {
			map.put(id, bodiesGroup);
			// ASI PONGO LOS ID DE LOS GRUOPOS EN ORDEN SEGUN SE CREAN?
			listOrderedGroupIds.add(id);
		}
	}

	public void addBody(Body b) {
		if (b == null)
			throw new IllegalArgumentException("Illegal parameter: Body received is null");

		if (bodyList.contains(b))
			throw new IllegalArgumentException("Illegal parameter: Body is already in Bodies Group");
		else
			bodyList.add(b);
	}

	public void setForceLaws(String id, ForceLaws f) {

		if (!map.containsKey(id))
			throw new IllegalArgumentException("That Bodies Group id doesnt exist in the map");

		else
			map.get(id).setForceLaws(f);

	}

	public JSONObject getState() {// PREGUNTAR GORDILLO SI ITERAS SOBRE BODYLIST O LOS BODYGROUPS DEL MAPA

		JSONObject jo = new JSONObject();

		jo.put("time", tiempoActual);

		for (String id : listOrderedGroupIds) {//ASI ITERO SOBRE LOS IDS DE LOS GRUPOS ORDENADOS?

			jo.put("groups", map.get(id));
		}
		return jo;
	}

	public String toString() {
		return getState().toString();
	}
}
