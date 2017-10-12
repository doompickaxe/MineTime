package minetime

import minetime.model.Person
import minetime.persistence.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BootApplication {
    private val log = LoggerFactory.getLogger(BootApplication::class.java)

    @Bean
    fun init(pr: PersonRepository) = CommandLineRunner {
        log.info("_______init________")
        pr.save(Person(firstName = "A", lastName = "B", email = "C", password = "D"))
        pr.findAll().forEach { p -> log.info(p.toString()) }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(BootApplication::class.java, *args)
}
