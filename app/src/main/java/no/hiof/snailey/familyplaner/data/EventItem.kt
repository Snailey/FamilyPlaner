package no.hiof.snailey.familyplaner.data

import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView

class EventItem(
    override val date: CalendarDate,
    override val color: Int,
    val eventName: String

) : CalendarView.DateIndicator