package minetime.model

import javax.persistence.*

@Entity
class Project(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = -1L, var name: String, @ManyToOne var owner: Person) {
    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    @JoinTable(name = "members")
    val members: List<Person> = listOf(owner)
}
