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
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = getContentPane();

		placeLoginComponents();
		setVisible(true);
	}

	//authenticates the user
	private boolean loginUser(String email, String password) {
		try {
			String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			System.out.println("loginUser: sql error: " + e.getMessage());
			JOptionPane.showMessageDialog(contentPane, "error logging in");
			return false;
		}
	}

	private void placeLoginComponents() {
		JPanel topPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel emailLabel = new JLabel("Email");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		//emailLabel.setForegroundColor(Color.Dark_Gray);

		gbc.anchor = GridBagConstraints.EAST;
		topPanel.add(emailLabel, gbc);

		JTextField emailText = new JTextField(20);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		topPanel.add(emailText, gbc);

		JLabel passwordLabel = new JLabel("Password");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		//passwordLabel.setForegroundColor(Color.Dark_Gray);

		topPanel.add(passwordLabel, gbc);

		JPasswordField passwordText = new JPasswordField(20);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		topPanel.add(passwordText, gbc);

		JButton loginButton = new JButton("Login");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		//loginButton.setBackgroundColor(Color.Green);
		//loginButton.setForegroundColor(Color.white);

		topPanel.add(loginButton, gbc);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailText.getText();
				String password = new String(passwordText.getPassword());

				if (loginUser(email, password)) {
					JOptionPane.showMessageDialog(contentPane, "Login Successful!");
					dispose();
					new DashboardWindow(dbConnection);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Invalid email or password!");
				}
			}
		});

		contentPane.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		contentPane.add(topPanel, gbc);
	}
}
