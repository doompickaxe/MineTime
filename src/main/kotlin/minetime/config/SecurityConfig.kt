package minetime.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SecurityConfig(val datasource: DataSource) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/signUp", "/resources/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .successForwardUrl("/hello")
        .defaultSuccessUrl("/hello")
        .and()
        .logout()
        .permitAll()
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.jdbcAuthentication()
        .dataSource(datasource)
        .usersByUsernameQuery("select email, password, true from person where email=?")
        .authoritiesByUsernameQuery("select ?, 'user' from dual")

    auth.userDetailsService(auth.defaultUserDetailsService)
        .passwordEncoder(passwordEncoder())
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}