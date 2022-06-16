package com.example.news_sqlite.Auth;

public class UsersDatabase {

    private String  _username, _password;
    private int _uid;

    public UsersDatabase(int uid, String username, String password)
    {
        _uid = uid;
        _username = username;
        _password = password;
    }

    public String getUsername()
    {
        return _username;
    }

    public int getUserId() { return _uid; }

    public String getPassword()
    {
        return _password;
    }

}