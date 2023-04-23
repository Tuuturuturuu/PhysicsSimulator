package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
class StatusBar extends JPanel implements SimulatorObserver {

	private JLabel time;
	private JLabel groups;
	private JLabel _time;
	private JLabel _nGroups;

	StatusBar(Controller ctrl) {

		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));

		this.time = new JLabel("Time: ");
		this._time = new JLabel();
		this.add(time);
		this.add(_time);

		this.groups = new JLabel("Groups: ");
		this._nGroups = new JLabel();
		this.add(groups);
		this.add(_nGroups);

		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		this._time.setText(String.valueOf(time));
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._time.setText(String.valueOf(0.0));
		this._nGroups.setText(String.valueOf(groups.size()));
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		this._time.setText(String.valueOf(time));
		this._nGroups.setText(String.valueOf(groups.size()));
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this._nGroups.setText(String.valueOf(groups.size()));

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {

	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {

	}
}
