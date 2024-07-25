package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBookWindow extends JFrame {
	private final Connection dbConnection;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField isbnField;
	private JLabel coverImageLabel;
	private File selectedFile;

	public AddBookWindow(Connection connection) {
		this.dbConnection = connection;

		// Initialize window
		setTitle("Add Book");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(6, 2));

		// Fields for book details
		add(new JLabel("Title:"));
		titleField = new JTextField();
		add(titleField);

		add(new JLabel("Author:"));
		authorField = new JTextField();
		add(authorField);

		add(new JLabel("ISBN:"));
		isbnField = new JTextField();
		add(isbnField);

		// Field for cover image
		add(new JLabel("Cover Image:"));
		coverImageLabel = new JLabel("No file selected");
		add(coverImageLabel);

		JButton chooseFileButton = new JButton("Choose File");
		chooseFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					coverImageLabel.setText(selectedFile.getName());
				}
			}
		});
		add(chooseFileButton);

		// Add button to save the book
		JButton addButton = new JButton("Add Book");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBook();
			}
		});
		add(addButton);

		setVisible(true);
	}

	private void addBook() {
		String title = titleField.getText();
		String author = authorField.getText();
		String isbn = isbnField.getText();
		String coverImagePath = selectedFile != null ? selectedFile.getAbsolutePath() : null;

		try {
			String sql = "INSERT INTO books (title, author, isbn, coverpage) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, title);
			preparedStatement.setString(2, author);
			preparedStatement.setString(3, isbn);
			preparedStatement.setString(4, coverImagePath);
			preparedStatement.executeUpdate();

			JOptionPane.showMessageDialog(this, "Book added successfully!");
			dispose();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding book: " + ex.getMessage());
		}
	}
}
