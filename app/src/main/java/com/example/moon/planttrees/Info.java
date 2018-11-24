package com.example.moon.planttrees;

public class Info {
    public static String treeTable = "Trees";
    public static String urlName = "locationUrl";
    public static String userName = "Moon";
    public static String Question_table = "questions";
    public static String answer_table = "answer";
    public static String user = "user";
    public static String date = "date";
    public static String Email ;
    public static String Key;
    public static int total_tree_planted;
    public static String clay = "Clay";
    public static String sandy = "Sandy";
    public static String loam = "Loam";
    public static String peat = "Peat";
    public static String silt = "Silt";
    public static String soiltable = "Soil";


    private static String current_user ;


    public static int getTotal_tree_planted() {
        return total_tree_planted;
    }

    public static void setTotal_tree_planted(int total_tree_planted) {
        Info.total_tree_planted = total_tree_planted;
    }

    public static String getEmail() {
        return Email;
    }

    public static void setEmail(String email) {
        Email = email;
    }

    public static String getCurrent_user() {
        return current_user;
    }

    public static void  setCurrent_user(String current_) {
        current_user = current_;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        Info.date = date;
    }

    public static String getKey() {
        return Key;
    }

    public static void setKey(String key) {
        Key = key;
    }
}
