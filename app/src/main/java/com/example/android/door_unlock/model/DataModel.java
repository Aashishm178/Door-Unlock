package com.example.android.door_unlock.model;


public class DataModel {

    private String mail_username="",mail_id="",first_name="",last_name="",password="",machine_code="";

    public DataModel() {
    }

    public DataModel(String mail_username, String mail_id, String first_name, String last_name, String password, String machine_code) {
        this.mail_username = mail_username;
        this.mail_id = mail_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.machine_code = machine_code;
    }

    public String getMail_username() {
        return mail_username;
    }

    public void setMail_username(String mail_username) {
        this.mail_username = mail_username;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }
}
