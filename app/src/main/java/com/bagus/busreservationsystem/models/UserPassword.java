package com.bagus.busreservationsystem.models;

import com.google.gson.annotations.SerializedName;

public class UserPassword {
    @SerializedName("password")
    private String password;

    public UserPassword() {
    }

    public UserPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
