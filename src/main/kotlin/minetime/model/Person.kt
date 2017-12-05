package minetime.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1L,

    @field:NotEmpty
    var firstName: String,

    @field:NotEmpty
    var lastName: String,

    @Column(unique = true, nullable = false)
    @field:NotEmpty
    @field:Email
    var email: String,

    @field:NotEmpty
    var password: String) {

  @ManyToMany(mappedBy = "members")
  val projects: List<Project> = listOf()
}

