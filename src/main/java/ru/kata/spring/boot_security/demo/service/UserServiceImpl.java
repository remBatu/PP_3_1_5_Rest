package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.util.UserDto;
import ru.kata.spring.boot_security.demo.util.RoleType;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleServiceImpl roleService;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void save(User user, List<RoleType> roleTypes) {
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
