package Library;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewBooksWindow extends JFrame {
	private final Connection dbConnection;

	public ViewBooksWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("View Books");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = {"ID", "Title", "Author", "ISBN", "Genre"};
		Object[][] data = fetchBooksData();

		JTable booksTable = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(booksTable);
		add(scrollPane);

		setVisible(true);
	}

	private Object[][] fetchBooksData() {
		Object[][] data = new Object[10][5];

		try {
			String query = "SELECT * FROM books";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			int i = 0;

			while (resultSet.next()) {
				data[i][0] = resultSet.getInt("id");
				data[i][1] = resultSet.getString("title");
				data[i][2] = resultSet.getString("author");
				data[i][3] = resultSet.getString("isbn");
				data[i][4] = resultSet.getString("genre");
				i++;
			}

			return data;
		} catch (SQLException e) {
			System.out.println("fetchBooksData: sql error: " + e.getMessage());
			return data;
		}
	}
}
