package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DataValidator implements Validator {

    private final UserDetailsService userDetailsService;

    @Autowired
    public DataValidator(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DtoForView.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DtoForView dto=(DtoForView) target;

       try {
           userDetailsService.loadUserByUsername(dto.getUser().getName());
       } catch (UsernameNotFoundException ignore){
           return;
       }
       errors.rejectValue("user.name","","Пользователь с таким именем уже существует!");
    }
}
