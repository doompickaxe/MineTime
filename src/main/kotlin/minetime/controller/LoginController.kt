package minetime.controller;

import minetime.model.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class LoginController {
    @GetMapping("/login")
    fun login(): ModelAndView {
        return ModelAndView("signIn", "user", User("user", "password"))
    }
}
