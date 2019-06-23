package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableMenuBar extends JMenuBar {
	private JMenu tblsMenu;
	private JMenu singleTblsMenu; // single tables menu
	private JMenuItem[] singleTblsMenus; // single tables
	private JMenuItem joinTblMenu; // joined table
	private JMenuItem boundTblsMenu; // bounded tables menu
	private JMenuItem evalFormMenu; // evaluated fields menu
	private JMenu managingMenu; 
	private JMenuItem searchFormMenuItem; // search menu item
	private JMenuItem editingFormMenuItem; // edit menu item
	private JMenuItem insertingFormMenuItem; // add menu item
	private JMenuItem deletionFormMenuItem; // deletion menu item

	public TableMenuBar() {

		tblsMenu = new JMenu("Tables");
		this.add(tblsMenu);

		singleTblsMenu = new JMenu("Single Tables");
		tblsMenu.add(singleTblsMenu);

		singleTblsMenus = new JMenuItem[2];
		singleTblsMenus[0] = new JMenuItem("Employee");
		singleTblsMenus[0].addActionListener(new EmployeeMenuItemListener());
		singleTblsMenus[1] = new JMenuItem("Job");
		singleTblsMenus[1].addActionListener(new JobMenuItemListener());
		for (JMenuItem item : singleTblsMenus) {
			singleTblsMenu.add(item);
		}

		joinTblMenu = new JMenuItem("Joined Table");
		joinTblMenu.addActionListener(new JoinMenuItemListener());
		tblsMenu.add(joinTblMenu);

		boundTblsMenu = new JMenuItem("Bouned tables");
		boundTblsMenu.addActionListener(new BoundTblsMenuItemListener());
		tblsMenu.add(boundTblsMenu);

		evalFormMenu = new JMenuItem("Evaluated Fields");
		evalFormMenu.addActionListener(new EvalFormMenuItemListener());
		tblsMenu.add(evalFormMenu);
		
		managingMenu = new JMenu("Manage");
		this.add(managingMenu);
		
		searchFormMenuItem = new JMenuItem("Search");
		searchFormMenuItem.addActionListener(new SearchFormMenuItemListener());
		managingMenu.add(searchFormMenuItem);

		editingFormMenuItem = new JMenuItem("Edit");
		editingFormMenuItem.addActionListener(new EditingFormMenuItemListener());
		managingMenu.add(editingFormMenuItem);

		insertingFormMenuItem = new JMenuItem("Add");
		insertingFormMenuItem.addActionListener(new InsertingFormMenuItemListener());
		managingMenu.add(insertingFormMenuItem);

		deletionFormMenuItem = new JMenuItem("Delete");
		deletionFormMenuItem.addActionListener(new DeletionFormMenuItemListener());
		managingMenu.add(deletionFormMenuItem);
	}

	private class EmployeeMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.employeeForm == null || !Global.employeeForm.isVisible()) {
				Global.employeeForm = new EmployeeForm();
			}
			Global.employeeForm.setVisible(true);
		}
	}

	private class JobMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.jobForm == null || !Global.jobForm.isVisible()) {
				Global.jobForm = new JobForm();
			}
			Global.jobForm.setVisible(true);
		}
	}

	private class JoinMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.joinForm == null || !Global.joinForm.isVisible()) {
				Global.joinForm = new JoinForm();
			}
			Global.joinForm.setVisible(true);
		}
	}

	private class BoundTblsMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.boundTblsForm == null || !Global.boundTblsForm.isVisible()) {
				Global.boundTblsForm = new BoundTblsForm();
			}
			Global.boundTblsForm.setVisible(true);
		}
	}

	private class EvalFormMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.evalForm == null || !Global.evalForm.isVisible()) {
				Global.evalForm = new EvalForm();
			}
			Global.evalForm.setVisible(true);
		}
	}

	private class SearchFormMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.searchForm == null || !Global.searchForm.isVisible()) {
				Global.searchForm = new SearchForm();
			}
			Global.searchForm.setVisible(true);
		}
	}

	private class EditingFormMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.editingForm == null || !Global.editingForm.isVisible()) {
				Global.editingForm = new EditingForm();
			}
			Global.editingForm.setVisible(true);
		}
	}
	
	private class InsertingFormMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.insertingForm == null || !Global.insertingForm.isVisible()) {
				Global.insertingForm = new InsertingForm();
			}
			Global.insertingForm.setVisible(true);
		}
	}

	private class DeletionFormMenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (Global.deletionForm == null || !Global.deletionForm.isVisible()) {
				Global.deletionForm = new DeletionForm();
			}
			Global.deletionForm.setVisible(true);
		}
	}
}
