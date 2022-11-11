package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DtoForView;
import ru.kata.spring.boot_security.demo.util.DataValidator;
import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DataValidator dataValidator;

    @Autowired
    public AdminController(UserService userService, DataValidator dataValidator) {
        this.userService = userService;
        this.dataValidator = dataValidator;
    }

    @GetMapping()
    public String showAllUsers(Model model){
        model.addAttribute("users", userService.getDtoUsers());
        model.addAttribute("dto", DtoForView.getEmptyDTO());
             return "admin";
    }


    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("dto")  @Valid DtoForView dto, BindingResult bindingResult) {
        dataValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors())
            return "admin";
        userService.update(dto.getUser(), dto.getRoles());
        return "redirect:/admin";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("dto") @Valid DtoForView dto, BindingResult bindingResult) {
        dataValidator.validate(dto,bindingResult);
        if (bindingResult.hasErrors())
            return "admin";
        userService.save(dto.getUser(),dto.getRoles());
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
