package minetime.model

import javax.persistence.*

@Entity
data class Person(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = -1L,
        var firstName: String,
        var lastName: String,
        var email: String,
        var password: String) {

    @ManyToMany(mappedBy = "members")
    val projects: List<Project> = listOf()
}
