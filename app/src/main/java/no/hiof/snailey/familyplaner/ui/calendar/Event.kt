package no.hiof.snailey.familyplaner.ui.calendar

class Event(
    var title: String,
    var location: String,
    var day: Int,
    var month: Int,
    var year: Int,
    var hour: Int,
    minute: Int
) {
    var minute: String? = null

    init {
        if (minute < 10) {
            this.minute = "0$minute"
        } else {
            this.minute = minute.toString()
        }
    }
}