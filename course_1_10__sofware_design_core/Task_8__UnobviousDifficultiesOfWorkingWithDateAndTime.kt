// ORIGINAL VERSION OF CODE:
// One of the points is that such a date and time ("99999-99-13 34:30:00") they will be processed
// by the code below without an explicit error
//
// import java.text.ParseException
// import java.text.SimpleDateFormat
// import java.util.Date
//
// val dateString = "2024-05-13 14:30:00"
// val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
// try {
//     val date: Date = format.parse(dateString)
//     println("Date: $date")
// } catch (e: ParseException) {
//     e.printStackTrace()
// }


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.format.DateTimeParseException


fun main() {

    // CORRECTED VERSION OF CODE:
    val dateTimeString = "2024-05-13 14:30:00"
    val dateTimeFormater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    try {
        val localDateTime = LocalDateTime.parse(dateTimeString, dateTimeFormater)
        val zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Almaty"))

        println("ZonedDateTime: $zonedDateTime")
    } catch (e: DateTimeParseException) {
        println("Parsing error: ${e.message}")
    }

}