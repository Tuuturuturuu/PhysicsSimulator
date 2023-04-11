package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ViewerWindow extends JFrame implements SimulatorObserver {
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private Frame _parent;

	ViewerWindow(Frame parent, Controller ctrl) { 
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		intiGUI();
		
		_ctrl.addObserver(this);
	}

private void intiGUI() {
	JPanel mainPanel = new JPanel();
	mainPanel.setMinimumSize(new Dimension(200,200));
	
	_viewer = new Viewer();

	mainPanel.setLayout(new BorderLayout());
	mainPanel.add(new JScrollPane(_viewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);	
	
	setContentPane(mainPanel); 
	addWindowListener(new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent e) { //-> VER SI ESTO ESTA BIEN;
			Window window = e.getWindow();
	        window.removeWindowListener(this);
	        //_ctrl.removeObserver(this);
		}

		@Override
		public void windowClosed(WindowEvent e) {}

		@Override
		public void windowIconified(WindowEvent e) {}

		@Override
		public void windowDeiconified(WindowEvent e) {}

		@Override
		public void windowActivated(WindowEvent e) {}

		@Override
		public void windowDeactivated(WindowEvent e) {}
		
	});
	
	pack();
	if (_parent != null) 
		setLocation(_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2, 
				_parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
	setVisible(true);
	System.out.println("llegamos aqui");
}

@Override
public void onAdvance(Map<String, BodiesGroup> groups, double time) { 
	_viewer.update();	
	//_viewer.repaint();
}

@Override
public void onReset(Map<String, BodiesGroup> groups, double time, double dt) { //TODO
	_viewer.reset();
}

@Override
public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) { //TODO
	for(BodiesGroup g: groups.values()) {
		_viewer.addGroup(g);
	}
}

@Override
public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	_viewer.addGroup(g);
}

@Override
public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
	_viewer.addBody(b);
}

@Override
public void onDeltaTimeChanged(double dt) {}

@Override
public void onForceLawsChanged(BodiesGroup g) {}


}
