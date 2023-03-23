package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; //USADO EN RUN/STOP BUTTON
	private JButton _quitButton;

	// ATRIBUTOS AÑADIDOS
	private int _dt = 25000;
	private int _steps = 10000;
	JSpinner stepsSpinner;
	JTextField dtText;

	// BOTONES
	private JButton loadButton; // ICONO DE CARPETA
	private JButton fldButton; // ICONO REDONDO AZUL
	private JButton stopButton; // ICONO ROJO CON CUADRADO EN EL MEDIO
	private JButton runButton; // ICONO ROJO CON TRIANGULO EN EL MEDIO
	private JButton viewerWindowButton; // ICONO CON UNA MANO

	private JLabel dtLabel; // LABEL QUE MESTRA EL DELTA TIME
	private JLabel stepsLabel; // LABEL QUE MUESTRA LOS STEPS DE LA SIMULACION

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		// REGISTRAR THIS COMO OBSERVADOR
		this._ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);

		// CREAR LOS BOTONES/ATRIBUTOS QUE TENDRA EN CONTROL PANEL Y AÑADIRLOS A TOOLBAR
		createLoadButton();
		this._toolaBar.addSeparator();
		createFldButton();
		createViewerWindowButton();
		this._toolaBar.addSeparator();
		createRunButton();
		createStopButton();

		// STEPS
		this.stepsLabel = new JLabel();
		this.stepsLabel.setText(" Steps: ");
		this._toolaBar.add(stepsLabel);
		createStepsSpinner();

		// DELTA TIME
		this.dtLabel = new JLabel();
		this.dtLabel.setText(" Delta time: ");
		this._toolaBar.add(dtLabel);
		createDeltaTimeText();

		// QUIT BUTTON
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);	
	}

	private void createDeltaTimeText() {
		dtText = new JTextField();
		dtText.setText(String.valueOf(_dt));
		dtText.setMinimumSize(new Dimension(80, 30));
		dtText.setMaximumSize(new Dimension(200, 30));
		dtText.setPreferredSize(new Dimension(80, 30));	
		dtText.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	
				_dt = Integer.parseInt(dtText.getText());
				
			}
			
		});		
		
		this._toolaBar.add(dtText);
		
	}

	private void createStepsSpinner() {
		stepsSpinner = new JSpinner();
		stepsSpinner.setValue(_steps);
		stepsSpinner.setMinimumSize(new Dimension(80, 30));
		stepsSpinner.setMaximumSize(new Dimension(200, 30));
		stepsSpinner.setPreferredSize(new Dimension(80, 30));	
		stepsSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				_dt = Integer.parseInt(dtText.getText());
			}
			
		});		
		
		this._toolaBar.add(stepsSpinner);
		
	}

	private void createLoadButton() {
		this._fc = new JFileChooser();
		this.stopButton.setToolTipText("Load File");
		this.loadButton = new JButton();

		// LE ASIGNAMOS EL ICONO (IMAGEN) AL LOAD BUTTON
		this.loadButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));

		// LE ASIGNAMOS AL BOTON UNA ACCION AL PULSARLO: (CON UN ACTION LISTENER)
		this.loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// SE ABRE EL SELECTOR DE ARCHIVOS PARA ELEGIR EL FICHERO DE ENTRADA
				if (_fc.showOpenDialog(Utils.getWindow(_fc)) == JFileChooser.APPROVE_OPTION) 
					// SI SE HA PODIDO SELECCIONAR UN ARCHIVO, SE MUESTRA
					// EL ARCHIVO SELECCIONADO
					JOptionPane.showMessageDialog(_fc, "The file you have selected is: " + _fc.getSelectedFile());
				else
					JOptionPane.showMessageDialog(_fc, "An error has ocurred.");

				// SE SELECCIONA EL ARCHIVO
				File file = _fc.getSelectedFile();
				InputStream is;

				try {// SI SE HA SELECCIONADO UN ARCHIVO SE RESETEA Y CARGAN LOS DATOS DEL ARCHIVO
					is = new FileInputStream(file);
					_ctrl.reset();
					_ctrl.loadData(is);
				} catch (FileNotFoundException e1) {// SI NO SE SELECCIONA UN ARCHIVO SE LANZA UN ERROR
					JOptionPane.showMessageDialog(null, "An error has ocurred.");
				}
			}

		});

		// POR ULTIMO AÑADIMOS EL LOAD BUTTON QUE HEMOS CREADO A LA TOOLBAR
		this._toolaBar.add(loadButton);
	}

	private void createFldButton() {//TODO

		this.fldButton = new JButton();
		this.stopButton.setToolTipText("Add Force Law");
		this.fldButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/physics.png")));

		ForceLawsDialog fld = new ForceLawsDialog(null, _ctrl);

		fldButton.add(fld);
		fld.open();

		// POR ULTIMO AÑADIMOS EL FLD BUTTON QUE HEMOS CREADO A LA TOOLBAR
		this._toolaBar.add(fldButton);

	}

	private void createRunButton() {

		this.runButton = new JButton();
		this.stopButton.setToolTipText("Run Simulation");
		this.runButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/run")));

		this.runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = false;

				loadButton.setEnabled(false);
				//runButton.setEnabled(false);
				fldButton.setEnabled(false);
				_quitButton.setEnabled(false);
				viewerWindowButton.setEnabled(false);

				stepsSpinner.setEnabled(false);
				dtText.setEnabled(false);

				_ctrl.setDeltaTime(_dt);
				run_sim(_steps);
			}
		});
		this._toolaBar.add(runButton);
	}
	
	private void createViewerWindowButton() {//TODO
		
		this.viewerWindowButton = new JButton();
		this.viewerWindowButton.setToolTipText(" Window Viewer ");
		this.stopButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/viewer.png")));
		
		this.viewerWindowButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		this._toolaBar.add(viewerWindowButton);
		
	}

	private void createStopButton() {

		this.stopButton = new JButton();
		this.stopButton.setToolTipText("Stop");
		this.stopButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/stop.png")));

		this.stopButton.addActionListener((e) -> _stopped = true);

		// POR ULTIMO AÑADIMOS EL STOP BUTTON QUE HEMOS CREADO A LA TOOLBAR
		this._toolaBar.add(stopButton);
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			
			try {
				_ctrl.run(1);
				
			} catch (Exception e) {
				Utils.showErrorMsg("Couldnt run the simulation");
				
				//ACTIVAR TODOS LOS BOTONES
				stopButton.setEnabled(true);
				_quitButton.setEnabled(true);
				runButton.setEnabled(true);
				fldButton.setEnabled(true);
				loadButton.setEnabled(true);
				viewerWindowButton.setEnabled(true);
				
				stepsSpinner.setEnabled(true);
				dtText.setEnabled(true);
				
				_stopped = true;
				return;
			}
			
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
			
		} else {
			Utils.showErrorMsg("Simulation stopped");
			
			//ACTIVAR TODOS LOS BOTONES
			stopButton.setEnabled(true);
			_quitButton.setEnabled(true);
			runButton.setEnabled(true);
			fldButton.setEnabled(true);
			loadButton.setEnabled(true);
			viewerWindowButton.setEnabled(true);
			
			stepsSpinner.setEnabled(true);
			dtText.setEnabled(true);
			
			_stopped = true;
		}
	}
	
	//METODOS QUE IMPLEMENTAN SIMULATOR OBSERVER
	//TODO GORDILLO AIUDA

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
//		SwingUtilities.invokeLater( new Runnable() { 
//			@Override 
//			public void run() { _ctrl.setDeltaTime(_dt); }
//			
//		});

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		//TODO

	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {
		
		//TODO
	}
	
	//METODOS VACIOS

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}
}
