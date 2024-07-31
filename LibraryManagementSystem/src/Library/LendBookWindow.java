package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

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

				// verify user exists
				try {
					if (!isMemberExist(borrowersEmail)) {
						throw new NoSuchElementException("Cannot lend book to user who is not a library member");
					}

					lendBook(bookTitle, borrowersEmail);

				} catch (SQLException ex) {
					System.out.println("lendBookWindow: error lending book: " + ex.getMessage());
					JOptionPane.showMessageDialog(contentPane, "error lending book!");
				} catch(NoSuchElementException ex) {
					System.out.println("lendBookWindow: error lending book: " + ex.getMessage());
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}

			}
		});
		rightPanel.add(lendButton);
		return rightPanel;
	}

	private boolean isMemberExist(String userEmail) throws SQLException {
		String query = "SELECT COUNT(*) FROM users WHERE email = ?";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		stmt.setString(1, userEmail);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		return rs.getInt(1) > 0;
	}

	private void lendBook(String bookTitle, String borrowersEmail) throws SQLException {
		String query = "INSERT INTO borrowed_books(book_title, borrowers_email, issue_date) VALUES(?, ?, CURRENT_TIME)";
		PreparedStatement stmt = dbConnection.prepareStatement(query);
		stmt.setString(1, bookTitle);
		stmt.setString(2, borrowersEmail);
		stmt.executeUpdate();

		JOptionPane.showMessageDialog(contentPane, "book lent to " + borrowersEmail);
	}
}
