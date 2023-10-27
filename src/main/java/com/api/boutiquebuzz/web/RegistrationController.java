package com.api.boutiquebuzz.web;


import com.api.boutiquebuzz.domain.dtos.user.UserRegisterFormDto;
import com.api.boutiquebuzz.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class RegistrationController {

    private static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult.";
    @Autowired
    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "auth-register";
    }

//    @PostMapping("/register")
//    public String postRegister(
//            @Valid UserRegisterFormDto userRegisterForm,
//            BindingResult bindingResult
//            ,            RedirectAttributes redirectAttributes
//    ) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes
//                    .addFlashAttribute("userRegisterForm", userRegisterForm)
//                    .addFlashAttribute(BINDING_RESULT_PATH + "userRegisterForm", bindingResult);
//            return "redirect:/users/register";
//
//
////            return "redirect:/users/register/";
////            System.out.println("Binding result has errors");
//        }
//
//        userService.registerUser(userRegisterForm);
//        return "redirect:/users/login";
////        return "redirect:/users/login/";
//    }

    @PostMapping("/register")
    public ResponseEntity<String> postRegister(
            @Valid @RequestBody UserRegisterFormDto userRegisterForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest().body("Validation errors");
        }

        userService.registerUser(userRegisterForm);

        return ResponseEntity.ok("User registered successfully");
    }

//    @RequestMapping(value = "/register/", method = RequestMethod.OPTIONS)
//    public ResponseEntity<?> handleOptionsRequest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Allow", "GET, POST, PUT, DELETE");
//
//        return new ResponseEntity<>(null, headers, HttpStatus.NO_CONTENT);
//    }


    @ModelAttribute(name = "userRegisterForm")
    public UserRegisterFormDto initUserRegisterFormDto() {
        return new UserRegisterFormDto();
    }

}
