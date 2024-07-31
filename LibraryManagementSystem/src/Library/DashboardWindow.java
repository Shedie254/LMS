package Library;

import javax.swing.*;
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

		// Left side buttons
		JPanel leftPanel = displayLeftSideButtons();
		add(leftPanel, BorderLayout.WEST);

		// Center panel for images
		JPanel centerPanel = displayBookCovers();
		add(centerPanel, BorderLayout.CENTER);
		setVisible(true);
	}

	private JPanel displayBookCovers() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 4, 10, 10));

		if (books.isEmpty()) {
			JLabel label = new JLabel("No books in library");
			centerPanel.add(label);
		} else {
			for (Book book : books) {
				String imageFile = book.coverPage();

				File file = new File(imageFile);
				if (!file.exists()) {
					// inverted control flow, to log book cover pages not loaded.
					// TODO: show a default ImageIcon for books with no cover pages.
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

	private JPanel displayLeftSideButtons() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		JButton addBookButton = new JButton("Add Book");
		addBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddBookWindow(dbConnection);
			}
		});

		JButton viewBooksButton = new JButton("View Books");
		viewBooksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewBooksWindow(dbConnection);
			}
		});

		JButton signUpButton = new JButton("SignUp");
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpWindow(dbConnection);
			}
		});

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginWindow(dbConnection);
			}
		});

		JButton viewMembersButton = new JButton("View Members");
		viewMembersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewMembersWindow(dbConnection);
			}
		});

		JButton lendBookButton = new JButton("Lend Book");
		lendBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LendBookWindow(dbConnection);
			}
		});

		JButton returnBookButton = new JButton("Return Book");
		returnBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReturnBookWindow(dbConnection);
			}
		});

		JButton globalSearchButton = new JButton("Global Search");
		globalSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GlobalSearchWindow(dbConnection);
			}
		});
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.updateComponentTreeUI(frame);
				frame.dispose();

				new DashboardWindow(dbConnection);
			}
		});

		leftPanel.add(addBookButton);
		leftPanel.add(viewBooksButton);
		leftPanel.add(signUpButton);
		leftPanel.add(loginButton);
		leftPanel.add(viewMembersButton);
		leftPanel.add(lendBookButton);
		leftPanel.add(returnBookButton);
		leftPanel.add(globalSearchButton);
		leftPanel.add(refreshButton);
		return leftPanel;
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
