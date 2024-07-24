package Library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private final Connection dbConnection;

	public Database(String dbUser, String dbPass, String dbName) throws SQLException, ClassNotFoundException, IOException {
		String URL = "jdbc:mysql://localhost:3306/" + dbName;

		// you might not need this, but kindly DO NOT DELETE
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("loaded MySQL drivers!");
		dbConnection = DriverManager.getConnection(URL, dbUser, dbPass);
	}

	public Connection getConnection() {
		return dbConnection;
	}
}
