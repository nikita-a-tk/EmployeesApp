package gui;

import logic.ServerHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsertingForm extends JFrame implements ActionListener {
	private JTextField nameTextField;
	private JTextField titleTextField;
	private JTextField salaryTextField;
	private JComboBox genderComboBox;
	private JTextField ageTextField;
	private ArrayList<JTextField> fieldsContaner = new ArrayList<JTextField>();


	private String oldId;
	private String oldName;
	private String oldTitle;
	private String oldSalary;
	private String oldGender;
	private String oldAge;

	public InsertingForm() {
		this.setTitle("New");
		this.setSize(300, 200);
		this.setLocation(300, 200);
		this.setLayout(new BorderLayout());

		JPanel backPanel = new JPanel(new BorderLayout());
		backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel editFieldsPanel = new JPanel(new GridLayout(6, 2));

		editFieldsPanel.add(new JLabel("Name"));
		editFieldsPanel.add(nameTextField = new JTextField(15));
		fieldsContaner.add(nameTextField);

		editFieldsPanel.add(new JLabel("Job Title"));
		editFieldsPanel.add(titleTextField = new JTextField(15));
		fieldsContaner.add(titleTextField);

		editFieldsPanel.add(new JLabel("Salary"));
		editFieldsPanel.add(salaryTextField = new JTextField(15));
		fieldsContaner.add(salaryTextField);

		editFieldsPanel.add(new JLabel("Gender"));
		editFieldsPanel.add(genderComboBox = new JComboBox(new String[]{"male", "female"}));

		editFieldsPanel.add(new JLabel("Age"));
		editFieldsPanel.add(ageTextField = new JTextField(15));
		fieldsContaner.add(ageTextField);

		JPanel buttonPanel = new JPanel(new BorderLayout());
		JButton insertButton = new JButton("Add");
		insertButton.addActionListener(this);
		buttonPanel.add(BorderLayout.CENTER, insertButton);

		backPanel.add(BorderLayout.CENTER, editFieldsPanel);
		backPanel.add(BorderLayout.SOUTH, buttonPanel);

		if (TableForm.lastSelectedRow != null) {
			setOldValues(fillFieldsByLastRow());
		}

		this.add(backPanel);
	}

	public void setVisible(boolean visible) {
		if (visible) {
			super.setVisible(true);

			if (TableForm.lastSelectedRow != null) fillFieldsByLastRow();
		} else {
			super.setVisible(false);
		}
	}

	private String[] fillFieldsById(String id) {
		String[] employeeInfo = ServerHelper.getSingleEmployeeInfo(id);

		if (employeeInfo != null) {
			nameTextField.setText(employeeInfo[1]);
			titleTextField.setText(employeeInfo[5]);
			salaryTextField.setText(employeeInfo[6]);
			genderComboBox.setSelectedIndex((employeeInfo[3].endsWith("male")) ? 0 : 1);
			ageTextField.setText(employeeInfo[2]);
		} else {
			nameTextField.setText("");
			titleTextField.setText("");
			salaryTextField.setText("");
			genderComboBox.setSelectedIndex(0);
			ageTextField.setText("");
		}

		oldTitle = titleTextField.getText();

		return employeeInfo;
	}

	private String[] fillFieldsByLastRow() {
		if (TableForm.lastSelectedRow != null) {
			String[] employeeInfo = fillFieldsById(TableForm.lastSelectedRow[1][0]);

			String lastWorker = null;
			String lastSalary = null;
			int numOfCols = TableForm.lastSelectedRow[0].length;

			for (int i = 0; i < numOfCols; i++)
			{
				if (TableForm.lastSelectedRow[0][i] == "title" && TableForm.lastSelectedRow[1][i] != null) {
					lastWorker = TableForm.lastSelectedRow[1][i];
				}
				if (TableForm.lastSelectedRow[0][i] == "salary" && TableForm.lastSelectedRow[1][i] != null) {
					lastSalary = TableForm.lastSelectedRow[1][i];
				}
			}

			if (lastWorker != null) {
				titleTextField.setText(lastWorker);
				salaryTextField.setText(lastSalary);
				oldTitle = lastWorker;
				employeeInfo[5] = lastWorker;
				employeeInfo[6] = lastSalary;
			}

			return employeeInfo;
		} else {
			nameTextField.setText("");
			titleTextField.setText("");
			salaryTextField.setText("");
			genderComboBox.setSelectedIndex(0);
			ageTextField.setText("");
			return null;
		}
	}

	private void setOldValues(String[] values) {
		if (values != null) {
			oldId = values[0];
			oldName = values[1];
			oldTitle = values[5];
			oldSalary = values[6];
			oldGender = values[3];
			oldAge = values[2];
		}
	}

	public void actionPerformed(ActionEvent e) {
		boolean emptyFieldExist = false;
		for (JTextField field : fieldsContaner) {
			if (field.getText().length() == 0) emptyFieldExist = true;
		}

		if (!emptyFieldExist) {
			try {
				Integer.parseInt(salaryTextField.getText());
				Integer.parseInt(ageTextField.getText());

				int answer = JOptionPane.showConfirmDialog(this, String.format("New Row: Name = %s, Gender = %s, Age = %s, Job Title = %s, Salary = %s.",
																				nameTextField.getText(), genderComboBox.getSelectedItem(), ageTextField.getText(), titleTextField.getText(), salaryTextField.getText()),
																		"Warning", JOptionPane.YES_NO_OPTION);

				if (answer == 0) {
					ServerHelper.execInsertEmployeeInfo(nameTextField.getText(),
														titleTextField.getText(),
														salaryTextField.getText(),
														genderComboBox.getSelectedItem() == "male" ? 1 : 2,
														ageTextField.getText());

					JOptionPane.showMessageDialog(this, "Data was added", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				JOptionPane.showMessageDialog(this, "Invalid data was entered", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(this, "All field should be filled", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
