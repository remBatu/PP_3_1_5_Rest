package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.util.RoleType;

public interface RoleService {
    Role getUserRole(RoleType roleType);
}
