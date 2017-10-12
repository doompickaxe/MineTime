package minetime.persistence

import minetime.model.Person
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, Long>