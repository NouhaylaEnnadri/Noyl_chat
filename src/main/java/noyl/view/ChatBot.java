package noyl.view;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChatBot extends JFrame {
    private JTextField inputField;
    private DefaultListModel<String> chatModel;
    private JList<String> chatList;
    private List<Intent> intents;
    private final String knowledgeBaseFilePath = "src/main/java/noyl/json/questions.json";

    public ChatBot() {
        setTitle("Chatbot");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatModel = new DefaultListModel<>();
        chatList = new JList<>(chatModel);
        JScrollPane scrollPane = new JScrollPane(chatList);

        inputField = new JTextField(20);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                String response = getResponse(userInput);
                appendMessage("User: " + userInput, false);
                appendMessage("Chatbot: " + response, true);
                inputField.setText("");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane);
        add(BorderLayout.SOUTH, panel);

        // Ensure intents are loaded
        intents = loadIntents();

        setVisible(true);
    }





    private List<Intent> loadIntents() {
        try (Reader reader = new FileReader(knowledgeBaseFilePath)) {
            java.lang.reflect.Type type = new TypeToken<Map<String, List<Intent>>>() {}.getType();
            Gson gson = new Gson();
            Map<String, List<Intent>> data = gson.fromJson(reader, type);
            return data != null ? data.get("intents") : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void appendMessage(String message, boolean isChatbot) {
        String alignmentStyle = isChatbot ? "align=left" : "align=right";
        String formattedMessage = "<html><p style='width: 75%; padding: 5px; margin: 5px; "
                + "background-color: " + (isChatbot ? "#add8e6;" : "#e6e6fa;") + "; "
                + "border-radius: 10px; " + alignmentStyle
                + "'>" + message + "</p></html>";
        chatModel.addElement(formattedMessage);
        chatList.ensureIndexIsVisible(chatModel.size() - 1);
    }

    private String getResponse(String userInput) {
        if (intents == null) {
            // Handle the case where intents are not loaded properly
            return "I'm sorry, I'm currently unable to respond. Please try again later.";
        }

        for (Intent intent : intents) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChatBot();
            }
        });
    }

    static class Intent {
        private String tag;
        List<String> patterns;
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
}
