package noyl.model;

public class LoginModel {
    private String phoneNumber;
    private String password;

    public LoginModel(){

    }

    //constructor

    public LoginModel(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    //getter and setter methods

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
