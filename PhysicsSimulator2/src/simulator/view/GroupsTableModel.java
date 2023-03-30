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

class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;

	GroupsTableModel(Controller ctrl) {
		_groups = new ArrayList<>();

		// REGISTRAR THIS COMO OBSERVADOR
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return this._groups.size();
	}

	@Override
	public int getColumnCount() {
		return this._header.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return _header[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {// TIENE QUE DEVOLVER UN STRING???
		BodiesGroup bg = _groups.get(rowIndex);
		Object ret = null;

		switch (columnIndex) {
		case 0:
			ret = bg.getId();
			break;
		case 1:
			ret = bg.getForceLawsInfo();
			break;
		case 2:
			for (Body b : bg._bodiesRO)
				ret = b.getId();// HAY QUE CONCATENAR LOS IDS DE TODOS LOS CUERPOS DEL GROUP, PK AHORA SOLO ESTA MOSTRANDO EL ULTIMO TODO
			break;
		}
		return ret;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		//SE QUEDA VACIO
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {}

		});
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				_groups.clear();
				
				fireTableStructureChanged();
			}

		});
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				_groups.addAll(groups.values());

				fireTableStructureChanged();
			}

		});

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				_groups.add(g);

				fireTableStructureChanged();
			}

		});

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				_groups.clear();
				_groups.addAll(groups.values());

				fireTableStructureChanged();
			}

		});

	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				fireTableDataChanged();
			}
		});

	}

	@Override
	public void onDeltaTimeChanged(double dt) {}
}
