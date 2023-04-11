package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;

	InfoTable(String title, TableModel tableModel) {
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}

	private void initGUI() {
		// CAMBIAR EL LAYOUT DEL PANEL A BORDERLAYOUT 
		this.setLayout(new BorderLayout());
		
		//AÑADIR UN BORDE CON TITULO AL PANEL CON EL TEXTO _TITLE
		//(LE PASAs AL createTitleBorder(ColoryGrosor, TITULO, IZQ, ARRIBA)
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), _title,
				TitledBorder.LEFT, TitledBorder.TOP));
		
		// AÑADIR UN JTABLE (CON BARRA DE DESPLAZAMIENTO VERTICAL) QUE USE _tableModel
		JTable table = new JTable(_tableModel);
		//USAS EL SCROLLPANE PARA EL DESPLAZAMIENTO, METIENDOLE EL JTABLE CREADO
		this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
}
