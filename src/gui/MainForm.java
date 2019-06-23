package gui;

import logic.ServerHelper;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
	private TableMenuBar menuBar;

	private MainForm() {
		this.setTitle("Employees");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(260, 50);
		this.setLocation(200, 200);

		JPanel backPanel = new JPanel(new BorderLayout());

		menuBar = new TableMenuBar();
		this.setJMenuBar(menuBar);

		this.add(BorderLayout.CENTER, backPanel);

		ServerHelper.setConnection();

		this.setVisible(true);
	}

	public static void main(String[] args) {
		new MainForm();
	}
}

