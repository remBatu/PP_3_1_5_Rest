package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DtoForView;
import ru.kata.spring.boot_security.demo.util.DataValidator;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RegistrationService registrationService;

    private final DataValidator dataValidator;

    @Autowired
    public AdminController(UserService userService, RegistrationService registrationService, DataValidator dataValidator) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.dataValidator = dataValidator;
    }

    @GetMapping
    public String adminPage() {
        return "admin/admin";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/users/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("dto", DtoForView.getDTO(userService.getUser(id)));
        return "admin/user-info";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("dto") DtoForView dto) {
        registrationService.update(dto.getUser(),dto.getRoles());
        return "redirect:/admin/users";
    }

    @GetMapping("users/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("dto", DtoForView.getEmptyDTO());
        return "admin/new";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("dto") @Valid DtoForView dto, BindingResult bindingResult) {
        dataValidator.validate(dto,bindingResult);
        if (bindingResult.hasErrors())
            return "admin/new";
        registrationService.register(dto.getUser(),dto.getRoles());
        return "redirect:/admin/users";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
