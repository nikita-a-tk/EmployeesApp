package gui;

import logic.MyTableModel;

public class SearchResultForm extends TableForm {
	public SearchResultForm(MyTableModel tableModel) {
		super(tableModel);
		this.setTitle("Search Result");
		this.setSize(650, 400);
		toolTipLabel.setText("ALT+Click for editing");
	}
}
