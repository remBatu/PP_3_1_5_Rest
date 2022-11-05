package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.RoleType;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;


    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional
    public void register(User user, List<RoleType> roleTypes) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (roleTypes.isEmpty()) {
            user.addRole(roleService.getUserRole(RoleType.ROLE_USER));
        } else {
            roleTypes.forEach(r -> user.addRole(roleService.getUserRole(r)));
        }
        userRepository.save(user);
    }
    @Transactional
    public void update (User user, List<RoleType> roleTypes) {
        if (roleTypes.isEmpty()) {
            user.addRole(roleService.getUserRole(RoleType.ROLE_USER));
        } else {
            roleTypes.forEach(r -> user.addRole(roleService.getUserRole(r)));
        }
        userRepository.save(user);
    }


}
