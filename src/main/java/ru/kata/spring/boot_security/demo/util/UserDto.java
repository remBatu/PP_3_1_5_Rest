package ru.kata.spring.boot_security.demo.util;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    @Valid
    private User user;
    private ArrayList<RoleType> roles;



    public UserDto() {
    }

    private UserDto(User user, ArrayList<RoleType> roles) {
        this.user = user;
        this.roles = roles;

    }

    public static UserDto getDTO(User user) {
        ArrayList<RoleType> roles = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(RoleType::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
        return new UserDto(user, roles);
    }
    public static UserDto getEmptyDTO() {
        return new UserDto(new User(), new ArrayList<>());
    }


    public static List<UserDto> getDtoUsers(List<User> list) {
        return list.stream().map(UserDto::getDTO).collect(Collectors.toList());
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
