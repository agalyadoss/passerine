package com.heaerie.common;

public class AuthToken {
    String userId;
    String role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
