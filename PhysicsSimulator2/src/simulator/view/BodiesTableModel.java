package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public String getColumnName(int col) {
		return _header[col];
	}

	@Override
	public int getColumnCount() {
		return this._header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Body b = _bodies.get(rowIndex);

		Object ret = null;

		switch (columnIndex) {
		case 0:
			ret = b.getId();
			break;
		case 1:
			ret = b.getgId();
			break;
		case 2:
			ret = b.getMass();
			break;
		case 3:
			ret = b.getVelocity();
			break;
		case 4:
			ret = b.getPosition();
			break;
		case 5:
			ret = b.getForce();
			break;
		}
		return ret;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireTableDataChanged();
			}

		});
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				_bodies.clear();
				fireTableStructureChanged();
			}

		});
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (BodiesGroup b : groups.values()) {
					for (Body bd : b) {
						_bodies.add(bd);
					}
				}
				fireTableStructureChanged();
			}

		});
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (Body bd : g) {
					_bodies.add(bd);
				}
				fireTableStructureChanged();
			}

		});

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				fireTableDataChanged();
			}

		});
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}
}
