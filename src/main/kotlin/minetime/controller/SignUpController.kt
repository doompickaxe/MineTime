package minetime.controller

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid

@Controller
class SignUpController(val personRepo: PersonRepository, val passwordEncoder: PasswordEncoder) {

  @GetMapping("/signUp")
  fun registrationGet(): String = "signUp"

  @PostMapping("/signUp")
  fun registrationPost(@Valid @ModelAttribute person: Person, result: BindingResult, model: Model): String {
    if(result.hasErrors()) {
      model.addAttribute("errors", result.allErrors)
      return "signUp"
    }

    person.password = passwordEncoder.encode(person.password)
    personRepo.save(person)
    return "redirect:/login"
  }
}