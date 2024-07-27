package Library;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;

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
    /**
     * Places the login components on the specified panel and sets up the login button's action listener.
     * This method arranges the email, password and the login button on the panel.
     * When the login button is clicked, it  triggers an action to check if the user exists in the database
     */
    
    private void placeLoginComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL ;

        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Color labelColor = new Color(17, 24, 39, 255);
        Color inputColor = new Color(31, 41, 55, 255);
        Font hFont =  new Font("Arial", Font.BOLD, 20);
        Font inputFont = new Font("Arial", Font.PLAIN, 12);
        Color btnBgColor = new Color(0, 171, 0, 255);
        Insets margin = new Insets(5, 5, 5, 5);

        Vector<String> roles = new Vector<>();
        roles.add("User");
        roles.add("Librarian");
        roles.add("Admin");

        //Heading label
        JLabel heading = new JLabel("Welcome");
        heading.setForeground(labelColor);
        heading.setFont(hFont);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(heading, gbc);

        // reset width
        gbc.gridwidth = 1;

        JLabel emailLabel = new JLabel("Enter Your Email");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailLabel, gbc);

        JTextField emailText = new JTextField(30);
        emailText.setFont(inputFont);
        emailText.setMargin(margin);
        emailText.setForeground(inputColor);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailText, gbc);

        JLabel passwordLabel = new JLabel("Enter  Password");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(30);
        passwordText.setFont(inputFont);
        passwordText.setMargin(margin);
        passwordText.setForeground(inputColor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordText, gbc);

        JLabel roleLabel = new JLabel("User type");
        roleLabel.setFont(labelFont);
        roleLabel.setForeground(labelColor);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(roleLabel, gbc);

        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(roleComboBox, gbc);

        JButton loginButton = new JButton("LOG IN");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton.setBackground(btnBgColor);
        loginButton.setOpaque(true);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        loginButton.setPreferredSize(new Dimension(200, 40));
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailText.getText();
                String password = new String(passwordText.getPassword());
                String role = (String) roleComboBox.getSelectedItem();

                if (loginUser(email, password, role)) {
                    JOptionPane.showMessageDialog(contentPane, "Login Successful!");
                    dispose();
                    //new DashboardWindow(dbConnection);
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Invalid email, password or role!");
                }
            }
        });

        contentPane.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(panel, gbc);
    }
    //This method fetches data from the database to authenticate the user provided credentials
    private boolean loginUser(String email, String password, String role) {
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND role_id = ?";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("SQL error logging in user: " + e.getMessage());
            JOptionPane.showMessageDialog(contentPane, "Error logging in");
            return (false);
        }
    }
}
