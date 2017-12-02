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
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration
@WebAppConfiguration
class SignUpControllerTest {

  lateinit var mockMvc: MockMvc

  @Mock
  private lateinit var personRepo: PersonRepository

  @Before
  fun setup() {
    mockMvc = MockMvcBuilders
        .standaloneSetup(SignUpController(personRepo))
        .build()

    `when`(personRepo.save(ArgumentMatchers.any(Person::class.java))).then { invocation -> invocation.getArgument(0) }
  }

  @Test
  fun testRegistrationPost() {
    val requestBody = "firstName=Test&lastName=User&email=ema&password=1234"

    mockMvc.perform(post("/signUp")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .content(requestBody))
        .andExpect { result ->
          result.response.redirectedUrl.equals("/login")
          verify(personRepo, times(1)).save(ArgumentMatchers.any(Person::class.java))
        }
  }
}
