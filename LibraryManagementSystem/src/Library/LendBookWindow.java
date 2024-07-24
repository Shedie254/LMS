package Library;

import javax.swing.*;
import java.sql.Connection;

public class LendBookWindow extends JFrame {
	private final Connection dbConnection;

	public LendBookWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Lend Book");
		setSize(400, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
