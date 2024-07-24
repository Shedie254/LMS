package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
	private final Container contentPane;
	private final Connection dbConnection;

	public LoginWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Login");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = getContentPane();

		placeLoginComponents();
		setVisible(true);

	}

	/**
	 * Authenticates a user by checking the provided email and password against the database in a table called members.
	 *
	 * @param email    the email entered by the user
	 * @param password the password entered by the user
	 * @return true if the email and password match a record in the database, false otherwise
	 */
	private boolean loginUser(String email, String password) {
		try {
			String sql = "SELECT * FROM members WHERE email = ? AND password = ?";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			System.out.println("sql error logging in user: " + e.getMessage());
			JOptionPane.showMessageDialog(contentPane, "error logging in");
			return false;
		}
	}

	private void placeLoginComponents() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);

		JLabel emailLabel = new JLabel("Email");
		emailLabel.setBounds(10, 20, 80, 25);
		topPanel.add(emailLabel);

		JTextField emailText = new JTextField(50);
		emailText.setBounds(100, 20, 165, 25);
		topPanel.add(emailText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 50, 80, 25);
		topPanel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(50);
		passwordText.setBounds(100, 50, 165, 25);
		topPanel.add(passwordText);

		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		topPanel.add(loginButton);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailText.getText();
				String password = new String(passwordText.getPassword());

				if (loginUser(email, password)) {
					JOptionPane.showMessageDialog(contentPane, "Login Successful!");
				} else {
					JOptionPane.showMessageDialog(contentPane, "Invalid email or password!");
				}
			}
		});

		this.add(topPanel);
	}

}
