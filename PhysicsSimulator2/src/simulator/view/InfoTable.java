package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;

	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());

		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), _title,
				TitledBorder.LEFT, TitledBorder.TOP));

		JTable table = new JTable(_tableModel);

		this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
}
