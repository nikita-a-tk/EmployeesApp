package gui;

import logic.ServerHelper;

public class EmployeeForm extends TableForm {
	public EmployeeForm() {
		super(ServerHelper.execSelectEmployee(""));
		this.setTitle("Employee");
		this.setSize(450, 400);
		toolTipLabel.setText("ALT+Click for editing");
	}

}
