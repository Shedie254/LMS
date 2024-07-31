package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LendBookWindow extends JFrame {
	private final Connection dbConnection;
	private final Container contentPane;
	private final Book book;

	public LendBookWindow(Connection dbConnection, Book book) {
		this.dbConnection = dbConnection;
		this.book = book;

		setTitle("Lend Book");
		setSize(800, 600);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.contentPane = getContentPane();

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		// display book cover
		ImageIcon icon = new ImageIcon(book.coverPage());
		JLabel bookCover = new JLabel(icon);
		panel.add(bookCover);

		JPanel rightPanel = lendingForm();
		panel.add(rightPanel);
		add(panel);
		setVisible(true);
	}

	private JPanel lendingForm() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0, 2));

		JLabel emailLabel = new JLabel("Borrowers Email:");
		JTextField emailField = new JTextField(15);
		rightPanel.add(emailLabel);
		rightPanel.add(emailField);

		JButton lendButton = new JButton("Lend");
		lendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bookTitle = book.title();
				String borrowersEmail = emailField.getText();

				// TODO: verify user exists

				String query = "INSERT INTO borrowed_books(book_title, borrowers_email, issue_date) VALUES(?, ?, CURRENT_TIME)";
				try {
					PreparedStatement stmt = dbConnection.prepareStatement(query);
					stmt.setString(1, bookTitle);
					stmt.setString(2, borrowersEmail);
					stmt.executeUpdate();

					JOptionPane.showMessageDialog(contentPane, "book lent to " + borrowersEmail);
				} catch (SQLException ex) {
					System.out.println("lendBookWindow: error lending book: " + ex.getMessage());
					JOptionPane.showMessageDialog(contentPane, "error lending book!");
				}
			}
		});
		rightPanel.add(lendButton);
		return rightPanel;
	}
}
