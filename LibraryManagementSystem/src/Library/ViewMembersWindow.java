package Library;

import javax.swing.*;
import java.sql.Connection;

public class ViewMembersWindow extends JFrame {
	private final Connection dbConnection;

	public ViewMembersWindow(Connection dbConnection) {
		this.dbConnection = dbConnection;

		setTitle("View Members");
		setSize(400, 300);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
