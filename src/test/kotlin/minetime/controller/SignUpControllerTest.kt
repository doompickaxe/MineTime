package minetime.controller

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.view.InternalResourceViewResolver

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration
@WebAppConfiguration
class SignUpControllerTest {

  lateinit var mockMvc: MockMvc

  @Mock
  private lateinit var personRepo: PersonRepository

  @Before
  fun setup() {
    val viewResolver = InternalResourceViewResolver()
    viewResolver.setPrefix("/templates")
    viewResolver.setSuffix(".jade")

    mockMvc = MockMvcBuilders
      .standaloneSetup(SignUpController(personRepo, BCryptPasswordEncoder()))
      .setViewResolvers(viewResolver)
      .build()

    `when`(personRepo.save(any(Person::class.java))).then { invocation -> invocation.getArgument(0) }
  }

  @Test
  fun registrationPostShouldFail() {
    val requestBody = "firstName=Test&lastName=User&email=ema&password=1234"

    mockMvc.perform(post("/signUp")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      .content(requestBody))
      .andExpect { result ->
        result.response.forwardedUrl === "/signUp"
        verify(personRepo, times(0)).save(ArgumentMatchers.any(Person::class.java))
      }
  }

  @Test
  fun registrationPost() {
    val requestBody = "firstName=Test&lastName=User&email=test@email.com&password=1234"

    mockMvc.perform(post("/signUp")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
      .content(requestBody))
      .andExpect { result ->
        result.response.redirectedUrl!! === "/login"
        verify(personRepo, times(1)).save(ArgumentMatchers.any(Person::class.java))
      }
  }
}
