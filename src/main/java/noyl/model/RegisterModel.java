package noyl.model;

import java.io.File;

public class RegisterModel {

    private Long PhoneNumber;
    private String name;
    private String l_name;
    private boolean isActive;
    private String password;
    private String vPassword;
    private String picture;
    private File file;

    public RegisterModel(){

    }

    //constructor

    public RegisterModel(Long phoneNumber, String name, String password, String vPassword, String picture) {
        PhoneNumber = phoneNumber;
        this.name = name;
        this.l_name = l_name;
        this.isActive = isActive;
        this.password = password;
        this.vPassword = vPassword;
        this.picture = picture;
    }

    //getters ans setters methods


    public long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getvPassword() {
        return vPassword;
    }

    public void setvPassword(String vPassword) {
        this.vPassword = vPassword;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public File getFile() {
        return file;
    }

    public void SetFile(File file) {
        this.file = file;
    }
}
