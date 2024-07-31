package Library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewMembersWindow extends JFrame {
	private final Connection dbConnection;
	private JTable membersTable;
	private DefaultTableModel tableModel;

	public ViewMembersWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("View Members");
		setSize(600, 400);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		initializeComponents();
		loadMembersData();

		setVisible(true);
	}

	private void initializeComponents() {
		// Create a table model with column names that match your database schema
		tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Email"}, 0);
		membersTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(membersTable);

		add(scrollPane);
	}

	private void loadMembersData() {
		String sql = "SELECT id, name, email FROM users"; // Adjust SQL query to match actual columns

		try (PreparedStatement stmt = dbConnection.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {

			// Clear the existing data
			tableModel.setRowCount(0);

			while (rs.next()) {
				// Retrieve data from result set
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");

				// Add a row to the table model
				tableModel.addRow(new Object[]{id, name, email});
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error retrieving data from database.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// For testing purposes, ensure you provide a valid Connection object
		try {
			Database database = new Database("dbUser", "dbPass", "dbName");
			Connection connection = database.getConnection();
			new ViewMembersWindow(connection).setVisible(true);
		} catch (SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
