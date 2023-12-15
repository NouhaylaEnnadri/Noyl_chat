package noyl.controller;

import noyl.DAO.LoginDao;
import noyl.model.LoginModel;
import noyl.view.ChatBotView;
import noyl.view.Login;
import noyl.view.Register;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private LoginModel model;
    private Login view;

    public LoginController(Login view) {
        this.view = view;
        this.model = new LoginModel();
        view.getCmdLogin().addActionListener(e -> LoginListener());
        view.getCmdRegister().addActionListener(e ->switchToChatbot());

    }


    public void switchToChatbot() {
        view.setVisible(false);
        // Create an instance of ChatBotView and make it visible
        RegisterController registerController = new RegisterController(new Register());

    }
    public void LoginListener() {

        try{

            //this is stupid nouhayla find another way later !!!!!!!!!!
            model.setPhoneNumber(view.getPhoneNumber().getText());
            model.setPassword(view.getPassword().getText());

            if(checkUser(model)){

                view.setLoginSuccessful(true);

               System.out.println(" login succefully");
           }
           else{
                view.setLoginSuccessful(false);

                JOptionPane.showMessageDialog(null, "I guess you are not a part of this app yet . Please go to the the register page.", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private boolean checkUser( LoginModel model) throws SQLException {
        return LoginDao.authentication(model.getPhoneNumber(), model.getPassword());
    }
}
