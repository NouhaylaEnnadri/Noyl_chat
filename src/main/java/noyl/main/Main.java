package noyl.main;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import noyl.view.ChatApplication;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("noyl.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN , 13));
        FlatDarkLaf.setup();
        noyl.view.Login _view =  new noyl.view.Login();
        noyl.model.LoginModel  _model = new  noyl.model.LoginModel();

        noyl.controller.LoginController _controller = new noyl.controller.LoginController(_view);

    }

}