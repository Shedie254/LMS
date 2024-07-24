package Library;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewBooksWindow extends JFrame {
    private JTable booksTable;

    public ViewBooksWindow() {
        setTitle("View Books");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Title", "Author", "Genre", "Available"};
        Object[][] data = fetchBooksData();

        booksTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane);
    }

    private Object[][] fetchBooksData() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            Object[][] data = new Object[rowCount][5];
            int i = 0;

            while (resultSet.next()) {
                data[i][0] = resultSet.getInt("id");
                data[i][1] = resultSet.getString("title");
                data[i][2] = resultSet.getString("author");
                data[i][3] = resultSet.getString("genre");
                data[i][4] = resultSet.getBoolean("is_available");
                i++;
            }

            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }
}
