package minetime.controller

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import javax.validation.Valid
import javax.validation.Validator

@Controller
class SignUpController(val personRepo: PersonRepository, val passwordEncoder: PasswordEncoder, val validator: Validator) {

  @GetMapping("/signUp")
  fun registrationGet(): String = "signUp"

  @PostMapping("/signUp")
  fun registrationPost(@Valid person: Person, bindingResult: BindingResult, model: Model): String {
    if(bindingResult.hasErrors()) {
      return "signUp"
    }
    println(person.password)
    println(person.lastName)
    println(person.id)
    println(person.firstName)
    println(person.email)

    val violations = validator.validate(person)
    if(violations.isEmpty()) {
      person.password = passwordEncoder.encode(person.password)
      personRepo.save(person)
      return "redirect:/login"
    }

    model.addAttribute("errors", violations)
    return "signUp"
  }
}