package Library;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ReturnBookWindow extends JFrame {
    private final Connection dbConnection;
    private final Container contentPane;
    private JTextField issueDateField;
    private JTextField expectedReturnDateField;
    private JTextField actualReturnDateField;
    private JLabel fineLabel;

    public ReturnBookWindow(Connection dbConnection) {
        this.dbConnection = dbConnection;

        setTitle("Return Book");
        setSize(800, 600);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        contentPane = getContentPane();

        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 15);
        Font hFont = new Font("Arial", Font.BOLD, 20);
        Color btnBgColor = new Color(0, 171, 0, 255);
        Insets margin = new Insets(5, 5, 5, 5);

        JLabel heading = new JLabel("Return Book");
        heading.setFont(hFont);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;

        JLabel borrowersEmail = new JLabel("Email");
        borrowersEmail.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(borrowersEmail, gbc);

        JTextField email = new JTextField(30);
        email.setMargin(margin);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(email, gbc);

        JLabel bookTitle = new JLabel("Book Title");
        bookTitle.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(bookTitle, gbc);

        JTextField title = new JTextField(30);
        title.setMargin(margin);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);

        
        /*
         * what if tueke such that tunafetch dates ka issue_date, expected_return_date from the database  and display
         * them to the user as readonly input fields then the difference in number of days
         * itumike kucalculate the fine amount if the user exceeds the expected_return_date ???
         */

        /*JLabel issueDateLabel = new JLabel("Issue Date");
        issueDateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(issueDateLabel, gbc);

        issueDateField = new JTextField(30);
        issueDateField.setMargin(margin);
        issueDateField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(issueDateField, gbc);

        JLabel expectedReturnDateLabel = new JLabel("Expected Return Date");
        expectedReturnDateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(expectedReturnDateLabel, gbc);

        expectedReturnDateField = new JTextField(30);
        expectedReturnDateField.setMargin(margin);
        expectedReturnDateField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(expectedReturnDateField, gbc);

        JLabel actualReturnDateLabel = new JLabel("Return Date (yyyy-MM-dd)");
        actualReturnDateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(actualReturnDateLabel, gbc);

        actualReturnDateField = new JTextField(30);
        actualReturnDateField.setMargin(margin);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(actualReturnDateField, gbc);


        
        The fine label  will be displyed after fetching the issue_date and  expected_return_date and only if
        the actual_return_date exceeds the expected_return_date
        You'll have to alter the borrowed_books table and add the missing fields
         
        fineLabel = new JLabel("");
        fineLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(fineLabel, gbc);

        JButton fetchIssueDateButton = new JButton("Fetch Dates");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.CENTER;
        fetchIssueDateButton.setBackground(btnBgColor);
        fetchIssueDateButton.setOpaque(true);
        fetchIssueDateButton.setForeground(Color.WHITE);
        fetchIssueDateButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        fetchIssueDateButton.setPreferredSize(new Dimension(200, 40));
        panel.add(fetchIssueDateButton, gbc);*/

        JButton submitButton = new JButton("Return");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton.setBackground(btnBgColor);
        submitButton.setOpaque(true);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        submitButton.setPreferredSize(new Dimension(200, 40));
        panel.add(submitButton, gbc);

        contentPane.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(panel, gbc);
    }
}
