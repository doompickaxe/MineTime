package minetime.model

import org.apache.commons.lang3.builder.HashCodeBuilder
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

@Entity
data class Person(
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  var id: UUID = UUID(0, 0),

  @field:NotEmpty(message = "Please enter your first name")
  var firstName: String,

  @field:NotEmpty(message = "Please enter your last name")
  var lastName: String,

  @Column(unique = true, nullable = false)
  @field:Email(message = "Must be a valid email")
  var email: String,

  @field:NotEmpty(message = "Please set a password")
  var password: String) {

  @field:ManyToMany(mappedBy = "_members", fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
  val projects: List<Project> = listOf()

  fun getFullName() = "$firstName $lastName"

  override fun equals(other: Any?): Boolean {
    if(other === this) return true
    if(other == null) return false
    if(other !is Person) return false
    return other.id == id
  }

  override fun hashCode(): Int {
    return HashCodeBuilder(3, 19)
      .append(id)
      .toHashCode()
  }
}

