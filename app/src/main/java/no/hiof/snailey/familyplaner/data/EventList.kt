package no.hiof.snailey.familyplaner.data

import com.google.firebase.database.Exclude

data class EventList(
    @get:Exclude
    var id: String? = null,
    var name: String? = null,
    @get:Exclude
    var isDeleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return if (other is EventList) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

}