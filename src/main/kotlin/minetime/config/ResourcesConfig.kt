package minetime.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class ResourcesConfig : WebMvcConfigurer {
  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    registry.addResourceHandler("/resources/**")
      .addResourceLocations("classpath:/static/")
  }

  override fun addViewControllers(registry: ViewControllerRegistry) {
    registry.addViewController("/").setViewName("notLoggedIn/index")
    registry.addViewController("/login").setViewName("notLoggedIn/signIn")
  }
}