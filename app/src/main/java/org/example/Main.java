package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import org.example.library.Database;
import org.example.views.LoginWindow;

public class Main {
	public static void main(String[] args) {
		var props = new Properties();

		try {
			// read environment variables
			String projectDir = System.getProperty("user.dir");
			var envFile = Paths.get(projectDir, ".env");
			var inputStream = Files.newInputStream(envFile);
			props.load(inputStream);

			// developers use different configurations for setting up their database.
			// e.g. different usernames and passwords
			// to avoid changing this section of code everytime a developer wants
			// to connect to their own database, we put these configuration settings
			// in an environment file (.env)
			// create your own .env file from the .env.example file given
			// !!!! make sure you NEVER git push your .env file as it holds your db passwords!!!!
			String dbUser = (String) props.get("DB_USER");
			String dbPass = (String) props.get("DB_PASS");
			String dbName = (String) props.get("DB_NAME");
			Database libraryDB = new Database(dbUser, dbPass, dbName);

			// start GUI application
//			new DashboardWindow(libraryDB.getConnection());
			new LoginWindow(libraryDB.getConnection());

		} catch (IOException | SQLException e) {
			System.out.println("main: error connecting to database: " + e.getMessage());
			System.exit(0);
		} catch (ClassNotFoundException e) {
			System.out.println("main: error connecting to database - MySQL drivers not loaded: " + e.getMessage());
			System.exit(0);
		}
	}
}