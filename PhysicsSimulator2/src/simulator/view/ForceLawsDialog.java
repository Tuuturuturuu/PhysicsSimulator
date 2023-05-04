package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
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

@SuppressWarnings("serial")
class ForceLawsDialog extends JDialog implements SimulatorObserver {
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };

	private int _status;
	private int _selectedLawsIndex = 0;

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

	public JPanel createFirstComponent() {
		
		JPanel firstComponent = new JPanel();
		firstComponent.setLayout(new BoxLayout(firstComponent, BoxLayout.X_AXIS));
		
		  JLabel jtfText = new JLabel();
		  jtfText.setText("<html><p>Select a force law and provide values for the parameters in the Value column (default values used for parameters with no value).</p></html>");
		  firstComponent.add(jtfText);	

		return firstComponent;
	}

	public JPanel createSecondComponent() {

		_forceLawsInfo = _ctrl.getForceLawsInfo();

		_dataTableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {

				return column == 1;
			}
		};

		_dataTableModel.setColumnIdentifiers(_headers);

		JTable table = new JTable(_dataTableModel);
		JPanel secondComponent = new JPanel();
		secondComponent.setLayout(new BorderLayout());
		secondComponent.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		return secondComponent;
	}

	public JPanel createThirdComponent() {
		JPanel thirdComponent = new JPanel();
		thirdComponent.setLayout(new BoxLayout(thirdComponent, BoxLayout.X_AXIS));

		JLabel laws = new JLabel("Force Law: ");
		laws.setMaximumSize(new Dimension(75, 25));

		_lawsModel = new DefaultComboBoxModel<>();

		for (JSONObject jo : _forceLawsInfo)
			_lawsModel.addElement(jo.getString("desc"));

		JComboBox<String> cbLaws = new JComboBox<String>(_lawsModel);
		cbLaws.setMaximumSize(new Dimension(250, 25));

		cbLaws.addActionListener((e) -> {
			updateTableModel(cbLaws.getSelectedIndex());
		});

		JLabel groups = new JLabel("  Group: ");
		groups.setMaximumSize(new Dimension(55, 25));

		_groupsModel = new DefaultComboBoxModel<>();

		JComboBox<String> cbGroups = new JComboBox<String>(_groupsModel);
		cbGroups.setMaximumSize(new Dimension(60, 25));

		updateTableModel(cbLaws.getSelectedIndex());

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

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String s = "{";

				for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
					s += _dataTableModel.getValueAt(i, 0).toString() + ":"
							+ _dataTableModel.getValueAt(i, 1).toString();

					if (i < _dataTableModel.getRowCount() - 1) {
						s += ",";
					}
					/*
					 * if (_dataTableModel.getValueAt(i, 0).toString().equals("c")) {
					 * 
					 * JSONArray jsonArray = new JSONArray(_dataTableModel.getValueAt(i,
					 * 1).toString()); jo1.put("c", jsonArray); } else
					 * jo1.put(_dataTableModel.getValueAt(i, 0).toString(),
					 * _dataTableModel.getValueAt(i, 1).toString());
					 */

				}
				s += "}";

				JSONObject jo2 = new JSONObject();
				JSONObject joData = new JSONObject(s);
				jo2.put("data", joData);
				jo2.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));

				try {
					_ctrl.setForcesLaws(_groupsModel.getSelectedItem().toString(), jo2);
				} catch (Exception e1) {
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
		_selectedLawsIndex = _dataIdx;

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

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_groupsModel.removeAllElements();
			}

		});
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();

		for (BodiesGroup bg : groups.values()) {
			_groupsModel.addElement(bg.getId());
		}

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_groupsModel.removeAllElements();

				for (BodiesGroup bg : groups.values()) {
					_groupsModel.addElement(bg.getId());
				}
			}

		});
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
