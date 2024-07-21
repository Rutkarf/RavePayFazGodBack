package org.example.controller.auth;

public class AuthRequestDto {
    private String username;
    private String password;

    //todo ajouter les infos du user

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
