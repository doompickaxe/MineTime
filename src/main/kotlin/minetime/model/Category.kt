package minetime.model

import javax.persistence.Embeddable

@Embeddable
data class Category(val name: String)