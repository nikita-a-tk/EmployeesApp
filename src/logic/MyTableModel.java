package logic;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class MyTableModel extends AbstractTableModel {
	private Vector<Vector<String>> tableData = new Vector<Vector<String>>();
	private Vector<String> columns = new Vector<String>();

	public MyTableModel(String[] columnNames) {
		for (String name : columnNames) {
			columns.add(name);
		}
	}

	public MyTableModel(ArrayList<String> columnNames) {
		for (String name : columnNames) {
			columns.add(name);
		}
	}

	public int getRowCount() {
		return tableData.size();
	}

	public int getColumnCount() {
		if (tableData.size() > 0) {
			return tableData.firstElement().size();
		} else {
			return 0;
		}
	}

	public String getColumnName(int columnIndex) {
		return columns.get(columnIndex);
	}

	public String[] getColumnNames() {
		int numOfCols = this.getColumnCount();
		String colNames[] = new String[numOfCols];
		for (int i = 0; i < numOfCols; i++) {
			colNames[i] = this.getColumnName(i);
		}
		return colNames;
	}

	public Class<?> getColumnClass(int columnIndex) {
		if (getValueAt(0, columnIndex) != null) {
			return getValueAt(0, columnIndex).getClass();
		} else {
			return null;
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if ((rowIndex >= 0) && (tableData.get(rowIndex).get(columnIndex) != null)) {
			return tableData.get(rowIndex).get(columnIndex);
		} else {
			return null;
		}
	}

	public String[] getRowData(int rowIndex) {
		Object[] tmpRow = tableData.get(rowIndex).toArray();
		String[] resultRow = new String[tmpRow.length];
		for (int i = 0; i < tmpRow.length; i++) {
			resultRow[i] = (String) tmpRow[i];
		}
		return resultRow;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		tableData.get(rowIndex).setElementAt((String) aValue, columnIndex);
	}

	public void addRow(Vector<String> row) {
		tableData.add(row);
	}

	public void addTableModelListener(TableModelListener l) {}

	public void removeTableModelListener(TableModelListener l) {}

	public static MyTableModel getEmptyTableModel() {
		MyTableModel emptyModel = new MyTableModel(new String[]{"empty set"});
		Vector<String> emptyRow = new Vector<String>(1);
		emptyRow.add("empty");
		emptyModel.addRow(emptyRow);

		return emptyModel;
	}
}
