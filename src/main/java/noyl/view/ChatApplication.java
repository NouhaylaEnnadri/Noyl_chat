package noyl.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Properties;

public class ChatApplication extends JFrame {

    private JPanel leftContact;
    private JPanel contactPanel;
    private JPanel chatApplicationPanel;
    private JPanel chatApplication;

    public ChatApplication() throws IOException {
        FrameInit();

        setTitle("NOYL Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void FrameInit() throws IOException {
        chatApplication = new JPanel(new BorderLayout());
        setContentPane(chatApplication);

        // Left panel for contacts with the smallest width

        leftContact();
        // Middle panel for contacts with a bit bigger width
        contact();
        // Right panel for the chat with a lot of width
        chat();

    }
    public void leftContact() throws IOException {
        leftContact = new JPanel();
        leftContact.setLayout(new BoxLayout(leftContact, BoxLayout.Y_AXIS)); // Set layout to vertical

        leftContact.setBackground(Color.decode("#7a7a7a"));

        leftContact.setPreferredSize(new Dimension(70, getHeight())); // Set the preferred size

        JButton logout = new JButton("Logout"); // Add the path to your image file


        // Set button alignment to the left
        logout.setAlignmentX(Component.LEFT_ALIGNMENT);

        Border customBorder = BorderFactory.createLineBorder(Color.decode("#7a7a7a"), 6); // Example: Blue border with a width of 2 pixels
        logout.setBorder(customBorder);
        leftContact.add(Box.createVerticalGlue()); // Push components to the bottom
        leftContact.add(logout);


        chatApplication.add(leftContact, BorderLayout.WEST);
    }





    public void chat(){

        chatApplicationPanel = new JPanel();
        chatApplicationPanel.setBackground((Color.decode("#7a7a7a")));
        chatApplicationPanel.setPreferredSize(new Dimension(500, getHeight())); // Set the preferred size
        chatApplication.add(chatApplicationPanel, BorderLayout.EAST);
    }
    public void contact(){

        contactPanel = new JPanel();

        contactPanel.setBackground((Color.decode("#343434")));
        contactPanel.setPreferredSize(new Dimension(200, getHeight())); // Set the preferred size
        chatApplication.add(contactPanel, BorderLayout.CENTER);



    }

    public static void main(String[] args) throws IOException {
        // Apply FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new ChatApplication();


        }
}
