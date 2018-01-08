package minetime.persistence

import minetime.model.Person
import minetime.model.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long> {
  fun findByOwner(person: Person): List<Project>
}