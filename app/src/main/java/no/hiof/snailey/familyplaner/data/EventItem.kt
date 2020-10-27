package no.hiof.snailey.familyplaner.data

import com.google.firebase.database.Exclude
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView

class EventItem(
    @get:Exclude
    var id: String? = null,
    val name: String,
    override val date: CalendarDate,
    override val color: Int,
    //val time: CalendarDate,
    //var description: String,


    ) : CalendarView.DateIndicator

{
    override fun equals(other: Any?): Boolean {
        return if (other is Shopping) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }

}