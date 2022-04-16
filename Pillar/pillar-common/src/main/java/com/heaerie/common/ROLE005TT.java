package com.heaerie.common;

import javax.persistence.Id;

public class ROLE005TT {
    @Id
    String role;
    Number roleValue;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Number getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(Number roleValue) {
        this.roleValue = roleValue;
    }

    @Override
    public String toString() {
        return "ROLEOO5TT{" +
                "role='" + role + '\'' +
                ", roleValue=" + roleValue +
                '}';
    }
}
