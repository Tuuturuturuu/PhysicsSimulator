package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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

	private int _status;// HAY QUE AÑADIR?? TODO
	private int _selectedLawsIndex;

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

		mainPanel.add(createFirstComponent());
		mainPanel.add(createSecondComponent());
		mainPanel.add(createThirdComponent());
		mainPanel.add(createFourthComponent());

		pack();
		setPreferredSize(new Dimension(700, 400));
		setResizable(false);
		setVisible(false);
	}

	public JTextField createFirstComponent() {

		JTextField jtfText = new JTextField(
				"Select a force law and provide values for the parameters in the Value column (default values used for parameters with no value).");
		jtfText.setMaximumSize(new Dimension(900, 25));
		jtfText.setEditable(false);
		return jtfText;
	}

	public JPanel createSecondComponent() {
		// _forceLawsInfo SE USARA PARA ESTABLECER LA INFO. EN LA TABLA
		_forceLawsInfo = _ctrl.getForceLawsInfo();

		// CREAR UN JTABLE QUE USE _dataTableModel, Y AÑADIRLA AL PANEL

		_dataTableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				// HACER EDITABLE SOLO LA COLUMNA 1
				return column == 1;
			}
		};

		_dataTableModel.setColumnIdentifiers(_headers);
		// _dataTableModel = new DefaultTableModel(_headers,3);

		for (int i = 0; i < _forceLawsInfo.size(); i++) {
			JSONObject lawsData = _forceLawsInfo.get(i).getJSONObject("data");
			Set<String> keys = lawsData.keySet();

			for (String key : keys) {
//				System.out.println(key);
//				System.out.println(lawsData.getString(key));
				String[] data = { key, "", lawsData.getString(key) };
				_dataTableModel.addRow(data);

			}

		}

		JTable table = new JTable(_dataTableModel);
		JPanel secondComponent = new JPanel();
		secondComponent.setLayout(new BorderLayout());
		secondComponent.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		return secondComponent;
	}

	public JPanel createThirdComponent() {
		JPanel thirdComponent = new JPanel();
		thirdComponent.setLayout(new BoxLayout(thirdComponent, BoxLayout.X_AXIS));

		JTextField laws = new JTextField("Force Law: ");
		laws.setMaximumSize(new Dimension(75, 25));
		laws.setEditable(false);

		JTextField groups = new JTextField("Group: ");
		groups.setMaximumSize(new Dimension(55, 25));
		groups.setEditable(false);

		_lawsModel = new DefaultComboBoxModel<>();

		for (JSONObject jo : _forceLawsInfo)
			_lawsModel.addElement(jo.getString("desc"));

		// CREAR UN COMBOBOX QUE USE _lawsModel Y AÑADIRLO AL PANEL
		JComboBox<String> cbLaws = new JComboBox<String>(_lawsModel);
		cbLaws.setMaximumSize(new Dimension(250, 25));

		cbLaws.addActionListener((e) -> {
			updateTableModel(cbLaws.getSelectedIndex());
		});

		_groupsModel = new DefaultComboBoxModel<>();

		// CREAR UN COMBOX Q USE _groupsModel y AÑADIRLO AL PANEL
		JComboBox<String> cbGroups = new JComboBox<String>(_groupsModel);
		cbGroups.setMaximumSize(new Dimension(60, 25));

		cbGroups.addActionListener((e) -> {
			updateTableModel(cbGroups.getSelectedIndex());
		});
		
		thirdComponent.add(laws);
		thirdComponent.add(cbLaws);
		thirdComponent.add(groups);
		thirdComponent.add(cbGroups);
	
		thirdComponent.setMinimumSize(new Dimension(30, 100));
		return thirdComponent;
	}

	public JPanel createFourthComponent() {

		JPanel fourthComponent = new JPanel();
		fourthComponent.setLayout(new BoxLayout(fourthComponent, BoxLayout.X_AXIS));

		// OK BUTTON 
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// convierte la información en la tabla en un JSONObject que incluye la
				// clave y el valor para cada fila en la tabla
				JSONObject jo1 = new JSONObject();
				for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
					jo1.put(_dataTableModel.getValueAt(i, 0).toString(), _dataTableModel.getValueAt(i, 1));
				}
				// crea otro JSONObject que tiene una clave llamada
				// “data” cuyo valor es el JSONObject del punto anterior y una clave “type” que
				// es igual a la clave
				// type de la ley seleccionada, es decir,
				JSONObject jo2 = new JSONObject();
				jo2.put("data", jo1);
				jo2.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
				// llama al controlador para fijar esta ley en el grupo seleccionado en el
				// combobox correspondiente
				
//				System.out.println(jo2);
				
				try {
					_ctrl.setForcesLaws(_groupsModel.getSelectedItem().toString(), jo2);
				}
				catch (Exception e1){
					Utils.showErrorMsg("An error has occured");
				}

				_status = 1;
				ForceLawsDialog.this.setVisible(false);

			}
		});

		// CANCEL BUTTON
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				_status = 0;
				ForceLawsDialog.this.setVisible(false);
			}
		});

		fourthComponent.add(okButton);
		fourthComponent.add(cancelButton);
		
		fourthComponent.setMinimumSize(new Dimension(30, 100));
		return fourthComponent;
	}

	public int open() {

		if (_groupsModel.getSize() == 0) {
			return _status;
		}

		if (getParent() != null)
			setLocation(getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2,
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	private void updateTableModel(int _dataIdx) {
		_selectedLawsIndex = _dataIdx;// GUARDA EL INDICE PARA EL OK BUTTON

		JSONObject info = _forceLawsInfo.get(_dataIdx);
		JSONObject data = info.getJSONObject("data");

		_dataTableModel.setNumRows(data.length());

		int i = 0;
		for (String key : data.keySet()) {
			_dataTableModel.setValueAt(key, i, 0);
			_dataTableModel.setValueAt(data.getString(key), i, 2);
			i++;
		}
	}

	// CUALES TODO PREGUNTARSELO A GORDILLO
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
		_groupsModel.addAll(groups.keySet());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.removeAllElements();
		_groupsModel.addAll(groups.keySet());
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
