package minetime.persistence

import minetime.model.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepository : JpaRepository<Project, Long>