package Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookWindow extends JFrame {
    private JTextField titleField;
    private JTextField authorField;
    private JTextField genreField;
    private JButton saveButton;

    public AddBookWindow() {
        setTitle("Add Book");
        setSize(400, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        titleField = new JTextField("Title");
        authorField = new JTextField("Author");
        genreField = new JTextField("Genre");
        saveButton = new JButton("Save");

        add(titleField);
        add(authorField);
        add(genreField);
        add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
    }

    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String genre = genreField.getText();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, genre);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }
}
