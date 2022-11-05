package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.util.RoleType;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService{
    private final RolesRepository rolesRepository;

    @Autowired
    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public Role getUserRole(RoleType roleType) {
        return rolesRepository.findByRoleName(roleType.name()).orElse(null);
    }






}
