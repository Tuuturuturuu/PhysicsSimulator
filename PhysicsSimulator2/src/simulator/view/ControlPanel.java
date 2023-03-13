package simulator.view;

import java.awt.BorderLayout;
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
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;

	// ATRIBUTOS AÑADIDOS
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

		// ALGUNOS DE ELLOS HAN DE TENER SU CORRESPONDIENTE TOOLTIP (ADDSEPARATOR())
		// PARA AÑADIR UNA LINEA DE
		// SEPARACION VERTICAL ENTRE LOS COMPONENTES
		this._toolaBar.addSeparator();

		createFldButton();
		this._toolaBar.add(viewerWindowButton);

		this._toolaBar.addSeparator();
		this._toolaBar.add(runButton);
		this._toolaBar.add(stopButton);

		// STEPS
		this.stepsLabel = new JLabel();
		this.stepsLabel.setText(" Steps: ");
		this._toolaBar.add(stepsLabel);

		// DELTA TIME
		this.dtLabel = new JLabel();
		this.dtLabel.setText(" Delta time: ");
		this._toolaBar.add(dtLabel);

		// QUIT BUTTON
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);

		// CREAR EL SELECTOR DE FICHEROS ?? PARA QUE LO HACEMOS AQUI?? ASI??? SIRVE PARA
		// ALGO??
		_fc = new JFileChooser();
	}

	private void createLoadButton() {
		this._fc = new JFileChooser();
		this.loadButton = new JButton();
		
		//LE ASIGNAMOS EL ICONO (IMAGEN) AL LOAD BUTTON
		this.loadButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/open.png")));
		
		//LE ASIGNAMOS AL BOTON UNA ACCION AL PULSARLO: (CON UN ACTION LISTENER)
		this.loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//SE ABRE EL SELECTOR DE ARCHIVOS PARA ELEGIR EL FICHERO DE ENTRADA
				if (_fc.showOpenDialog(Utils.getWindow(_fc)) == JFileChooser.APPROVE_OPTION) //SI SE HA PODIDO SELECCIONAR UN ARCHIVO, SE MUESTRA EL ARCHIVO SELECCIONADO 
					JOptionPane.showMessageDialog(_fc, "The file you have selected is: " + _fc.getSelectedFile());
				else 
					JOptionPane.showMessageDialog(_fc, "An error has ocurred.");
				
				//SE SELECCIONA EL ARCHIVO
				File file = _fc.getSelectedFile(); 
				InputStream is;
				
				try {//SI SE HA SELECCIONADO UN ARCHIVO SE RESETEA Y CARGAN LOS DATOS DEL ARCHIVO
					is = new FileInputStream(file);
					_ctrl.reset();
					_ctrl.loadData(is);
				} 
				catch (FileNotFoundException e1) {//SI NO SE SELECCIONA UN ARCHIVO SE LANZA UN ERROR
					JOptionPane.showMessageDialog(null, "An error has ocurred.");
				}	
			}
			
		});
		
		//POR ULTIMO AÑADIMOS EL LOAD BUTTON QUE HEMOS CREADO A LA TOOLBAR
		this._toolaBar.add(loadButton);
	}
	
	private void createFldButton() {//WTF? ME HE QUEDADO AQUI
		
		this.fldButton = new JButton();
		this.fldButton.setIcon(new ImageIcon(this.getClass().getResource("/icons/physics.png")));
		
		ForceLawsDialog fld = new ForceLawsDialog(null, _ctrl);
		
		fldButton.add(fld);
		fld.open();
		
		//POR ULTIMO AÑADIMOS EL FLD BUTTON QUE HEMOS CREADO A LA TOOLBAR
		this._toolaBar.add(fldButton);
		
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub

	}
// TODO el resto de métodos van aquí…
}
