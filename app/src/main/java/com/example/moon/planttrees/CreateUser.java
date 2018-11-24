package com.example.moon.planttrees;

class CreateUser {
    private String email;
    private String pass;
    private String user;
    private String date;
    private int total_tree;
    private String key;


    public CreateUser() {
    }

    public CreateUser(String email, String pass, String user, String date, int total_tree, String key) {
        this.email = email;
        this.pass = pass;
        this.user = user;
        this.date = date;
        this.total_tree = total_tree;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_tree() {
        return total_tree;
    }

    public void setTotal_tree(int total_tree) {
        this.total_tree = total_tree;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
