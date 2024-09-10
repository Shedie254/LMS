package org.example.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GlobalSearchWindow extends JFrame {
	private final Connection dbConnection;
	private JTextField searchField;
	private JTextArea resultArea;

	public GlobalSearchWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Search Books");
		setSize(400, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initializeComponents();

		setVisible(true);
	}

	private void initializeComponents() {
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout());

		JLabel searchLabel = new JLabel("Enter search query:");
		searchField = new JTextField(20);
		JButton searchButton = new JButton("Search");

		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);

		resultArea = new JTextArea(10, 30);
		resultArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(resultArea);

		add(searchPanel);
		add(scrollPane);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = searchField.getText();
				if (!query.isEmpty()) {
					searchDatabase(query);
				} else {
					JOptionPane.showMessageDialog(GlobalSearchWindow.this, "Please enter a search query.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void searchDatabase(String query) {
		String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";

		try (PreparedStatement stmt = dbConnection.prepareStatement(sql)) {
			stmt.setString(1, "%" + query + "%");
			stmt.setString(2, "%" + query + "%");
			stmt.setString(3, "%" + query + "%");

			try (ResultSet rs = stmt.executeQuery()) {
				StringBuilder results = new StringBuilder();

				while (rs.next()) {
					results.append("Title: ").append(rs.getString("title")).append("\n");
					results.append("Author: ").append(rs.getString("author")).append("\n");
					results.append("Genre: ").append(rs.getString("genre")).append("\n");
					results.append("----------------------\n");
				}

				if (results.length() == 0) {
					results.append("No results found.");
				}

				resultArea.setText(results.toString());
			}
		} catch (SQLException e) {
			System.out.println("searchDatabase: sql error: " + e.getMessage());
			JOptionPane.showMessageDialog(this, "Error querying the database.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// this is not the entry for our program. Please refer to Main.java file
}
