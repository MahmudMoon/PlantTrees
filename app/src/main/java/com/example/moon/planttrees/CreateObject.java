package com.example.moon.planttrees;

public class CreateObject {
    private String question;
    private String userName;
    private String key;



    public CreateObject() {

    }

    public CreateObject(String question, String userName, String key) {
        this.question = question;
        this.userName = userName;
        this.key = key;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
