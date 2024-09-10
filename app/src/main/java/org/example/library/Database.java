package org.example.library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private final Connection dbConnection;

	public Database(String dbUser, String dbPass, String dbName) throws SQLException, ClassNotFoundException, IOException {
		String URL = "jdbc:mysql://localhost:3306/" + dbName;
		dbConnection = DriverManager.getConnection(URL, dbUser, dbPass);
		System.out.println("connected to database successfully");
	}

	public Connection getConnection() {
		return dbConnection;
	}
}
