package minetime.controller

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class SignUpController(val personRepo: PersonRepository) {

  @GetMapping("/signUp")
  fun registrationGet(): String = "signUp"

  @PostMapping("/signUp")
  fun registrationPost(@ModelAttribute person: Person, bindingResult: BindingResult): ModelAndView {
    return if(bindingResult.hasErrors()) {
      ModelAndView("signUp")
          .addObject("user", person)
    } else {
      personRepo.save(person)
      ModelAndView("redirect:/login")
    }
  }
}