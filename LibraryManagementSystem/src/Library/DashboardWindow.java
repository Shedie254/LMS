package Library;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DashboardWindow extends JFrame {
	private final Connection dbConnection;
	private final JFrame frame;
	private ArrayList<Book> books;

	public DashboardWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;
		this.frame = this;

		try {
			this.books = getBooks();
		} catch (SQLException e) {
			System.out.println("error fetching books from database: " + e.getMessage());
			JOptionPane.showMessageDialog(frame, "error fetching books from database");
		}

		// Initialize window
		setTitle("Library Management System");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Top panel for buttons
		JPanel topPanel = displayTopButtons();
		add(topPanel, BorderLayout.NORTH);

		// Center panel for images
		JPanel centerPanel = displayBookCovers();
		add(centerPanel, BorderLayout.CENTER);

		// Add some welcoming colors
		getContentPane().setBackground(new Color(19, 137, 214)); // Light yellow background
		topPanel.setBackground(new Color(22, 77, 134)); // Light blue background

		setVisible(true);
	}

	private JPanel displayBookCovers() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 4, 10, 10));
		centerPanel.setBackground(new Color(204, 255, 204));
		centerPanel.setBorder(new LineBorder(Color.BLACK, 1)); // Visible border

		if (books.isEmpty()) {
			JLabel label = new JLabel("No books in library");
			centerPanel.add(label);
		} else {
			for (Book book : books) {
				String imageFile = book.coverPage();

				File file = new File(imageFile);
				if (!file.exists()) {
					// Show a default ImageIcon for books with no cover pages.
					System.out.println("DashboardWindow: missing book cover " + imageFile);
				} else {
					ImageIcon imageIcon = new ImageIcon(imageFile);
					JLabel imageLabel = new JLabel(imageIcon);
					imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(java.awt.event.MouseEvent evt) {
							new LendBookWindow(dbConnection).setVisible(true);
						}
					});
					centerPanel.add(imageLabel);
				}
			}
		}

		return centerPanel;
	}

	private JPanel displayTopButtons() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());

		JButton addBookButton = createStyledButton("Add Book");
		addBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddBookWindow(dbConnection);
			}
		});

		JButton viewBooksButton = createStyledButton("View Books");
		viewBooksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewBooksWindow(dbConnection);
			}
		});

		JButton signUpButton = createStyledButton("SignUp");
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpWindow(dbConnection);
			}
		});

		JButton loginButton = createStyledButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginWindow(dbConnection);
			}
		});

		JButton viewMembersButton = createStyledButton("View Members");
		viewMembersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewMembersWindow(dbConnection).setVisible(true);
			}
		});

		JButton lendBookButton = createStyledButton("Lend Book");
		lendBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LendBookWindow(dbConnection).setVisible(true);
			}
		});

		JButton returnBookButton = createStyledButton("Return Book");
		returnBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReturnBookWindow(dbConnection).setVisible(true);
			}
		});

		JButton globalSearchButton = createStyledButton("Global Search");
		globalSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GlobalSearchWindow(dbConnection).setVisible(true);
			}
		});

		JButton refreshButton = createStyledButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.updateComponentTreeUI(frame);
				frame.dispose();

				new DashboardWindow(dbConnection);
			}
		});

		topPanel.add(addBookButton);
		topPanel.add(viewBooksButton);
		topPanel.add(signUpButton);
		topPanel.add(loginButton);
		topPanel.add(viewMembersButton);
		topPanel.add(lendBookButton);
		topPanel.add(returnBookButton);
		topPanel.add(globalSearchButton);
		topPanel.add(refreshButton);

		return topPanel;
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(new Color(153, 204, 255)); // Light blue background
		button.setForeground(Color.BLACK); // Black text
		button.setFocusPainted(false);
		button.setBorder(new LineBorder(Color.BLACK, 1));
		return button;
	}

	/*
    Fetch books from database
    */
	private ArrayList<Book> getBooks() throws SQLException {
		ArrayList<Book> books = new ArrayList<>();

		String query = "SELECT * FROM books";
		Statement statement = dbConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			Book book = new Book(
					resultSet.getString("title"),
					resultSet.getString("author"),
					resultSet.getString("isbn"),
					resultSet.getString("genre"),
					resultSet.getString("cover_page")
			);
			books.add(book);
		}
		return books;
	}
}
