package ru.kata.spring.boot_security.demo.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
    @JsonProperty("USER")
    @JsonAlias({"ROLE_USER"})
    ROLE_USER ("USER"),
    @JsonProperty("ADMIN")
    @JsonAlias({"ROLE_ADMIN"})
     ROLE_ADMIN ("ADMIN");
    private final String viewName;

    RoleType(String viewName) {
        this.viewName = viewName;
    }



    public String getViewName() {
        return viewName;
    }

}
