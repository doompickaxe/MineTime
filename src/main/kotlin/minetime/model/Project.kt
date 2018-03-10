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
                   val id: UUID = UUID(0,0),

                   @field:NotBlank
                   var name: String,

                   @ManyToOne
                   var owner: Person) {

  @field:ManyToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
  @field:JoinTable
  private val _members = mutableSetOf(owner)

  @ElementCollection(targetClass = Category::class, fetch = FetchType.EAGER)
  val _categories = mutableSetOf<Category>()

  fun members() = _members.toSet()

  fun categories() = _categories.toSet()

  fun addMembers(vararg person: Person): Project {
    _members.addAll(person)
    return this
  }

  fun addCategories(vararg categories: Category): Project {
    _categories.addAll(categories)
    return this
  }

  fun isPartOf(person: Person) = person == owner || _members.contains(person)
}
