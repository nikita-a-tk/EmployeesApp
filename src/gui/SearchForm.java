package gui;

import logic.ServerHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchForm extends JFrame implements ActionListener {
	private JTextField idTextField;
	private JTextField nameTextField;
	private JComboBox genderComboBox;
	private JTextField titleTextField;
	private JTextField minSalaryTextField;
	private JTextField maxSalaryTextField;
	private JLabel idLabel;
	private JLabel nameLabel;
	private JLabel genderLabel;
	private JLabel titleLabel;
	private JLabel salaryLabel;
	private JButton searchButton;
	private JPanel searchPanel;
	private JPanel buttonPanel;

	public SearchForm() {
		this.setTitle("Search");
		this.setLocation(300, 200);
		this.setSize(400, 200);

		JPanel backPanel = new JPanel(new BorderLayout());
		backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		searchPanel = new JPanel(new GridLayout(6, 2));

		idLabel = new JLabel("id");
		nameLabel = new JLabel("Name");
		genderLabel = new JLabel("Gender");
		titleLabel = new JLabel("Job Title");
		salaryLabel = new JLabel("Salary");

		idTextField = new JTextField(15);
		nameTextField = new JTextField(15);
		genderComboBox = new JComboBox(new String[]{"male", "female"});
		titleTextField = new JTextField(15);
		minSalaryTextField = new JTextField(15);
		maxSalaryTextField = new JTextField(15);

		searchPanel.add(new JLabel("Parameter"));
		searchPanel.add(new JLabel("Value"));
		searchPanel.add(idLabel);
		searchPanel.add(idTextField);
		searchPanel.add(nameLabel);
		searchPanel.add(nameTextField);
		searchPanel.add(genderLabel);
		searchPanel.add(genderComboBox);
		searchPanel.add(titleLabel);
		searchPanel.add(titleTextField);
		searchPanel.add(salaryLabel);
		JPanel salaryPanel = new JPanel();
		BoxLayout layout = new BoxLayout(salaryPanel, BoxLayout.X_AXIS);
		salaryPanel.setLayout(layout);
		salaryPanel.add(new JLabel("min: "));
		salaryPanel.add(minSalaryTextField);
		salaryPanel.add(new JLabel("max: "));
		salaryPanel.add(maxSalaryTextField);
		searchPanel.add(salaryPanel);

		searchButton = new JButton("Find");
		searchButton.addActionListener(this);

		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(BorderLayout.CENTER, searchButton);

		backPanel.add(BorderLayout.CENTER, searchPanel);
		backPanel.add(BorderLayout.SOUTH, buttonPanel);

		this.add(backPanel);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		openSearchResultForm();
	}

	protected void openSearchResultForm() {
		trimFields();

		int id = -1;
		String name = nameTextField.getText();
		int gender = genderComboBox.getSelectedItem() == "male" ? 1 : 2;
		String title = titleTextField.getText();
		int minSalary = -1;
		int maxSalary = -1;

		try {
			if (idTextField.getText().length() > 0) id = Integer.parseInt(idTextField.getText());
			if (minSalaryTextField.getText().length() > 0) { minSalary = Integer.parseInt(minSalaryTextField.getText()); }
			if (maxSalaryTextField.getText().length() > 0) { maxSalary = Integer.parseInt(maxSalaryTextField.getText()); }

			if (Global.searchResultForm != null) {
				Global.searchResultForm.dispose();
			}
			Global.searchResultForm = new SearchResultForm(ServerHelper.execSearchQuery(id, name, gender, title, minSalary, maxSalary));
		} catch (NumberFormatException exc) {
			JOptionPane.showMessageDialog(this, "Invalid data was entered", "Error", JOptionPane.ERROR_MESSAGE);
			exc.printStackTrace();
		}
	}

	private void trimFields() {
		idTextField.setText(idTextField.getText().trim());
		nameTextField.setText(nameTextField.getText().trim());
		titleTextField.setText(titleTextField.getText().trim());
		minSalaryTextField.setText(minSalaryTextField.getText().trim());
		maxSalaryTextField.setText(maxSalaryTextField.getText().trim());
	}
}

