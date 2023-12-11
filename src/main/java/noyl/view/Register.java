package noyl.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import noyl.view.component.pwdStatus;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Register extends JFrame {

    private static File selectedFile;
    private JTextField textUsername;
    private JTextField phoneNumber;
    private JTextField email;
    private JTextField textPassword;
    private JTextField verifyTextPassword;
    private JCheckBox terms_conditions;
    private JButton cmdRegister;
    private JButton cmdUploadPhoto;
    private static JLabel lblImagePreview;
    private JButton justLogin;
    private pwdStatus passwordStrengthStatus;

    public Register() {
        initializeFrame();
        setTitle("NOYL SignUp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(400, 650));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeFrame() {
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));

        phoneNumber = new JTextField();
        textUsername = new JTextField();
        textPassword = new JPasswordField();
        verifyTextPassword = new JPasswordField();
        cmdUploadPhoto = new JButton("Upload Photo");
        terms_conditions = new JCheckBox("I accept terms & conditions ");
        cmdRegister = new JButton("Register");
        lblImagePreview = new JLabel();
        passwordStrengthStatus = new pwdStatus();

        lblImagePreview.setPreferredSize(new Dimension(200, 200));

        JPanel loginPanel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "[light]background:darken(@background,3%);" +
                "[dark]background:lighten(@background,3%);"
        );

        textPassword.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true"
        );

        textUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your name");
        textPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");
        verifyTextPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Verify your password");

        JLabel lbTitle = new JLabel("Welcome to NOYL!");
        JLabel description = new JLabel("Please register to create your account");

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:lighten(@foreground,30%);" +
                "[dark]background:darken(@foreground,30%);"
        );
        passwordStrengthStatus.initPasswordField(textPassword);
        loginPanel.add(lbTitle);
        loginPanel.add(description);
        loginPanel.add(new JLabel("Phone number"), "gapy 8");
        loginPanel.add(phoneNumber);
        loginPanel.add(new JLabel("Your name"), "gapy 8");
        loginPanel.add(textUsername);
        loginPanel.add(new JLabel("Password"), "gapy 8");
        loginPanel.add(textPassword);
        loginPanel.add(passwordStrengthStatus,"gapy 0");
        loginPanel.add(new JLabel("Confirm password"), "gapy 0");
        loginPanel.add(verifyTextPassword);
        loginPanel.add(cmdUploadPhoto, "gapy 5");
        loginPanel.add(terms_conditions, "grow 0");
        loginPanel.add(lblImagePreview, "gapy 5");

        loginPanel.add(cmdRegister, "gapy 10");
        loginPanel.add(createSignupLabel(), "gapy 10");

        cmdUploadPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(Register.this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    displayImage(selectedFile, 50, 50);
                }
            }
        });

        add(loginPanel);
    }

    private Component createSignupLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        justLogin = new JButton("<html><a href=\"#\">Login in</html>");
        justLogin.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3"
        );
        justLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel("Already have an account?");

        panel.add(label);
        panel.add(justLogin);
        return panel;
    }

    static void displayImage(File file, int width, int height) {
        try {
            Image originalImage = ImageIO.read(file);
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            lblImagePreview.setIcon(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters methods


    public static File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public JTextField getTextUsername() {
        return textUsername;
    }

    public void setTextUsername(JTextField textUsername) {
        this.textUsername = textUsername;
    }

    public JTextField getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(JTextField phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public JTextField getEmail() {
        return email;
    }

    public void setEmail(JTextField email) {
        this.email = email;
    }

    public JTextField getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(JTextField textPassword) {
        this.textPassword = textPassword;
    }

    public JTextField getVerifyTextPassword() {
        return verifyTextPassword;
    }

    public void setVerifyTextPassword(JTextField verifyTextPassword) {
        this.verifyTextPassword = verifyTextPassword;
    }

    public JCheckBox getTerms_conditions() {
        return terms_conditions;
    }

    public void setTerms_conditions(JCheckBox terms_conditions) {
        this.terms_conditions = terms_conditions;
    }

    public JButton getCmdRegister() {
        return cmdRegister;
    }

    public void setCmdRegister(JButton cmdRegister) {
        this.cmdRegister = cmdRegister;
    }

    public JButton getCmdUploadPhoto() {
        return cmdUploadPhoto;
    }

    public void setCmdUploadPhoto(JButton cmdUploadPhoto) {
        this.cmdUploadPhoto = cmdUploadPhoto;
    }
    public JLabel getLblImagePreview() {
        return lblImagePreview;
    }

    public void setLblImagePreview(JLabel lblImagePreview) {
        this.lblImagePreview = lblImagePreview;
    }

    public JButton getJustLogin() {
        return justLogin;
    }

    /* ... getters and setters */
    public void setJustLogin(JButton justLogin) {
        this.justLogin = justLogin;
    }
    // end of
}
