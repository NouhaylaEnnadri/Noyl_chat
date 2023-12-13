package noyl.view;

import com.formdev.flatlaf.FlatDarkLaf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import noyl.DatabaseConnection.DatabaseConnection;
import noyl.model.LoginModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatBotView  extends JFrame {


    private JPanel leftContact;
    private JPanel contactPanel;
    private JPanel chatApplicationPanel;
    private JPanel chatApplication;
    private JTextArea messagesTextArea;
    private JTextField messageInput;

    private JTextField inputField;
    private DefaultListModel<String> chatModel;
    private JList<String> chatList;
    private List<ChatBot.Intent> intents;
    private final String knowledgeBaseFilePath = "src/main/java/noyl/json/questions.json";
    private LoginModel model ;
    public ChatBotView(){



        setTitle("NOYL ChatBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(360, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        someLogic();


    }

    public void someLogic() {

        chatModel = new DefaultListModel<>();
        chatList = new JList<>(chatModel);
        JScrollPane scrollPane = new JScrollPane(chatList);

        inputField = new JTextField(20);

        JButton sendButton = new JButton("Send");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane);
        add(BorderLayout.SOUTH, panel);

        sendButton.addActionListener(e -> sendButon());
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButon();
                }
            }
        });

        // Ensure intents are loaded
        intents = loadIntents();

        // Set focus on the input field
        inputField.requestFocusInWindow();

        // Configure JList
        chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chatList.setLayoutOrientation(JList.VERTICAL);
        chatList.setVisibleRowCount(-1); // Allows it to expand vertically

        // Set the cell renderer to align the text properly
        chatList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(((String) value).contains("Chatbot") ? SwingConstants.RIGHT : SwingConstants.LEFT);
                return label;
            }
        });

        // Retrieve chat history from the database
        List<ChatMessage> chatHistory = getChatHistoryFromDatabase();

        // Display chat history in the GUI
        chatModel.clear(); // Clear existing messages

        for (ChatMessage chatMessage : chatHistory) {
            appendMessage(chatMessage.getPhoneNumber(), chatMessage.getMessage(), chatMessage.isChatbot());
        }

        // Set visibility after loading chat history
        setVisible(true);
    }

    private void sendButon() {
        String userName = getUserNameFromDatabase();
        String userInput = inputField.getText();
        String response = getResponse(userInput);

        // Append user message to the GUI
        appendMessage(userName, userInput, false);

        // Store user message in the database
        storeMessageInDatabase(userName, userInput, false);

        // Append chatbot message to the GUI
        appendMessage("Chatbot", response, true);

        // Store chatbot message in the database
        storeMessageInDatabase("Chatbot", response, true);

        inputField.setText("");
    }


    public String getUserNameFromDatabase() {
        try {
            // Establish the database connection
            Connection connection = DatabaseConnection.getConnection();

            // Execute a query to get the user's name from the "user" table
            // Assuming there is a column named "name" in the "user" table
            // Replace the query and column names based on your actual database schema
            // Use PreparedStatement to prevent SQL injection
            String query = "SELECT name FROM noyl_chat.user WHERE PhoneNumber = ?"; // Replace with your actual query
            // Assume you have a user ID stored in a variable, replace with your actual variable

            try (var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, LoginModel.getPhoneNumber());
                var resultSet = preparedStatement.executeQuery();

                // Check if the result set has any rows
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    // Handle the case where the user is not found
                    return "Unknown User";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Unknown User";
        }
    }

    private List<ChatBot.Intent> loadIntents() {
        try (Reader reader = new FileReader(knowledgeBaseFilePath)) {
            java.lang.reflect.Type type = new TypeToken<Map<String, List<ChatBot.Intent>>>() {}.getType();
            Gson gson = new Gson();
            Map<String, List<ChatBot.Intent>> data = gson.fromJson(reader, type);
            return data != null ? data.get("intents") : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendMessage(String userName, String message, boolean isChatbot) {
        String alignmentStyle = isChatbot ? "right" : "left";
        String bgColor = isChatbot ? "#2E2E2E;" : "#3F3F3F;";
        String textColor = isChatbot ? "#FFFFFF;" : "#FFFFFF;";
        String borderRadius = isChatbot ? "10px 0 10px 10px" : "0 10px 10px 10px"; // Adjust the radius as needed

        // Set a maximum line length to limit the width
        int maxLineLength = 40; // Adjust the length as needed
        String formattedMessage = formatMessage((isChatbot ? "Chatbot" : userName) + ": " + message, maxLineLength);

        formattedMessage = "<html><div style='"
                + "background-color: " + bgColor + "; "
                + "border-radius: " + borderRadius + "; "
                + "padding: 5px; "
                + "text-align: " + alignmentStyle + ";'>"
                + "<span style='color: " + textColor + "'>"
                + formattedMessage
                + "</span></div></html>";

        chatModel.addElement(formattedMessage);
        chatList.ensureIndexIsVisible(chatModel.size() - 1);
    }

    private String formatMessage(String message, int maxLineLength) {
        StringBuilder formattedMessage = new StringBuilder("<html>");
        String[] words = message.split("\\s+");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() <= maxLineLength) {
                // Add the word to the current line
                formattedMessage.append(word).append(" ");
                currentLineLength += word.length() + 1; // Include space
            } else {
                // Start a new line with the current word
                formattedMessage.append("<br>").append(word).append(" ");
                currentLineLength = word.length() + 1; // Include space
            }
        }

        formattedMessage.append("</html>");
        return formattedMessage.toString();
    }



    private String getResponse(String userInput) {
        if (intents == null) {
            // Handle the case where intents are not loaded properly
            return "I'm sorry, I'm currently unable to respond. Please try again later.";
        }

        for (ChatBot.Intent intent : intents) {
            for (String pattern : intent.getPatterns()) {
                if (userInput.toLowerCase().contains(pattern.toLowerCase())) {
                    return getRandomResponse(intent.getResponses());
                }
            }
        }

        return getRandomResponse(getDefaultResponses());
    }

    private String getRandomResponse(List<String> responses) {
        if (responses != null && !responses.isEmpty()) {
            int randomIndex = new Random().nextInt(responses.size());
            return responses.get(randomIndex);
        } else {
            return "I'm sorry, I didn't understand that.\nTeach me how to respond to that, will you?";
        }
    }

    private List<String> getDefaultResponses() {
        return List.of("I'm sorry, I didn't understand that.", "Could you please rephrase that?", "I'm still learning!");
    }

    private void learnAndStore(String userInput, String response) {
        // Check if the input already exists in intents
        boolean isNewInput = true;
        for (ChatBot.Intent intent : intents) {
            for (String pattern : intent.getPatterns()) {
                if (userInput.toLowerCase().contains(pattern.toLowerCase())) {
                    // Input already exists, update the responses
                    intent.getResponses().add(response);
                    isNewInput = false;
                    break;
                }
            }
        }

        // If it's a new input, create a new intent and add it to intents
        if (isNewInput) {
            ChatBot.Intent newIntent = new ChatBot.Intent();
            newIntent.getPatterns().add(userInput);
            newIntent.getResponses().add(response);
            intents.add(newIntent);
        }

        // Save the updated intents back to the JSON file
        saveIntentsToJson();
    }

    private void saveIntentsToJson() {
        try {
            Gson gson = new Gson();
            Map<String, List<ChatBot.Intent>> data = Map.of("intents", intents);
            String jsonData = gson.toJson(data);

            Files.write(Paths.get(knowledgeBaseFilePath), jsonData.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void storeMessageInDatabase(String userName, String message, boolean isChatbot) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO bot_history (phoneNumber, message, is_chatbot) VALUES (?, ?, ?)";

            try (var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, LoginModel.getPhoneNumber());
                preparedStatement.setString(2, message);
                preparedStatement.setBoolean(3, isChatbot);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<ChatMessage> getChatHistoryFromDatabase() {
        List<ChatMessage> chatHistory = new ArrayList<>();

        try {
            Connection connection = DatabaseConnection.getConnection();
            String query = "SELECT phoneNumber, message, is_chatbot FROM bot_history WHERE phoneNumber = ? ORDER BY timestamp";

            try (var preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, LoginModel.getPhoneNumber());
                var resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String message = resultSet.getString("message");
                    boolean isChatbot = resultSet.getBoolean("is_chatbot");

                    chatHistory.add(new ChatMessage(phoneNumber, message, isChatbot));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistory;
    }

    static class Intent {
        private String tag;
        private List<String> patterns;
        private List<String> responses;

        public String getTag() {
            return tag;
        }

        public List<String> getPatterns() {
            return patterns;
        }

        public List<String> getResponses() {
            return responses;
        }
    }
    public class ChatMessage {
        private String phoneNumber; // Change this to match your actual variable name
        private String message;
        private boolean isChatbot;

        public ChatMessage(String phoneNumber, String message, boolean isChatbot) {
            this.phoneNumber = phoneNumber;
            this.message = message;
            this.isChatbot = isChatbot;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getMessage() {
            return message;
        }

        public boolean isChatbot() {
            return isChatbot;
        }
    }


    public static void main(String[] args) throws IOException {
        // Apply FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new ChatBotView();


    }
}