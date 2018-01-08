package minetime

import minetime.model.Person
import minetime.model.Project
import minetime.persistence.PersonRepository
import minetime.persistence.ProjectRepository
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
  fun init(userRepo: PersonRepository, projectRepo: ProjectRepository) = CommandLineRunner {
    log.info("_______init________")
    val passwordEncoder = BCryptPasswordEncoder()

    var personA = Person(
      firstName = "abc",
      lastName = "def",
      email = "a@b.com",
      password = passwordEncoder.encode("pass"))
    var personB = Person(
      firstName = "abc",
      lastName = "def",
      email = "b@b.com",
      password = passwordEncoder.encode("pass"))

    personA = userRepo.save(personA)
    personB = userRepo.save(personB)

    val projectOfB = Project(name = "SecondOfAll", owner = personB)
    projectOfB.addMembers(personA)
    projectRepo.save(Project(name = "FirstOfAll", owner = personA))
    projectRepo.save(projectOfB)
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(BootApplication::class.java, *args)
}
