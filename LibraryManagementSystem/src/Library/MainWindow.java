package Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JButton addBookButton;
    private JButton viewBooksButton;
    private JButton addMemberButton;
    private JButton viewMembersButton;
    private JButton lendBookButton;
    private JButton returnBookButton;
    private JButton globalSearchButton;

    public MainWindow() {
        setTitle("Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addBookButton = new JButton("Add Book");
        viewBooksButton = new JButton("View Books");
        addMemberButton = new JButton("Add Member");
        viewMembersButton = new JButton("View Members");
        lendBookButton = new JButton("Lend Book");
        returnBookButton = new JButton("Return Book");
        globalSearchButton = new JButton("Global Search");

        add(addBookButton);
        add(viewBooksButton);
        add(addMemberButton);
        add(viewMembersButton);
        add(lendBookButton);
        add(returnBookButton);
        add(globalSearchButton);

        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Library.AddBookWindow().setVisible(true);
            }
        });

        viewBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Library.ViewBooksWindow().setVisible(true);
            }
        });

        addMemberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddMemberWindow().setVisible(true);
            }
        });

        viewMembersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewMembersWindow().setVisible(true);
            }
        });

        lendBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LendBookWindow().setVisible(true);
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnBookWindow().setVisible(true);
            }
        });

        globalSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GlobalSearchWindow().setVisible(true);
            }
        });
    }
}
