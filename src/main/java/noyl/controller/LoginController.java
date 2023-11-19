package noyl.controller;

import noyl.DAO.LoginDao;
import noyl.model.LoginModel;
import noyl.view.Login;
import noyl.view.Register;

import java.sql.SQLException;

public class LoginController {
    private LoginModel model;
    private Login view;

    public LoginController(Login view) {
        this.view = view;
        this.model = new LoginModel();
        view.getCmdLogin().addActionListener(e -> LoginListener());
        view.getCmdRegister().addActionListener(e ->switchToRegisterFrame());

    }


    public void switchToRegisterFrame() {
        view.setVisible(false);
        RegisterController registerController = new RegisterController(new Register());

    }
    public void LoginListener() {

        try{

            //this is stupid nouhayla find another way later !!!!!!!!!!
            model.setPhoneNumber(view.getPhoneNumber().getText());
            model.setPassword(view.getPassword().getText());

            if(checkUser(model)){

               System.out.println(" login succefully");
           }
           else{

               System.out.println(" who are u bitch");
           }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private boolean checkUser( LoginModel model) throws SQLException {
        return LoginDao.authentication(model.getPhoneNumber(), model.getPassword());
    }
}
