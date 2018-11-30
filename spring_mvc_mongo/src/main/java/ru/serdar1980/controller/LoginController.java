package ru.serdar1980.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username/password");
        }

        if (logout != null) {
            model.addObject("message", "Logged out");
        }
        model.setViewName("login");
        return "login";
    }

    @RequestMapping(value = "perform_login", method = RequestMethod.POST)
    public String loginAction(Model model) {

        return "login";
    }


}