package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class DashboardWindow extends JFrame {
	private final Connection dbConnection;

	public DashboardWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		// Initialize window
		setTitle("Library Management System");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Left side buttons
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		JButton addBookButton = new JButton("Add Book");
		addBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddBookWindow(dbConnection).setVisible(true);
			}
		});

		JButton viewBooksButton = new JButton("View Books");
		viewBooksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewBooksWindow(dbConnection).setVisible(true);
			}
		});

		JButton signUpButton = new JButton("SignUp");
		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpWindow(dbConnection).setVisible(true);
			}
		});

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoginWindow(dbConnection).setVisible(true);
			}
		});

		JButton viewMembersButton = new JButton("View Members");
		viewMembersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewMembersWindow(dbConnection).setVisible(true);
			}
		});

		JButton lendBookButton = new JButton("Lend Book");
		lendBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new LendBookWindow(dbConnection).setVisible(true);
			}
		});

		JButton returnBookButton = new JButton("Return Book");
		returnBookButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReturnBookWindow(dbConnection).setVisible(true);
			}
		});

		JButton globalSearchButton = new JButton("Global Search");
		globalSearchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new GlobalSearchWindow(dbConnection).setVisible(true);
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

		add(leftPanel, BorderLayout.WEST);

		// Center panel for images
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 4, 10, 10));

		// Path to images folder
		String projectRootDir = System.getProperty("user.dir");
		Path imageDir = Paths.get(projectRootDir, "LibraryManagementSystem", "lib", "images");

		for (int i = 1; i <= 8; i++) {
			String filename = "book" + i + ".jpeg";
			String imageFile = Paths.get(imageDir.toString(), filename).toString();

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

		add(centerPanel, BorderLayout.CENTER);
		setVisible(true);
	}
}
