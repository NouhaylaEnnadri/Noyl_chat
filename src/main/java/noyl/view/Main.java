package noyl.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main extends JFrame {
    private JTextField inputField;
    private JTextArea chatArea;
    private List<Intent> intents;
    private final String knowledgeBaseFilePath = "questions.json";

    public Main() {
        setTitle("Chatbot");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        intents = (List<Intent>) loadIntents();

        JPanel panel = new JPanel();
        inputField = new JTextField(20);
        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                String response = getResponse(userInput);
                chatArea.append("User: " + userInput + "\n");
                chatArea.append("Chatbot: " + response + "\n");
                inputField.setText("");
            }
        });

        panel.add(inputField);
        panel.add(sendButton);

        add(scrollPane);
        add(BorderLayout.SOUTH, panel);

        setVisible(true);
    }

    private List<Intent> loadIntents() {
        try (Reader reader = new FileReader(knowledgeBaseFilePath)) {
            // Change the type to match the JSON structure
            java.lang.reflect.Type type = new TypeToken<Map<String, List<Intent>>>() {}.getType();
            Gson gson = new Gson();
            Map<String, List<Intent>> data = gson.fromJson(reader, type);
            // Retrieve the intents from the map
            return data != null ? data.get("intents") : null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private String getResponse(String userInput) {
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
            return "I'm sorry, I didn't understand that.";
        }
    }

    private List<String> getDefaultResponses() {
        // Provide default responses when no intent matches
        return List.of("I'm sorry, I didn't understand that.", "Could you please rephrase that?", "I'm still learning!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    // Define a class to represent an intent
    static class Intent {
        private String tag;
        private List<String> patterns;
        private List<String> responses;
        // You can add more fields if needed

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
