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

	// Constructor method
	public SignUpWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Register");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		thisFrame = this;
		contentPane = this.getContentPane();
		placeSignUpComponents();

		setVisible(true);
	}

	/**
	 * Places the signup components on the specified panel and sets up the signup button's action listener.
	 * This method arranges the name, email, password, and confirm password fields along with the signup button on the panel.
	 * When the signup button is clicked, it attempts to register the user with the provided details.
	 */
	private void placeSignUpComponents() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		Font labelFont = new Font("Arial", Font.BOLD, 15);
		Color labelColor = new Color(17, 24, 39, 255);
		Color inputColor = new Color(31, 41, 55, 255);
		Font hFont = new Font("Arial", Font.BOLD, 20);
		Font inputFont = new Font("Arial", Font.PLAIN, 12);
		Color btnBgColor = new Color(0, 171, 0, 255);
		Insets margin = new Insets(5, 5, 5, 5);

		// Heading label
		JLabel heading = new JLabel("Library Management System");
		heading.setForeground(labelColor);
		heading.setFont(hFont);
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(heading, gbc);

		// Reset width
		gbc.gridwidth = 1;

		JLabel nameLabel = new JLabel("Enter your Name");
		nameLabel.setForeground(labelColor);
		nameLabel.setFont(labelFont);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(nameLabel, gbc);

		JTextField nameText = new JTextField(30);
		nameText.setMargin(margin);
		nameText.setFont(inputFont);
		nameText.setForeground(inputColor);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(nameText, gbc);

		JLabel emailLabel = new JLabel("Enter your Email");
		emailLabel.setForeground(labelColor);
		emailLabel.setFont(labelFont);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(emailLabel, gbc);

		JTextField emailText = new JTextField(30);
		emailText.setMargin(margin);
		emailText.setFont(inputFont);
		emailText.setForeground(inputColor);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(emailText, gbc);

		JLabel passwordLabel = new JLabel("Enter Password");
		passwordLabel.setForeground(labelColor);
		passwordLabel.setFont(labelFont);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(passwordLabel, gbc);

		JPasswordField passwordText = new JPasswordField(30);
		passwordText.setMargin(margin);
		passwordText.setFont(inputFont);
		passwordText.setForeground(inputColor);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(passwordText, gbc);

		JLabel confirmPasswordLabel = new JLabel("Re-enter Password");
		confirmPasswordLabel.setForeground(labelColor);
		confirmPasswordLabel.setFont(labelFont);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		panel.add(confirmPasswordLabel, gbc);

		JPasswordField confirmPasswordText = new JPasswordField(30);
		confirmPasswordText.setMargin(margin);
		confirmPasswordText.setFont(inputFont);
		confirmPasswordText.setForeground(inputColor);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		panel.add(confirmPasswordText, gbc);

		JButton signUpButton = new JButton("CREATE ACCOUNT");
		signUpButton.setBackground(btnBgColor);
		signUpButton.setForeground(Color.WHITE);
		signUpButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		signUpButton.setPreferredSize(new Dimension(200, 40));
		gbc.gridx = 1;
		gbc.gridy = 5;
		panel.add(signUpButton, gbc);

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

		this.setContentPane(panel);
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
			String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
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
}
