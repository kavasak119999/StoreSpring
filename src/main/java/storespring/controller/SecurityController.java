package storespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import storespring.dto.User;
import storespring.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Properties;

@Controller
public class SecurityController {
    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam(required = false) String error,
                        Model model, Principal principal) {

        if (principal != null) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Логін або пароль не вірні");
        }

        return "pages/login";
    }

    @GetMapping(value = "/registration")
    public String registration(Principal principal, Model model) {

        if (principal != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "pages/registration";
    }

    @PostMapping(value = "/registration-user")
    public String registration(
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            @RequestParam(value = "passwordSecondTime") String passwordSecondTime
    ) {

        if (bindingResult.hasErrors()) {
            return "pages/registration";
        }

        if (!passwordSecondTime.equals(user.getPassword())) {
            bindingResult.addError(new FieldError
                    ("user", "password", "Пароль повинен співпадати у двух рядках"));
        }

        Properties properties = userService.register(user);

        if (!properties.isEmpty()) {
            bindingResult.addError(new FieldError
                    ("user", "username", properties.getProperty("username")));

            return "pages/registration";
        }

        return "redirect:login";
    }

}