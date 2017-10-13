package minetime.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DefaultController {
    @Autowired
    lateinit var restTemplate: RestTemplate

    @Test
    fun getDefaultTemplate() {
        println(restTemplate.getForObject("/", String::class.java))
        //assertThat(restTemplate.getForObject("/"), is())
    }
}
