package ru.kata.spring.boot_security.demo.util;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DtoForView {

    private User user;
    private ArrayList<RoleType> roles;



    public DtoForView() {
    }

    private DtoForView(User user, ArrayList<RoleType> roles) {
        this.user = user;
        this.roles = roles;

    }

    public static DtoForView getDTO(User user) {
        ArrayList<RoleType> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(RoleType::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
        return new DtoForView(user, roles);
    }
    public static DtoForView getEmptyDTO() {
        return new DtoForView(new User(), new ArrayList<>());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<RoleType> roles) {
        this.roles = roles;
    }

}
