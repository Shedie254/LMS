package Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class DashboardWindow extends JFrame {
	private final Connection dbConnection;

	public DashboardWindow(Database mainDB) {
		this.dbConnection = mainDB.getConnection();

		// init window
		setTitle("Library Management System");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		JButton addBookButton = new JButton("Add Book");
		addBookButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new Library.AddBookWindow(dbConnection).setVisible(true);
					}
				});

		JButton viewBooksButton = new JButton("View Books");
		viewBooksButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new Library.ViewBooksWindow(dbConnection).setVisible(true);
					}
				});

		JButton signUpButton = new JButton("SignUp");
		signUpButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new SignUpWindow(dbConnection);
					}
				});

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new LoginWindow(dbConnection);
					}
				});

		JButton viewMembersButton = new JButton("View Members");
		viewMembersButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new ViewMembersWindow(dbConnection).setVisible(true);
					}
				});

		JButton lendBookButton = new JButton("Lend Book");
		lendBookButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new LendBookWindow(dbConnection).setVisible(true);
					}
				});

		JButton returnBookButton = new JButton("Return Book");
		returnBookButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new ReturnBookWindow(dbConnection).setVisible(true);
					}
				});

		JButton globalSearchButton = new JButton("Global Search");
		globalSearchButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new GlobalSearchWindow(dbConnection).setVisible(true);
					}
				});

		add(addBookButton);
		add(viewBooksButton);
		add(signUpButton);
		add(loginButton);
		add(viewMembersButton);
		add(lendBookButton);
		add(returnBookButton);
		add(globalSearchButton);

		setVisible(true);
	}
}
