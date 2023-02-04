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
	private Map<String,BodiesGroup> map;

	PhysicsSimulator(Double t, ForceLaws fl) {
		if (t == null || fl == null)
			throw new IllegalArgumentException("");

		this.fl = fl;
		this.t = t;
		this.bodyList = new ArrayList<Body>();
		this.tiempoActual = 0.0d;
		
		//PREGUNTAR SI AQUI ES UN MAP A SECAS (DA ERROR) O HASHMAP
		this.map = new HashMap<String,BodiesGroup>();
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
		//NO SE SI SE HACE ASI
		BodiesGroup bodiesGroup = new BodiesGroup(id, this.fl);
		
		//NO SE COMO COMPROBAR SI HAY MAS GRUPOS EN EL MAPA CON EL MISMO ID
		//Y SI LOS HAY LANZAR LA EXCEPCION
		
		//throw new IllegalArgumentException();
		

		map.put(id, bodiesGroup);
	}

	public void addBody(Body b) {// ES IGUAL QUE EL ADDBODY DE BODYGROUP
		if (b == null)
			throw new IllegalArgumentException("Illegal parameter: Body received is null");

		for (Body bi : bodyList) {

			if (b.getgId() == bi.getId()) {
				throw new IllegalArgumentException("Illegal parameter: Body is already in Bodies Group");
			} else
				// AÑADIR B AL BODIES GROUP, NO SE SI ESTA BIEN ASI
				bodyList.add(b);
		}
	}

	public void setForceLaws(String id, ForceLaws f) {

		// TENGO QUE HACERLO CON EL MAPA? COMO COMPARO LOS ID?
		//TENGO Q PONER UN ATRIBUTO TIPO BODIESGROUPS? O OL CREO AQUI Y SETTEO SU FL Y SU ID?
	
		BodiesGroup bodiesGroup = new BodiesGroup(id, f);
		//NO SE SI ESTÁ BIEN
		
		//EL MISMO PROBELMA Q EN ADGROUP, NO SE CMO LANZAR LA EXCEPCION

	}

	public JSONObject getState() {

		JSONObject jo = new JSONObject();
		JSONObject jo2 = new JSONObject();

		jo.put("time", tiempoActual);

		for (Body b : bodyList) {
			//jo2.put(b.toString());
		}

		jo.put("groups", jo2);

		return jo;
	}

	public String toString() {
		return getState().toString();
	}
}
