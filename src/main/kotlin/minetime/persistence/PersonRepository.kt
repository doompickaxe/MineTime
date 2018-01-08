package minetime.persistence

import minetime.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface PersonRepository : JpaRepository<Person, Long> {
  fun findByEmail(email: String): Person
}