package minetime.config

import de.neuland.jade4j.JadeConfiguration
import de.neuland.jade4j.spring.template.SpringTemplateLoader
import de.neuland.jade4j.spring.view.JadeViewResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.ViewResolver

@Configuration
class PugConfig {
  @Bean
  fun templateLoader(): SpringTemplateLoader {
    val templateLoader = SpringTemplateLoader()
    templateLoader.basePath = "classpath:/templates/"
    return templateLoader
  }

  @Bean
  fun jadeConfiguration(): JadeConfiguration {
    val configuration = JadeConfiguration()
    configuration.isCaching = false
    configuration.templateLoader = templateLoader()
    return configuration
  }

  @Bean
  fun viewResolver(): ViewResolver {
    val viewResolver = JadeViewResolver()
    viewResolver.setConfiguration(jadeConfiguration())
    return viewResolver
  }
}