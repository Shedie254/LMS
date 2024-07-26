package src.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class LendBookWindow extends JFrame {
	private final Connection dbConnection;

	public LendBookWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Lend Book");
		setSize(800, 600);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Font arialFont = new Font("Arial", Font.PLAIN, 12);

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());

		Path imageFile = Paths.get(System.getProperty("user.dir"), "lib", "images", "book1.jpeg");
		ImageIcon icon = new ImageIcon(imageFile.toString());
		JLabel bookCover = new JLabel(icon);
		panel.add(bookCover);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0, 2));

		// book-id
		JLabel titleLabel = new JLabel("Book Title");
		JTextField titleField = new JTextField(15);
		rightPanel.add(titleLabel);
		rightPanel.add(titleField);

		JLabel usernameLabel = new JLabel("Username");
		JTextField usernameField = new JTextField(15);
		rightPanel.add(usernameLabel);
		rightPanel.add(usernameField);

		JButton lendButton = new JButton("Lend");
		lendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: add functionality for book lending
			}
		});
		rightPanel.add(lendButton);
		panel.add(rightPanel);
		add(panel);
		setVisible(true);
	}
}
