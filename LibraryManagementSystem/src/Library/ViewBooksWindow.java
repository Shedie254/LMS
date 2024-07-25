package Library;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class ViewBooksWindow extends JFrame {
	private final Connection dbConnection;

	public ViewBooksWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("View Books");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = {"ID", "Title", "Author", "Genre", "Available"};
		Object[][] data = fetchBooksData();

		JTable booksTable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(booksTable);
		add(scrollPane);
	}

	private Object[][] fetchBooksData() {
		try {
			String query = "SELECT * FROM books";
			PreparedStatement statement = dbConnection.prepareStatement(query,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery();

			Object[][] data = new Object[10][5];
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
