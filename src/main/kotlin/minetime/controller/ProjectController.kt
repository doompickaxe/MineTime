package minetime.controller

import minetime.model.Category
import minetime.model.Project
import minetime.persistence.PersonRepository
import minetime.persistence.ProjectRepository
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.security.Principal
import java.util.*

@Controller
@RequestMapping("/projects")
class ProjectController(val personRepo: PersonRepository, val projectRepo: ProjectRepository) {

  @GetMapping("/create")
  fun createProject() = "loggedIn/createProject"

  @PostMapping("/create")
  fun saveProject(@ModelAttribute("projectName") projectName: String, principal: Principal): ModelAndView {
    return if(projectName.trim().isNotEmpty()) {
      val person = personRepo.findByEmail(principal.name)
      val project = projectRepo.save(Project(name = projectName, owner = person))
      ModelAndView("redirect:/projects/${project.id}")
    } else {
      ModelAndView(createProject(), "error", "Please give it a name")
    }
  }

  @GetMapping("/{projectId}")
  fun navigate(@PathVariable projectId: UUID, principal: Principal): ModelAndView {
    val project = projectRepo.findById(projectId)
    val person = personRepo.findByEmail(principal.name)
    return if(project.isPartOf(person)) {
      val values = mapOf(Pair("project", project), Pair("person", person))
      ModelAndView("loggedIn/project", values)
    } else {
      ModelAndView("redirect:/dashboard", FORBIDDEN)
    }
  }

  @GetMapping("/{projectId}/categories/create")
  fun getCategories(@PathVariable projectId: UUID, principal: Principal): ModelAndView {
    val project = projectRepo.findById(projectId)
    val person = personRepo.findByEmail(principal.name)
    return if(project.isPartOf(person)) {
      ModelAndView("loggedIn/createCategories", "project", project)
    } else {
      ModelAndView("redirect:/dashboard", FORBIDDEN)
    }
  }

  @PostMapping("/{projectId}/categories/create", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
  fun createCategories(@PathVariable projectId: UUID, @RequestBody params: MultiValueMap<String, String>, principal: Principal): ModelAndView {
    val project = projectRepo.findById(projectId)
    val person = personRepo.findByEmail(principal.name)
    return if(project.isPartOf(person)) {
      params["categories[]"]?.forEach { project.addCategories(Category(it)) }
      projectRepo.save(project)
      ModelAndView("redirect:/projects/$projectId")
    } else {
      ModelAndView("redirect:/dashboard", FORBIDDEN)
    }
  }
}