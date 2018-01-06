package minetime

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class BootApplication {
  private val log = LoggerFactory.getLogger(BootApplication::class.java)

  @Bean
  fun init(userRepo: PersonRepository) = CommandLineRunner {
    log.info("_______init________")
    val passwordEncoder = BCryptPasswordEncoder()
    userRepo.save(
        Person(
            firstName = "abc",
            lastName = "def",
            email = "a@b.com",
            password = passwordEncoder.encode("pass")))
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(BootApplication::class.java, *args)
}
