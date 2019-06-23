package gui;

import logic.ServerHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoundTblsForm extends TableForm {
	private JPanel tablePanel;
	private JTable[] tableSet;
	private JScrollPane[] tablePaneSet;

	public BoundTblsForm() {
		this.setLayout(new BorderLayout());
		this.setSize(750, 400);
		this.setLocation(300, 200);
		this.setTitle("United Tables");
		toolTipLabel.setText("ALT+Click for editing");

		backPanel = new JPanel(new BorderLayout());

		tablePanel = new JPanel();
		tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel productPanel = new JPanel();
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

		menuBar = new TableMenuBar();
		this.setJMenuBar(menuBar);

		int numOfTables = 2;
		tableSet = new JTable[numOfTables];
		tableSet[0] = new JTable();
		tableSet[0].addMouseListener(this);
		tableSet[1] = new JTable(ServerHelper.execSelectEmployee(""));
		tableSet[1].addMouseListener(this); // для отслеживания последней актированной строки

		tablePaneSet = new JScrollPane[numOfTables];
		tablePaneSet[0] = new JScrollPane(tableSet[0]);
		tableSet[0].setFillsViewportHeight(true);
		leftPanel.add(BorderLayout.CENTER, tablePaneSet[0]);
		for (int i = 1; i < numOfTables; i++) {
			tablePaneSet[i] = new JScrollPane(tableSet[i]);
			tableSet[i].setFillsViewportHeight(true);
			tableSet[i].addMouseListener(new CursorTableListener());
			productPanel.add(tablePaneSet[i]);
		}
		tablePanel.add(productPanel);
		tablePanel.add(leftPanel);

		backPanel.add(BorderLayout.CENTER, tablePanel);
		this.add(backPanel);

		this.setVisible(true);
	}

	public class CursorTableListener implements MouseListener {
		public void mouseEntered(MouseEvent e) {}

		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			tableSet[0].setModel(ServerHelper.execSelectJob(" where id = " +
					table.getValueAt(table.getSelectedRow(), 0)));
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
	}
}
