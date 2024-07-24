package Library;

import javax.swing.*;
import java.sql.Connection;

public class GlobalSearchWindow extends JFrame {
	private final Connection dbConnection;

	public GlobalSearchWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("Search Books");
		setSize(400, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
