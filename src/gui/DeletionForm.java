package gui;

import logic.MyTableModel;
import logic.ServerHelper;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Arrays;

public class DeletionForm extends TableForm implements KeyListener {
    public DeletionForm() {
        super(ServerHelper.execSelectJoin(""));
        this.setTitle("Deletion");
        this.setSize(650, 400);
        toolTipLabel.setText("Select rows and press DELETE");

        this.resultTable.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {

            int[] selectedRows = resultTable.getSelectedRows();
            String[] employeeIds = new String[selectedRows.length];
            int i = 0;

            String response = new String();
            for (int rowIndex : selectedRows) {
                employeeIds[i] = (String) resultTable.getValueAt(rowIndex, 0);
                response += String.format("%s\n", Arrays.toString(((MyTableModel) resultTable.getModel()).getRowData(rowIndex)));

                i++;
            }

            int answer = JOptionPane.showConfirmDialog(this, "Do you want to delete?\n" + response,
                    "Warning", JOptionPane.YES_NO_OPTION);

            if (answer == 0) {
                ServerHelper.execDeleteEmployee(employeeIds);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
