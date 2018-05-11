package minetime

import minetime.model.Category
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
  fun init(userRepo: PersonRepository, projectRepo: ProjectRepository, passwordEncoder: BCryptPasswordEncoder) = CommandLineRunner {
    log.info("_______init________")

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

    val projectOfA = Project(name = "FirstOfAll", owner = personA)
    projectOfA.addMembers(personB)
    projectOfA.addCategories(Category("Base"), Category("notBase"))
    projectRepo.save(Project(name = "SecondOfAll", owner = personB))
    projectRepo.save(projectOfA.addMembers(personB))
  }
}

fun main(args: Array<String>) {
  SpringApplication.run(BootApplication::class.java, *args)
}
