package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;

@Controller
public class UserController {

    @GetMapping("/user")
    public String showCurrentUser(Model model){
       model.addAttribute("user",((UserDetailsImpl) SecurityContextHolder
               .getContext()
               .getAuthentication()
               .getPrincipal())
               .user());
       return "user";
    }
}
