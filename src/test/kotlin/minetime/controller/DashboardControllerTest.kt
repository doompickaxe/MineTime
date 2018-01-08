package minetime.controller

import minetime.model.Person
import minetime.persistence.PersonRepository
import minetime.persistence.ProjectRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.view.InternalResourceViewResolver
import java.security.Principal

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration
@WebAppConfiguration
class DashboardControllerTest {

  lateinit var mockMvc: MockMvc

  @Mock
  private lateinit var personRepo: PersonRepository

  @Mock
  private lateinit var projectRepo: ProjectRepository

  @Mock
  private lateinit var principal: Principal

  // workaround for Mockito with Kotlin
  private fun <T> any(): T {
    Mockito.any<T>()
    return uninitialized()
  }

  private fun <T> uninitialized(): T = null as T

  @Before
  fun setup() {
    val viewResolver = InternalResourceViewResolver()
    viewResolver.setPrefix("/templates")
    viewResolver.setSuffix(".jade")

    mockMvc = MockMvcBuilders
      .standaloneSetup(DashboardController(personRepo, projectRepo))
      .setViewResolvers(viewResolver)
      .build()

    `when`(projectRepo.findByOwner(any())).thenReturn(listOf())
    `when`(personRepo.findByEmail("test")).thenReturn(Person(firstName = "", lastName = "", email = "", password = ""))
  }

  @Test
  fun getDashboard() {
    `when`(principal.name).thenReturn("test")

    mockMvc.perform(get("/dashboard")
      .principal(principal))
      .andExpect { result -> result.modelAndView!!.viewName === "userTemplates/dashboard" }
      .andExpect { result -> result.modelAndView!!.model.size == 2 }
      .andExpect { result -> result.modelAndView!!.model.containsKey("ownProjects") }
      .andExpect { result -> result.modelAndView!!.model.containsKey("projects") }
  }
}
