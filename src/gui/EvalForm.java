package gui;

import logic.ServerHelper;

import javax.swing.*;
import java.awt.*;

public class EvalForm extends TableForm {
	private JLabel salaryLabel[];
	private JComboBox employeeComboBox;

	public EvalForm () {
		super(ServerHelper.execSelectSalaryBonus());
		this.setTitle("Evaluated Fields");
		this.setSize(450, 580);
		toolTipLabel.setText("ALT+Click for editing");

		backPanel.remove(tablePane);
		backPanel.add(BorderLayout.SOUTH, tablePane);

		SpringLayout layout = new SpringLayout();
		JPanel evalFieldsPanel = new JPanel(layout);
		evalFieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel captionLabel[] = new JLabel[3];
		captionLabel[0] = new JLabel("Maximum Salary:");
		captionLabel[1] = new JLabel("Minimum salary:");
		captionLabel[2] = new JLabel("Average salary:");
		for (JLabel label : captionLabel) {
			evalFieldsPanel.add(label);
		}

		salaryLabel = new JLabel[4];
		salaryLabel[0] = new JLabel("" + ServerHelper.execSelectEvalSalary("max"));
		salaryLabel[1] = new JLabel("" + ServerHelper.execSelectEvalSalary("min"));
		salaryLabel[2] = new JLabel("" + ServerHelper.execSelectEvalSalary("avg"));
		salaryLabel[3] = new JLabel();
		for (JLabel textField : salaryLabel) {
			evalFieldsPanel.add(textField);
		}

		int spacing = 10;
		layout.putConstraint(SpringLayout.NORTH, captionLabel[0],
				0, SpringLayout.NORTH, captionLabel[0].getParent());
		layout.putConstraint(SpringLayout.NORTH, salaryLabel[0],
				0, SpringLayout.NORTH, salaryLabel[0].getParent());
		layout.putConstraint(SpringLayout.WEST, salaryLabel[0],
				spacing, SpringLayout.EAST, captionLabel[0]);
		layout.putConstraint(SpringLayout.NORTH, captionLabel[1],
				spacing, SpringLayout.SOUTH, captionLabel[0]);
		layout.putConstraint(SpringLayout.NORTH, salaryLabel[1],
				spacing, SpringLayout.SOUTH, salaryLabel[0]);
		layout.putConstraint(SpringLayout.WEST, salaryLabel[1],
				spacing, SpringLayout.EAST, captionLabel[1]);
		layout.putConstraint(SpringLayout.NORTH, captionLabel[2],
				spacing, SpringLayout.SOUTH, captionLabel[1]);
		layout.putConstraint(SpringLayout.NORTH, salaryLabel[2],
				spacing, SpringLayout.SOUTH, salaryLabel[1]);
		layout.putConstraint(SpringLayout.WEST, salaryLabel[2],
				spacing, SpringLayout.EAST, captionLabel[2]);

		backPanel.add(BorderLayout.CENTER, evalFieldsPanel);
	}

}
