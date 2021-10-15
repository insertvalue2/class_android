package com.nomad.mymvvmex2;

import androidx.annotation.Nullable;

public class UserInfoModel {

    @Nullable
    String email, password;

    public UserInfoModel(@Nullable String email, @Nullable String password) {
        this.email = email;
        this.password = password;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }
}
