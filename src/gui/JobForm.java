package gui;

import logic.ServerHelper;

import java.awt.event.ActionListener;

public class JobForm extends TableForm implements ActionListener {
	public JobForm() {
		super(ServerHelper.execSelectJob(""));
		this.setTitle("Jobs");
		toolTipLabel.setText("ALT+Click for editing");
	}
}
