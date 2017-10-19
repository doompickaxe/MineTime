package minetime.controller

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration
@WebAppConfiguration
class LoginControllerTest {

    lateinit var mvcMock: MockMvc

    @Before
    fun setup() {
        mvcMock = MockMvcBuilders.standaloneSetup(LoginController())
                .build()
    }

    @Test
    fun login() {
        mvcMock.perform(get("/login"))
                .andExpect { result -> result.modelAndView.viewName == "signIn" }
                .andExpect { result -> result.modelAndView.modelMap.containsAttribute("user") }
    }
}