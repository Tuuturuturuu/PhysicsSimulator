package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{

	private Double t;
	private ForceLaws fl;

	private Double tiempoActual;
	private Map<String, BodiesGroup> map;
	private List<String> listOrderedGroupIds;
	private List<SimulatorObserver> observerList;
	
	private Map<String, BodiesGroup> _groupsRO;//ESTO ES PUBLIC?
	
	public PhysicsSimulator(ForceLaws fl, Double t) {
		if (t == null || fl == null)
			throw new IllegalArgumentException("");

		this.fl = fl;
		this.t = t;
		this.tiempoActual = 0.0d;
		this.map = new HashMap<String, BodiesGroup>();
		this.listOrderedGroupIds = new ArrayList<String>();
		this.observerList = new ArrayList<SimulatorObserver>();
		
		//ESTO SE INICIALIZA ASI? _groupsRO TIENE QUE SER PUBLIC? SI NO DA ERROR
		this._groupsRO = Collections.unmodifiableMap(map);
	}

	public void advance() {

		for (BodiesGroup bg : map.values()) {
			bg.advance(t); // TIEMPO REAL POR PASO
		}
		// INCREMENTA ELTIEMPO ACTUAL EN T SEGUNDOS
		this.tiempoActual += t;
		
		for (SimulatorObserver o : observerList)
			o.onAdvance(_groupsRO, tiempoActual);
	}

	public void addGroup(String id) {

		BodiesGroup bodiesGroup = new BodiesGroup(id, this.fl);

		if (map.containsKey(id))
			throw new IllegalArgumentException("Bodies Group already has that id");
		else {
			map.put(id, bodiesGroup);
			// SE AÑADEN A LA LISTA LOS ID DE LOS GRUOPOS EN ORDEN SEGUN SE CREAN PARA Q
			// ESTEN ORDENADOS POR ORDEN DE INSERCION
			listOrderedGroupIds.add(id);
			
			for (SimulatorObserver o : observerList)
				o.onGroupAdded(_groupsRO, bodiesGroup);
		}
	}

	public void addBody(Body b) {
		if (b == null)
			throw new IllegalArgumentException("Illegal parameter: Body received is null");

		if (!map.containsKey(b.getgId()))
			throw new IllegalArgumentException("Illegal parameter: yataBodies Group doesnt exist in map");
		else {
			map.get(b.getgId()).addBody(b);
			
			for (SimulatorObserver o : observerList)
				o.onBodyAdded(_groupsRO, b);
		}

	}

	public void setForceLaws(String id, ForceLaws f) {

		if (!map.containsKey(id))
			throw new IllegalArgumentException("That Bodies Group id doesnt exist in the map");

		else {
			map.get(id).setForceLaws(f);
			
			//ESTO ES ASI??? IM CONFUSED
			for (SimulatorObserver o : observerList)
				o.onForceLawsChanged(map.get(id)); //PREGUNTAR A GORDI
		}

	}
	
	public void setDeltaTime(double dt) {
		
		if(dt < 0)
			throw new IllegalArgumentException("Delta time introduced is negative");
		
		this.t = dt; //CREO QUE ES ASI PERO NO ESTOY SEGURA
		
		for (SimulatorObserver o : observerList)
			o.onDeltaTimeChanged(t);
	}
	
	public void reset() {
		map.clear();
		
		this.tiempoActual = 0.0d;
		
		for (SimulatorObserver o : observerList)
			o.onReset(_groupsRO, tiempoActual, t);
		
	}

	public JSONObject getState() {

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

		jo.put("time", tiempoActual);

		for (String id : listOrderedGroupIds) {

			ja.put(map.get(id).getState());
		}

		jo.put("groups", ja);

		return jo;
	}

	public String toString() {
		return getState().toString();
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		
		if(observerList.contains(o)) //NO SE SI ES ASI, CON EL CONTAINS
			throw new IllegalArgumentException("Observer already added to the list");
		
		observerList.add(o);
		o.onRegister(_groupsRO, tiempoActual, t);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		observerList.remove(o);
		
	}
}