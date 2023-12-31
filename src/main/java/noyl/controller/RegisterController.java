package noyl.controller;

import noyl.DAO.LoginDao;
import noyl.DAO.RegisterDao;
import noyl.model.LoginModel;
import noyl.model.RegisterModel;
import noyl.view.Login;
import noyl.view.Register;

import javax.swing.*;
import java.sql.SQLException;

public class RegisterController {

    private RegisterDao dao;
    private RegisterModel model;
    private Register view ;


    public RegisterController(Register view){

        this.view = view;
        this.model = new RegisterModel() ;

        view.getCmdRegister().addActionListener(e -> RegisterListener());

        view.getJustLogin().addActionListener(e ->switchToLoginFrame());

    }
    public void switchToLoginFrame() {
        view.setVisible(false);
        LoginController loginController = new LoginController(new Login());
    }

    public void RegisterListener(){
        System.out.println(" hello");
        try {
            // Update model with user input
            model.setName(view.getTextUsername().getText());
            model.setPassword(view.getTextPassword().getText());
            model.setvPassword(view.getVerifyTextPassword().getText());
            model.setPhoneNumber(Long.parseLong(view.getPhoneNumber().getText()));
            model.SetFile(view.getSelectedFile());

            // Call the DAO to insert the data into the database
            if (RegisterDao.register(model.getPhoneNumber(), model.getName(), model.getPassword(), model.getvPassword(), model.getFile())) {
                System.out.println(" Registration successful");
                switchToLoginFrame();
            } else {
                JOptionPane.showMessageDialog(null, "I guess you are already a part of us. Please go to the login page.", "Warning", JOptionPane.WARNING_MESSAGE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
