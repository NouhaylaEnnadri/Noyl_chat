package noyl.view;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class botView extends JFrame {

    private DefaultListModel<String> chatModel;
    private JList<String> chatList;

    private JTextField inputField;
    private List<ChatBotView.Intent> intents;
    private JPanel leftBarPanel ;

    private boolean isExpanded = true;

    botView(){
        setTitle("NOYL ChatBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {

       JPanel mainPanel = new JPanel(new BorderLayout());
       JPanel middlePanlel = new JPanel(new BorderLayout());

        //profile bar
        JPanel profileBar = new JPanel();
        profileBar.setBackground(Color.red);
        JLabel profileLabel = new JLabel("Noyl_BOT");
        profileBar.add(profileLabel);

        // Bar at the left
         leftBarPanel = new JPanel();
        leftBarPanel.setPreferredSize(new Dimension(50, getHeight())); // Set the width to 50 and height to the whole height
        JButton leftChatbotLabel = new JButton("☰");
        leftBarPanel.add(leftChatbotLabel);

        leftChatbotLabel.addActionListener(e -> {
            if (isExpanded) {
                collapse();
            } else {
                expand();
            }
            isExpanded = !isExpanded;
        });


        // Chat history display
        chatModel = new DefaultListModel<>();
        chatList = new JList<>(chatModel);
        JScrollPane scrollPane = new JScrollPane(chatList);

        // Input field and emoji button
        inputField = new JTextField(20);
        JButton emojiButton = new JButton("\uD83D\uDE00");
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(emojiButton, BorderLayout.EAST);

        // Send button and input panel
        JButton sendButton = new JButton("Send");

        // Bottom panel for input and send button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        //middle panel
        middlePanlel.add(scrollPane, BorderLayout.CENTER);
        middlePanlel.add(bottomPanel, BorderLayout.SOUTH);
        middlePanlel.add(profileBar, BorderLayout.NORTH);

        // Entire panel
        mainPanel.add(leftBarPanel, BorderLayout.WEST);
        mainPanel.add(middlePanlel, BorderLayout.CENTER);
        add(mainPanel);

        // Add action listeners, set focus, load intents, configure JList
        sendButton.addActionListener(e -> sendButon());
        emojiButton.addActionListener(e -> handleEmojiButtonClick());
        inputField.addActionListener(e -> sendButon());

        intents = loadIntents();
        inputField.requestFocusInWindow();
        configureChatList();
    }

    private void expand() {
        // Code to handle expansion
        leftBarPanel.setSize(new Dimension(200, getHeight()));  // Adjust the size as needed
    }

    private void collapse() {
        // Code to handle collapse
        leftBarPanel.setSize(new Dimension(50, getHeight()));  // Adjust the size as needed
    }
    private void configureChatList() {
    }

    private List<ChatBotView.Intent> loadIntents() {
        return null;
    }

    private void handleEmojiButtonClick() {
    }

    private void sendButon() {
    }

    public static void main(String[] args) {
        // Apply FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new botView();
    }
}
