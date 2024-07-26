package Library;

import javax.swing.*;
import java.sql.Connection;

public class ReturnBookWindow extends JFrame {
	private final Connection dbConnection;

	public ReturnBookWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Return Book");
		setSize(400, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// TODO: implement return book feature
	}
}
