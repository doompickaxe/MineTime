package minetime.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
data class Person(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1L,

    @field:NotEmpty(message = "Please enter your firstname")
    var firstName: String,

    @field:NotEmpty(message = "Please enter your lastname")
    var lastName: String,

    @Column(unique = true, nullable = false)
    @field:Email(message = "Must be a valid email")
    var email: String,

    @field:NotEmpty(message = "Please set a password")
    var password: String) {

  @ManyToMany(mappedBy = "members")
  val projects: List<Project> = listOf()
}

