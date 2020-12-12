package no.hiof.snailey.familyplaner.data

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Event(
    var title: String? = null,
    var location: String? = null,
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null,
    var hour: Int? = null,
    var minute: Int? = null,
    @get:Exclude
    var isDeleted: Boolean = false,
    @get:Exclude
    var id: String? = null,
) {
    override fun equals(other: Any?): Boolean {
        return if (other is Shopping) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        return result
    }
}

