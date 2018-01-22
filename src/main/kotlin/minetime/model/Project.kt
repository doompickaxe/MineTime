package minetime.model

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class Project(@Id
                   @GeneratedValue(generator = "uuid2")
                   @GenericGenerator(name = "uuid2", strategy = "uuid2")
                   @Column(columnDefinition = "BINARY(16)")
                   var id: UUID = UUID(0,0),

                   @field:NotBlank
                   var name: String,

                   @ManyToOne
                   var owner: Person) {

  @field:ManyToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
  @field:JoinTable
  private val _members = mutableListOf(owner)

  @Transient
  val members: List<Person> = _members.toList()

  fun addMembers(vararg person: Person): Project {
    _members.addAll(person)
    return this
  }

  fun isPartOf(person: Person) = person == owner  || members.contains(person)
}
