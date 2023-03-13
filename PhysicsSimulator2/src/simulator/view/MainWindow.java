package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		ControlPanel controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		
		StatusBar statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		//DEFINICION DEL PANEL DE TABLAS (USANDO UN BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		//CREAR LA TABLA DE GRUPOS Y AÑADIRLA A CONTENT PANEL.
		GroupsTableModel groupsTable = new GroupsTableModel(_ctrl);
		contentPanel.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupsTable);
		
		
		//CREAR LA TABLA DE CUERPOS Y AÑADIRLA AL CONTENT PANEL.
		BodiesTableModel bodiesTable = new BodiesTableModel(_ctrl);
		contentPanel.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
		
		// TODO llama a Utils.quit(MainWindow.this) en el método windowClosing
		Utils.quit(MainWindow.this);
		WindowListener windowListener;
		windowListener.windowClosing();
		
		addWindowListener(windowListener);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
