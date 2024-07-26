package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignUpWindow extends JFrame {
	private final JFrame thisFrame;
	private final Container contentPane;
	private final Connection dbConnection;

	//constructor method
	public SignUpWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Register");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		thisFrame = this;
		contentPane = this.getContentPane();
		placeSignUpComponents();

		setVisible(true);
	}

	/**
	 * Registers a new user in the database with the provided name, email, and password.
	 *
	 * @param name     the name of the user
	 * @param email    the email of the user
	 * @param password the password associated with the email
	 * @return true if the user was successfully registered, false if the user already exists or an error occurred
	 */
	private boolean signUpUser(String name, String email, String password) {
		try {
			String sql = "INSERT INTO users (name, email, password, role, join_date) VALUES (?, ?, ?, ?, CURRENT_DATE)";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, "user"); // assuming a default role of user
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			// User already exists
			JOptionPane.showMessageDialog(contentPane, "User already exists");
			return false;
		} catch (SQLException e) {
			System.out.println("signUpUser: sql error: " + e.getMessage());
			JOptionPane.showMessageDialog(contentPane, "error creating user account");
			return false;
		}
	}

	/**
	 * Places the signup components on the specified panel and sets up the signup button's action listener.
	 * This method arranges the name, email, password, and confirm password fields along with the signup button on the panel.
	 * When the signup button is clicked, it attempts to register the user with the provided details.
	 */
	private void placeSignUpComponents() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(null);

		JLabel nameLabel = new JLabel("Enter your  Name");
		nameLabel.setBounds(10, 20, 80, 25);
		topPanel.add(nameLabel);

		JTextField nameText = new JTextField(30);
		nameText.setBounds(100, 20, 165, 25);
		topPanel.add(nameText);

		JLabel emailLabel = new JLabel("Enter  your Email");
		emailLabel.setBounds(10, 50, 80, 25);
		topPanel.add(emailLabel);

		JTextField emailText = new JTextField(50);
		emailText.setBounds(100, 50, 165, 25);
		topPanel.add(emailText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 80, 80, 25);
		topPanel.add(passwordLabel);

		JPasswordField passwordText = new JPasswordField(50);
		passwordText.setBounds(100, 80, 165, 25);
		topPanel.add(passwordText);

		JLabel confirmPasswordLabel = new JLabel("Re-enter Password");
		confirmPasswordLabel.setBounds(10, 110, 130, 25);
		topPanel.add(confirmPasswordLabel);

		JPasswordField confirmPasswordText = new JPasswordField(50);
		confirmPasswordText.setBounds(150, 110, 165, 25);
		topPanel.add(confirmPasswordText);

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(10, 140, 80, 25);
		topPanel.add(signUpButton);

		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameText.getText();
				String email = emailText.getText();
				String password = new String(passwordText.getPassword());
				String confirmPassword = new String(confirmPasswordText.getPassword());

				if (password.equals(confirmPassword)) {
					System.out.print("passwords match");
					if (signUpUser(name, email, password)) {
						JOptionPane.showMessageDialog(contentPane, "Sign Up Successful!");
						thisFrame.dispose();

						// show login form
						new LoginWindow(dbConnection);
					} else {
						JOptionPane.showMessageDialog(contentPane, "User already exists or error occurred!");
					}
				} else {
					JOptionPane.showMessageDialog(thisFrame, "Passwords do not match!");
				}
			}
		});

		this.setContentPane(topPanel);
	}
}
