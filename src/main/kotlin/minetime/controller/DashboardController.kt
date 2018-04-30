package minetime.controller

import minetime.persistence.PersonRepository
import minetime.persistence.ProjectRepository
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@Controller
@RequestMapping("/dashboard")
class DashboardController(val personRepo: PersonRepository, val projectRepo: ProjectRepository) {

  @GetMapping
  fun loadDashboard(person: Principal): ModelAndView {
    val model = ModelAndView("loggedIn/dashboard")
    val user = personRepo.findByEmail(person.name)
    val projects = projectRepo.findByOwner(user)

    model.addObject("ownProjects", projects)
    model.addObject("projects", user.projects)
    return model
  }
}