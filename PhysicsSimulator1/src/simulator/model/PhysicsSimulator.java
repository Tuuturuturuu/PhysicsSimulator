package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
		this.map = new HashMap<String, BodiesGroup>();
		this.listOrderedGroupIds = new ArrayList<String>();
	}

	public void advance() {

		this.fl.apply(bodyList);
		
		for (Body bi : bodyList) {// CAMBIAR ESTO NO RECORRE LA BODYLIST SI NO LOS BODY GROUPS DEL MAPA??
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
			// SE AÑADEN A LA LISTA LOS ID DE LOS GRUOPOS EN ORDEN SEGUN SE CREAN PARA Q
			// ESTEN ORDENADOS
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
	
	public void addBodyToGroup (Body b, String gid) {
		if (!map.containsKey(gid))
			throw new IllegalArgumentException("Bodies Group" + gid + " doesnt exist in the map");
		else{
			map.get(gid).addBody(b);
		}
	}

	public void setForceLaws(String id, ForceLaws f) {

		if (!map.containsKey(id))
			throw new IllegalArgumentException("That Bodies Group id doesnt exist in the map");

		else
			map.get(id).setForceLaws(f);

	}

	public JSONObject getState() {

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		jo.put("time", tiempoActual);

		for (String id : listOrderedGroupIds) {

			ja.put(map.get(id).getState());// TO STRING O GETSATE?
		}

		jo.put("groups", ja);

		return jo;
	}

	public String toString() {
		return getState().toString();
	}
}
