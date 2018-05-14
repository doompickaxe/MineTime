package minetime.model

import javax.persistence.*

@Entity
data class Transaction(@Id
                       @GeneratedValue
                       val id: Long = 0,
                       @ManyToOne
                       val person: Person,
                       val category: Category,
                       @ManyToOne
                       var project: Project? = null,
                       val amount: Double)