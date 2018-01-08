package minetime.model

import javax.persistence.*

@Entity
data class Project(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = -1L, var name: String, @ManyToOne var owner: Person) {

  @field:ManyToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
  @field:JoinTable
  private val _members = mutableListOf(owner)

  @Transient
  val members: List<Person> = _members.toList()

  fun addMembers(vararg person: Person): Project {
    _members.addAll(person)
    return this
  }
}
