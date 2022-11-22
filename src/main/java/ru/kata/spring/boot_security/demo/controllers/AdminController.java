package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Exceptions.PersonErrorResponse;
import ru.kata.spring.boot_security.demo.Exceptions.UserNotValidException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserDto;
import ru.kata.spring.boot_security.demo.util.DataValidator;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final DataValidator dataValidator;

    @Autowired
    public AdminController(UserService userService, DataValidator dataValidator) {
        this.userService = userService;
        this.dataValidator = dataValidator;
    }

    @GetMapping("/users")
    public List<UserDto> showAllUsers(){
        return UserDto.getDtoUsers(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public UserDto getUser( @PathVariable int id){
        return UserDto.getDTO(userService.getUser(id));
    }

    @GetMapping("/user")
    public UserDto getCurrentUser(){
        User user = ((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()).user();
        return UserDto.getDTO(user);
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid UserDto dto, BindingResult bindingResult) {
        System.out.println(dto);
        dataValidator.validate(dto,bindingResult);
        bindingResultHandler(bindingResult);
        userService.save(dto.getUser(),dto.getRoles());
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PatchMapping("/users")
    public ResponseEntity<HttpStatus>  update(@RequestBody @Valid UserDto dto, BindingResult bindingResult) {
        dataValidator.validate(dto, bindingResult);
        bindingResultHandler(bindingResult);
        userService.update(dto.getUser(), dto.getRoles());
        return ResponseEntity.ok(HttpStatus.OK);
    }





    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus>  delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(UserNotValidException e){
        return new ResponseEntity<>(PersonErrorResponse.getErrorResponse(e.getMessage())
                ,HttpStatus.BAD_REQUEST);

    }


    public void bindingResultHandler(BindingResult bindingResult) throws UserNotValidException{
        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> errorMessage
                    .append(fieldError.getDefaultMessage()));
            throw new UserNotValidException(errorMessage.toString());
        }

    }
}
