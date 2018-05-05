package minetime.controller

import minetime.model.Category
import minetime.model.Person
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
  fun navigate(@PathVariable projectId: UUID, principal: Principal) =
    doOrStandardError(projectId, principal.name, {
      val values = mapOf(Pair("project", it.first), Pair("person", it.second))
      ModelAndView("loggedIn/project", values)
    })

  @GetMapping("/{projectId}/logtime")
  fun logtime(@PathVariable projectId: UUID, principal: Principal) =
    doOrStandardError(projectId, principal.name, {
      val values = mapOf(Pair("project", it.first), Pair("person", it.second))
      ModelAndView("loggedIn/project", values)
    })

  @GetMapping("/{projectId}/categories/create")
  fun getCategories(@PathVariable projectId: UUID, principal: Principal) =
    doOrStandardError(projectId, principal.name, {
      ModelAndView("loggedIn/createCategories", "project", it.first)
    })

  @PostMapping("/{projectId}/categories/create", consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
  fun createCategories(@PathVariable projectId: UUID, @RequestBody params: MultiValueMap<String, String>, principal: Principal) =
    doOrStandardError(projectId, principal.name, { projectAndPerson ->
      params["categories[]"]?.forEach { projectAndPerson.first.addCategories(Category(it)) }
      projectRepo.save(projectAndPerson.first)
      ModelAndView("redirect:/projects/$projectId")
    })

  fun standardError() = ModelAndView("redirect:/dashboard", FORBIDDEN)

  fun getProjectAndPerson(projectId: UUID, email: String) = projectRepo.findById(projectId) to personRepo.findByEmail(email)

  fun <T> whenPartOf(pair: Pair<Project, Person>, then: (Pair<Project, Person>) -> T, orElse: (Pair<Project, Person>) -> T): T {
    return if(pair.first.isPartOf(pair.second)) {
      then.invoke(pair)
    } else {
      orElse.invoke(pair)
    }
  }

  fun doOrStandardError(projectId: UUID, email: String, f: (Pair<Project, Person>) -> ModelAndView): ModelAndView {
    val projectAndPerson = getProjectAndPerson(projectId, email)
    return whenPartOf(projectAndPerson, f, { standardError() })
  }
}