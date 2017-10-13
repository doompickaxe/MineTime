package minetime

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BootApplication {
    private val log = LoggerFactory.getLogger(BootApplication::class.java)

    @Bean
    fun init() = CommandLineRunner {
        log.info("_______init________")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(BootApplication::class.java, *args)
}
