package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

	// ATRIBUTOS AÑADIDOS
	private String[][] _jsonSrc;
	private int _status;

	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// _forceLawsInfo SE USARA PARA ESTABLECER LA INFO. EN LA TABLA
		_forceLawsInfo = _ctrl.getForceLawsInfo();

		// CREAR UN JTABLE QUE USE _dataTableModel, Y AÑADIRLA AL PANEL
		JTable table = new JTable(_dataTableModel);
		mainPanel.add(table);

		_dataTableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				// HACER EDITABLE SOLO LA COLUMNA 1
				return column != 0;
			}
		};

		_dataTableModel.setColumnIdentifiers(_headers);
		_lawsModel = new DefaultComboBoxModel<>();

		// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel

		// TODO crear un combobox que use _lawsModel y añadirlo al panel

		// PARA UAR LOS COMBOBOX -> GETSELECTEDINDEX
		JComboBox combobox = new JComboBox(_lawsModel);
		combobox.getSelectedIndex();
		

		_groupsModel = new DefaultComboBoxModel<>();// ES UN ARRAY AL Q LE P¡METO INFO, SE LE METE EL MODELO EN EL
													// CONTRUCTOR?

		_groupsModel.getSelectedItem();
		// CREAR UN COMBOX Q USE _groupsModel y AÑADIRLO AL PANEL
		JComboBox<String> dataSelector = new JComboBox<String>();

		for (int i = 0; i < _jsonSrc.length; i++)
			dataSelector.addItem("DATA-" + i);

		mainPanel.add(dataSelector);
		dataSelector.addActionListener((e) -> {
			updateTableModel(dataSelector.getSelectedIndex());
		});

		// OK BUTTON
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
				ForceLawsDialog.this.setVisible(false);
			}
		});
		mainPanel.add(okButton);

		// CANCEL
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				_status = 0; TODO
				ForceLawsDialog.this.setVisible(false);
			}
		});

		mainPanel.add(cancelButton);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open() {
		if (_groupsModel.getSize() == 0)

//	 TODO Establecer la posición de la ventana de diálogo de tal manera que se
//	 abra en el centro de la ventana principal

			if (getParent() != null)
				setLocation(getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2,
						getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	private void updateTableModel(int _dataIdx) {
		String[] keys = _jsonSrc[_dataIdx];
		_dataTableModel.setNumRows(keys.length);
		for (int i = 0; i < keys.length; i++) {
			_dataTableModel.setValueAt(keys[i], i, 0);
			_dataTableModel.setValueAt("", i, 1);
		}
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {

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
}
