package noyl.view;

import com.formdev.flatlaf.FlatDarkLaf;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import noyl.DatabaseConnection.DatabaseConnection;
import noyl.model.LoginModel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ChatBotView extends JFrame {

    private static JTextField inputField;
    private static DefaultListModel<String> chatModel;
    private static JList<String> chatList;
    private static List<Intent> intents;
    private static final String knowledgeBaseFilePath = "src/main/java/noyl/json/questions.json";

    private JPanel leftBarPanel;

    private boolean isExpanded = true;
    public ChatBotView() {
        setTitle("NOYL ChatBot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setResizable(false);

initComponents();
loadChatHistory();
        setVisible(true);
    }


    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel middlePanel = new JPanel(new BorderLayout());

        // profile bar
        JPanel profileBar = new JPanel();
        profileBar.setBackground(Color.decode("#33573d")); // Set the initial background color

        JLabel profileLabel = new JLabel("Noyl_BOT");
        profileBar.add(profileLabel);

        // Bar at the left
        leftBarPanel = new JPanel();
       // leftBarPanel.setBackground(Color.decode("#343541")); // Set the initial background color

        leftBarPanel.setPreferredSize(new Dimension(50, getHeight())); // Set the width to 50 and height to the whole height
        JButton leftChatbotLabel = new JButton(":");
        leftChatbotLabel.setBorderPainted(false);
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

        // middle panel
        middlePanel.add(scrollPane, BorderLayout.CENTER);
        middlePanel.add(bottomPanel, BorderLayout.SOUTH);
        middlePanel.add(profileBar, BorderLayout.NORTH);

        // Entire panel
        mainPanel.add(leftBarPanel, BorderLayout.WEST);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        add(mainPanel);

        // Add action listeners, set focus, load intents, configure JList
        sendButton.addActionListener(e -> sendButon());
        emojiButton.addActionListener(e -> handleEmojiButtonClick());
        inputField.addActionListener(e -> sendButon());

        intents = ChatBotView.loadIntents();
        inputField.requestFocusInWindow();
        ChatBotView.configureChatList();
    }


    private void expand() {
        // Code to handle expansion
        leftBarPanel.setPreferredSize(new Dimension(100, getHeight()));  // Adjust the size as needed
        leftBarPanel.setLayout(new BorderLayout());

        // Add the logout button only if it hasn't been added before
        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(e -> handleLogout());
        leftBarPanel.add(logoutButton, BorderLayout.SOUTH);

        // Update the layout
        leftBarPanel.revalidate();
        leftBarPanel.repaint();
    }

    void sendButon() {
        String userName = getUserNameFromDatabase();
        String userInput = inputField.getText();

        // Check if the user is trying to teach the chatbot
        if (userInput.toLowerCase().startsWith("teach me:")) {
            String responseToTeach = userInput.substring("teach me:".length()).trim();
            learnAndStore(userInput, responseToTeach);

            // Provide feedback to the user
            appendMessage("Chatbot", "Thanks for teaching me! I'll remember that.", true);
            storeMessageInDatabase("Chatbot", "Thanks for teaching me! I'll remember that.", true);
        } else {
            String response = getResponse(userInput);

            // If the response is a default learning message, proceed to learning
            if (response.equals(getDefaultLearningResponse())) {
                learnFromUserInput(userInput);
                appendMessage("Chatbot", "Thanks for teaching me! I'll remember that.", true);
                storeMessageInDatabase("Chatbot", "Thanks for teaching me! I'll remember that.", true);
            } else {
                // Append user message to the GUI
                appendMessage(userName, userInput, false);

                // Store user message in the database
                storeMessageInDatabase(userName, userInput, false);

                // Append chatbot message to the GUI
                appendMessage("Chatbot", response, true);

                // Store chatbot message in the database
                storeMessageInDatabase("Chatbot", response, true);
            }
        }

        inputField.setText("");
    }

    private void handleLogout() {
    }

    private void collapse() {
        // Code to handle collapse
        leftBarPanel.setPreferredSize(new Dimension(50, getHeight()));  // Adjust the size as needed

        // Remove the logout button if it exists
        Component[] components = leftBarPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Log out")) {
                leftBarPanel.remove(component);
                break;
            }
        }

        // Update the layout
        leftBarPanel.revalidate();
        leftBarPanel.repaint();
    }


    private void handleEmojiButtonClick() {
    }

    static void configureChatList() {
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
    }



    void learnFromUserInput(String userInput) {
        // Ask the user for the correct response and update the JSON file
        String response = JOptionPane.showInputDialog(
                this,
                "I'm not sure how to respond. Can you teach me the correct answer?",
                "Teach Me",
                JOptionPane.QUESTION_MESSAGE
        );
        // Validate the user-provided response
        if (response != null && !response.trim().isEmpty()) {
            // Learn and store the new intent in the JSON file
            learnAndStore(userInput, response);
        } else {
            // Provide feedback if the user input is invalid
            appendMessage("Chatbot", "Hmm, it seems the input is not valid. I'll keep learning!", true);
            storeMessageInDatabase("Chatbot", "Hmm, it seems the input is not valid. I'll keep learning!", true);
        }
    }

    static String getDefaultLearningResponse() {
        return "I'm sorry, I didn't understand that.\nTeach me how to respond to that, will you?";
    }


    static void learnAndStore(String userInput, String response) {
        // Check if the input already exists in intents
        boolean isNewInput = true;
        for (Intent intent : intents) {
            List<String> patterns = intent.getPatterns();

            if (patterns == null) {
                patterns = new ArrayList<>();  // Initialize patterns if null
                intent.patterns = patterns;
            }

            for (String pattern : patterns) {
                if (userInput.toLowerCase().contains(pattern.toLowerCase())) {
                    // Input already exists, update the responses
                    intent.getPatterns().add(response);
                    isNewInput = false;
                    break;
                }
            }
        }

        // If it's a new input, create a new intent and add it to intents
        if (isNewInput) {
            Intent newIntent = new Intent();
            newIntent.getPatterns().add(userInput);
            newIntent.getResponses().add(response);
            intents.add(newIntent);
        }

        // Save the updated intents back to the JSON file
        saveIntentsToJson();
    }

    private void loadChatHistory() {
        // Retrieve chat history from the database
        List<ChatMessage> chatHistory = getChatHistoryFromDatabase();

        // Display chat history in the GUI
        chatModel.clear(); // Clear existing messages

        for (ChatMessage chatMessage : chatHistory) {
            appendMessage(chatMessage.getPhoneNumber(), chatMessage.getMessage(), chatMessage.isChatbot());
        }
    }

    static String getUserNameFromDatabase() {
        try {
            // Establish the database connection
            Connection connection = DatabaseConnection.getConnection();

            // Execute a query to get the user's name from the "user" table
            String query = "SELECT name FROM noyl_chat.user WHERE PhoneNumber = ?";
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

    static List<Intent> loadIntents() {
        try (Reader reader = new FileReader(knowledgeBaseFilePath)) {
            java.lang.reflect.Type type = new TypeToken<Map<String, List<Intent>>>() {}.getType();
            Gson gson = new Gson();
            Map<String, List<Intent>> data = gson.fromJson(reader, type);
            return data != null ? data.get("intents") : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    static void appendMessage(String userName, String message, boolean isChatbot) {
        String alignmentStyle = isChatbot ? "right" : "left";
        String bgColor = isChatbot ? "#2E2E2E;" : "#3F3F3F;";
        String textColor = isChatbot ? "#FFFFFF;" : "#FFFFFF;";
        String borderRadius = isChatbot ? "10px 0 10px 10px" : "0 10px 10px 10px";

        // Set a maximum line length to limit the width
        int maxLineLength = 40;
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

    private static String formatMessage(String message, int maxLineLength) {
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

    static String getResponse(String userInput) {
        if (intents == null) {
            // Handle the case where intents are not loaded properly
            return "I'm sorry, I'm currently unable to respond. Please try again later.";
        }

        String lowerCaseInput = userInput.toLowerCase();

        for (Intent intent : intents) {
            for (String pattern : intent.getPatterns()) {
                // Check if a certain percentage of words from the pattern are present in the input
                if (isPartialMatch(lowerCaseInput, pattern.toLowerCase())) {
                    return getRandomResponse(intent.getResponses());
                }
            }
        }

        return getRandomResponse(Collections.singletonList(getDefaultLearningResponse()));
    }

    private static boolean isPartialMatch(String userInput, String pattern) {
        // Set a threshold for partial matching, adjust as needed (e.g., 0.8 for 80% match)
        double threshold = 0.8;

        // Split the pattern and user input into words
        String[] patternWords = pattern.split("\\s+");
        String[] inputWords = userInput.split("\\s+");

        // Count the number of words from the pattern present in the user input
        long matchingWordCount = Arrays.stream(patternWords)
                .filter(patternWord -> Arrays.stream(inputWords).anyMatch(inputWord -> inputWord.contains(patternWord)))
                .count();

        // Calculate the match percentage
        double matchPercentage = (double) matchingWordCount / patternWords.length;

        // Return true if the match percentage is above the threshold
        return matchPercentage >= threshold;
    }


    private static String getRandomResponse(List<String> responses) {
        if (responses != null && !responses.isEmpty()) {
            int randomIndex = new Random().nextInt(responses.size());
            return responses.get(randomIndex);
        } else {
            return "I'm sorry, I didn't understand that.\nTeach me how to respond to that, will you?";
        }
    }


    private static void saveIntentsToJson() {
        try (Writer writer = new FileWriter(knowledgeBaseFilePath)) {
            // Convert List<Intent> to JSON with pretty printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray intentsJsonArray = new JsonArray();
            for (Intent intent : intents) {
                JsonObject intentJsonObject = new JsonObject();
                intentJsonObject.addProperty("tag", intent.getTag());
                intentJsonObject.add("patterns", toJsonArray(intent.getPatterns()));
                intentJsonObject.add("responses", toJsonArray(intent.getResponses()));
                intentsJsonArray.add(intentJsonObject);
            }

            JsonObject dataJsonObject = new JsonObject();
            dataJsonObject.add("intents", intentsJsonArray);

            // Write JSON to file with pretty printing
            gson.toJson(dataJsonObject, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert List<String> to JsonArray
    private static JsonArray toJsonArray(List<String> list) {
        JsonArray jsonArray = new JsonArray();
        for (String item : list) {
            jsonArray.add(item);
        }
        return jsonArray;
    }

    static void storeMessageInDatabase(String userName, String message, boolean isChatbot) {
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

    public static void main(String[] args) {
        // Apply FlatLaf Dark theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new ChatBotView();
    }

    static class Intent {
        private String tag;
        private List<String> patterns = new ArrayList<>(); // Initialize patterns
        private List<String> responses = new ArrayList<>(); // Initialize responses

        // Add constructor if necessary

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



    public static class ChatMessage {
        private String phoneNumber;
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
}
