package gui;

import logic.ServerHelper;

public class JoinForm extends TableForm {
	public JoinForm() {
		super(ServerHelper.execSelectJoin(""));
		this.setTitle("Joined Table");
		this.setSize(650, 400);
		toolTipLabel.setText("ALT+Click for editing");
	}
}
