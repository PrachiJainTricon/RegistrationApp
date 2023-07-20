package com.example.registerlogin.response;

public class LoginResponse {

    String message;
    Boolean status;

    String authenticate;

    public LoginResponse(String message, Boolean status , String authenticate) {
        this.message = message;
        this.status = status;
        this.authenticate = authenticate;
    }

    public LoginResponse() {
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", authenticate='" + authenticate + '\'' +
                '}';
    }
}
