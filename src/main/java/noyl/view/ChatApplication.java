package noyl.view;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ChatApplication extends JFrame {

    private JPanel leftContact;
    private JPanel contactPanel;
    private JPanel chatApplicationPanel;
    private JPanel chatApplication;
    private JTextArea messagesTextArea;
    private JTextField messageInput;

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

        leftContact.setPreferredSize(new Dimension(50, getHeight())); // Set the preferred size
        File selectedFile = Register.getSelectedFile();

        System.out.println(selectedFile);
        JLabel label = new JLabel();
       /* try {
          //  Image originalImage = ImageIO.read(selectedFile);
           // Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            //   label.setIcon(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println(selectedFile);


        JButton logout = new JButton("parametre"); // Add the path to your image file
        logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Set button alignment to the left

        leftContact.add(Box.createVerticalGlue()); // Push components to the bottom
        leftContact.add(label);

        chatApplication.add(leftContact, BorderLayout.WEST);
    }


    public void chat() {
        chatApplicationPanel = new JPanel(new BorderLayout());
        chatApplicationPanel.setBackground((Color.white));
        chatApplicationPanel.setPreferredSize(new Dimension(500, getHeight())); // Set the preferred size
        chatApplication.add(chatApplicationPanel, BorderLayout.EAST);

        // Top panel with user information and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 5));
        topPanel.add(new JLabel("Username"));
        topPanel.add(Box.createHorizontalGlue()); // Expands to push elements to the sides
        topPanel.add(new JLabel("Online"));
        topPanel.add(new JButton("Call"));
        topPanel.add(new JButton("Video Call"));
        topPanel.add(new JButton("+"));

        // Middle panel with messages
        JPanel messagesPanel = new JPanel();
        messagesPanel.setBackground((Color.gray));
        messagesPanel.setLayout(new GridLayout(2, 2));

        // JTextArea for displaying messages
        messagesTextArea = new JTextArea();
        messagesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messagesTextArea);
        messagesPanel.add(scrollPane);

        // Bottom panel with message input
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JButton("😊")); // Emoji button

        // JTextField for message input
        messageInput = new JTextField();
        bottomPanel.add(messageInput);

        // JButton for sending messages
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        bottomPanel.add(sendButton);

        // Add panels to the main panel
        chatApplicationPanel.add(topPanel, BorderLayout.NORTH);
        chatApplicationPanel.add(messagesPanel, BorderLayout.CENTER);
        chatApplicationPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            messagesTextArea.append("You: " + message + "\n");
            messageInput.setText(""); // Clear the input field after sending a message
        }
    }
    public void contact() {

        contactPanel = new JPanel();
        // Set BoxLayout for the contact panel
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));

        // Set BoxLayout for the contact panel
        contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));

        // Create the search bar with small size
        JTextField searchBar = new JTextField("search");
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, searchBar.getPreferredSize().height)); // Set max width
        // Add other search bar customization as needed
        contactPanel.add(searchBar);

        // Create a list of contacts with images (you can replace this with your actual contact list logic)
        String[] contactData = {"Contact 1", "Contact 2", "Contact 3"};
        DefaultListModel<String> contactListModel = new DefaultListModel<>();
        for (String contact : contactData) {
            contactListModel.addElement(contact);
        }
        JList<String> contactList = new JList<>(contactListModel);
        JScrollPane contactScrollPane = new JScrollPane(contactList);

        // Add the contact list to the contact panel
        contactPanel.add(contactScrollPane);

        // Add the contact panel to the main panel
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
