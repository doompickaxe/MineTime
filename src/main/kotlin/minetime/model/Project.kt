package minetime.model

import org.hibernate.annotations.GenericGenerator
import org.slf4j.LoggerFactory
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

val logger = LoggerFactory.getLogger(Project::class.java)

@Entity
data class Project(
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16)")
  val id: UUID = UUID(0, 0),

  @field:NotBlank
  var name: String,

  @ManyToOne
  var owner: Person) {

  @field:ManyToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.EAGER)
  @field:JoinTable
  private val _members = mutableSetOf(owner)

  @ElementCollection(targetClass = Category::class)
  private val categories = mutableSetOf<Category>()

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
  private val transactions = mutableListOf<Transaction>()

  fun members() = _members.toSet()

  fun transactions() = transactions.toList()

  fun categories() = categories.toList()

  fun addMembers(vararg person: Person): Project {
    _members.addAll(person)
    return this
  }

  fun addCategories(vararg categories: Category): Project {
    this.categories.addAll(categories)
    return this
  }

  fun addTransaction(vararg transaction: Transaction): Project {
    transaction
      .filter { categories.contains(it.category) }
      .forEach {
        transactions.add(it)
        it.project = this
      }
    return this
  }

  fun isPartOf(person: Person) = person === owner || _members.contains(person)
}
