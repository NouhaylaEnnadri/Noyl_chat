package noyl.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import noyl.controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Login extends JFrame {

    private JTextField PhoneNumber ;
    private JTextField textPassword ;
    private JCheckBox chRememberMe;
    private JButton cmdLogin;
    private JButton cmdRegister;


    private Component createSignupLabel(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        panel.putClientProperty(FlatClientProperties.STYLE,""+
                "background:null");
         cmdRegister = new JButton("<html><a href = \"#\">Sign up</html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE,""+
                "border:3,3,3,3"
                );
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel label = new JLabel("don't have an account ?");

        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    public  Login(){


        FrameInit();
        setTitle("NOYL Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(400,500));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void FrameInit(){
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        PhoneNumber    = new JTextField() ;
        textPassword = new JPasswordField();
        chRememberMe = new JCheckBox("Remember me");
        cmdLogin = new JButton("Login");
        cmdLogin.addActionListener(e -> {
            this.setVisible(false);
            ChatBotView chat = new ChatBotView();
        });

                JPanel loginPanel = new JPanel(new MigLayout("wrap,fillx,insets 35 45 30 45", "fill,250:280"));
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;"+
                "[light]background:darken(@background,3%);"+
                "[dark]background:lighten(@background,3%);"
        );

        textPassword.putClientProperty(FlatClientProperties.STYLE, "" +

                "showRevealButton:true"
                );
        PhoneNumber.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter your username or email");
        textPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT,"Enter your password");
        JLabel lbTitle = new JLabel("Welcome back!");
        JLabel description = new JLabel("please sign in to acess your account");
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        lbTitle.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +10");

        description.putClientProperty(FlatClientProperties.STYLE,"" +
                        "[light]background:lighten(@foreground,30%);"+
                        "[dark]background:darken(@foreground,30%);"
                );

        loginPanel.add(lbTitle);
        loginPanel.add(description);
        loginPanel.add(new JLabel("phone number"),"gapy 8");
        loginPanel.add(PhoneNumber);
        loginPanel.add(new JLabel("password"),"gapy 8");
        loginPanel.add(textPassword);
        loginPanel.add(chRememberMe, "grow 0");
        loginPanel.add(cmdLogin,"gapy 10");
        loginPanel.add(createSignupLabel(),"gapy 10");

        add(loginPanel);


    }

    //getters and setters

    public JTextField getPhoneNumber() {
        return PhoneNumber;
    }

    public void setTextUsername(JTextField textUsername) {
        this.PhoneNumber = textUsername;
    }

    public JTextField getPassword() {
        return textPassword;
    }

    public void setTextPassword(JPasswordField textPassword) {
        this.textPassword = textPassword;
    }

    public JCheckBox getChRememberMe() {
        return chRememberMe;
    }

    public void setChRememberMe(JCheckBox chRememberMe) {
        this.chRememberMe = chRememberMe;
    }

    public JButton getCmdLogin() {
        return cmdLogin;
    }

    public void setCmdLogin(JButton cmdLogin) {
        this.cmdLogin = cmdLogin;
    }

    public void setPhoneNumber(JTextField phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public JTextField getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(JTextField textPassword) {
        this.textPassword = textPassword;
    }

    public JButton getCmdRegister() {
        return cmdRegister;
    }

    public void setCmdRegister(JButton cmdRegister) {
        this.cmdRegister = cmdRegister;
    }


}
