package Library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class SignUpLoginForm {
    /**
    * Places the signup components on the specified panel and sets up the signup button's action listener.
    * This method arranges the name, email, password, and confirm password fields along with the signup button on the panel.
    * When the signup button is clicked, it attempts to register the user with the provided details.
     * @param panel the JPanel on which the signup components will be placed
    * @param frame the JFrame that contains the panel, used for displaying dialog messages
    */
    public static void placeSignUpComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Enter Name");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        JTextField nameText = new JTextField(30);
        nameText.setBounds(100, 20, 165, 25);
        panel.add(nameText);

        JLabel emailLabel = new JLabel("Enter Email");
        emailLabel.setBounds(10, 50, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(50);
        emailText.setBounds(100, 50, 165, 25);
        panel.add(emailText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 80, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(50);
        passwordText.setBounds(100, 80, 165, 25);
        panel.add(passwordText);

        JLabel confirmPasswordLabel = new JLabel("Re-enter Password");
        confirmPasswordLabel.setBounds(10, 110, 130, 25);
        panel.add(confirmPasswordLabel);

        JPasswordField confirmPasswordText = new JPasswordField(50);
        confirmPasswordText.setBounds(150, 110, 165, 25);
        panel.add(confirmPasswordText);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(10, 140, 80, 25);
        panel.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());
                String confirmPassword = new String(confirmPasswordText.getPassword());

                if (password.equals(confirmPassword)) {
                    if (signUpUser(name, email, password)) {
                        JOptionPane.showMessageDialog(frame, "Sign Up Successful!");
                        frame.dispose();
                        showLoginForm();
                    } else {
                        JOptionPane.showMessageDialog(frame, "User already exists or error occurred!");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Passwords do not match!");
                }
            }
        });
    }

    public static void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeLoginComponents(panel, loginFrame);

        loginFrame.setVisible(true);
    }

    public static void placeLoginComponents(JPanel panel, JFrame frame) {
        panel.setLayout(null);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        JTextField emailText = new JTextField(50);
        emailText.setBounds(100, 20, 165, 25);
        panel.add(emailText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(50);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());

                if (authenticateUser(email, password)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid email or password!");
                }
            }
        });
    }

    /**
    * Registers a new user in the database with the provided name, email, and password.
    * @param name the name of the user
    * @param email the email of the user
    * @param password the password associated with the email
    * @return true if the user was successfully registered, false if the user already exists or an error occurred
    */
    private static boolean signUpUser(String name, String email, String password) {
        String sql = "INSERT INTO members (name, email, password, role_id, join_date) VALUES (?, ?, ?, ?, CURRENT_DATE)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            //preparedStatement.setInt(4, 1); // Assuming role_id 1 for 'Member'
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // User already exists
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * Authenticates a user by checking the provided email and password against the database in a table called members.
    * @param email the email entered by the user
    * @param password the password entered by the user
    * @return true if the email and password match a record in the database, false otherwise
    */
    private static boolean authenticateUser(String email, String password) {
        String sql = "SELECT * FROM members WHERE email = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
