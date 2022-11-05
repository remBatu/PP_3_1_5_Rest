package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.RoleType;

import java.util.List;


public interface RegistrationService {
    void register(User user, List<RoleType> roleTypes);
    void update (User user, List<RoleType> roleTypes);
}
