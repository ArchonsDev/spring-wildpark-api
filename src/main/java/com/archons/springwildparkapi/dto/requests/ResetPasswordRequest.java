package com.archons.springwildparkapi.dto.requests;

public class ResetPasswordRequest {
    private String email;

    public ResetPasswordRequest(String email) {
        this.email = email;
    }

    public ResetPasswordRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
