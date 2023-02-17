package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {

	protected String id;
	protected String gid;
	protected Vector2D vel;
	protected Vector2D pos;
	protected Vector2D force;
	protected Double mass;

	public Body(String id, String gid, Vector2D p, Vector2D v, Double m) {

		if (id == null || gid == null || v == null || p == null || m == null || m <= 0)
			throw new IllegalArgumentException("Body received null or negative parameter");

		else if (!(id.trim().length() > 0) || !(gid.trim().length() > 0))
			throw new IllegalArgumentException("Body received invalid parameter");

		this.id = id;
		this.gid = gid;
		this.vel = v;
		this.pos = p;
		this.mass = m;
		this.force = new Vector2D();
	}

	public String getId() {
		return id;
	}

	public String getgId() {
		return gid;
	}

	public Vector2D getVelocity() {
		return vel;
	}

	public Vector2D getForce() {
		return force;
	}

	public Vector2D getPosition() {
		return pos;
	}

	public double getMass() {
		return mass;
	}

	void addForce(Vector2D f) {
		this.force = this.force.plus(f);
	}

	void resetForce() {
		this.force = new Vector2D();

	}

	void resetVelocity() {
		this.vel = new Vector2D();
	}

	abstract void advance(double dt);

	public JSONObject getState() {

		JSONObject jo = new JSONObject();
		

		jo.put("id", this.id);
		jo.put("p", this.pos.asJSONArray());
		jo.put("v", this.vel.asJSONArray());
		jo.put("f", this.force.asJSONArray());
		jo.put("m", this.mass);

		return jo;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.id.compareTo(((Body) o).getId()) == 0 ? true : false; 
	}

	public String toString() {
		return getState().toString();

	}

}
