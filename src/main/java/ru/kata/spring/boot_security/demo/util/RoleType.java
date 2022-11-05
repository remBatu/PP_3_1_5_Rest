package ru.kata.spring.boot_security.demo.util;

public enum RoleType {
    ROLE_USER ("User"),
     ROLE_ADMIN ("Admin");
    private final String viewName;

    RoleType(String viewName) {
        this.viewName = viewName;
    }


    public String getViewName() {
        return viewName;
    }

}
