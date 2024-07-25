package Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookWindow extends JFrame {
	private final JTextField titleField;
	private final JTextField authorField;
	private final JTextField genreField;
	private final Connection dbConnection;

	public AddBookWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Add Book");
		setSize(800, 600);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		titleField = new JTextField("Title");
		authorField = new JTextField("Author");
		genreField = new JTextField("Genre");

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBook();
			}
		});

		add(titleField);
		add(authorField);
		add(genreField);
		add(saveButton);

		setVisible(true);
	}

	private void addBook() {
		String title = titleField.getText();
		String author = authorField.getText();
		String genre = genreField.getText();

		try {
			String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
			PreparedStatement statement = dbConnection.prepareStatement(sql);
			statement.setString(1, title);
			statement.setString(2, author);
			statement.setString(3, genre);
			statement.executeUpdate();

			JOptionPane.showMessageDialog(this, "Book added successfully!");
		} catch (SQLException e) {
			System.out.println("sql error adding book to database: " + e.getMessage());
			JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
		}
	}
}
