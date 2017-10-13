package minetime.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class DefaultController {

    @GetMapping("/")
    fun hello(): String {
        return "index"
    }
}