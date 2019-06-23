package gui;

import logic.MyTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public abstract class TableForm extends JFrame implements ActionListener, MouseListener {
	protected TableMenuBar menuBar;
	protected JPanel backPanel;
	protected JScrollPane tablePane;
	protected JTable resultTable;
	protected JLabel toolTipLabel;

	public static String[][] lastSelectedRow;

	protected TableForm() {}

	public TableForm(MyTableModel sqlTableData) {
		this.setLayout(new BorderLayout());
		this.setSize(450, 400);
		this.setLocation(300, 300);
		this.setTitle("Default Title");

		backPanel = new JPanel(new BorderLayout());

		menuBar = new TableMenuBar();
		this.setJMenuBar(menuBar);

		resultTable = new JTable(sqlTableData);
		resultTable.getTableHeader().setReorderingAllowed(false);
		resultTable.addMouseListener(this);

		tablePane = new JScrollPane(resultTable);
		resultTable.setFillsViewportHeight(true);

		backPanel.add(BorderLayout.CENTER, tablePane);

		toolTipLabel = new JLabel("Default Tooltip");
		backPanel.add(BorderLayout.SOUTH, toolTipLabel);

		this.add(backPanel);



		this.setVisible(true);
	}

	public void changeTableData(TableModel sqlTableData) {
		resultTable.setModel(sqlTableData);
	}

	public void actionPerformed(ActionEvent e) {}

	public void mouseClicked(MouseEvent e) {
		JTable srcTab = (JTable) e.getSource();

		lastSelectedRow = new String[2][];
		lastSelectedRow[0] = ((MyTableModel) srcTab.getModel()).getColumnNames();
		if (srcTab.getSelectedRow() >= 0) {
			lastSelectedRow[1] = ((MyTableModel) srcTab.getModel()).getRowData(srcTab.getSelectedRow());
		}

		if (e.isAltDown() && lastSelectedRow[1] != null) {
			if (Global.editingForm == null || !Global.editingForm.isVisible()) {
				Global.editingForm = new EditingForm();
			}
			Global.editingForm.setVisible(true);
		}
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
}
