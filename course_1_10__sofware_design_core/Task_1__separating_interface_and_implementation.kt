import java.sql.Connection
import java.sql.DriverManager


interface Storage {
    fun save(data: String)
    fun retrieve(id: Int): String?
}


class DatabaseStorage : Storage {
    private lateinit var connection: Connection

    fun initConnection(conn: Connection) {
        connection = conn
        connection.createStatement().use {
            it.executeUpdate(
                """CREATE TABLE IF NOT EXISTS storage (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    data TEXT
                """.trimIndent()
            )
        }
    }

    override fun save(data: String) {
        check(::connection.isInitialized) { "Connection not initialized" }
        connection.prepareStatement("INSERT INTO storage(data) VALUES(?)").use {
            it.setString(1, data)
            it.executeUpdate()
        }
    }

    override fun retrieve(id: Int): String? {
        check(::connection.isInitialized)
        connection.prepareStatement("SELECT data FROM storage WHERE id = ?").use {
            it.setInt(1, id)
            val rs = it.executeQuery()
            return if (rs.next()) rs.getString("data") else null
        }
    }
}


fun main() {
    // This example implies additional configuration of the details of the database connection
    // via initConnection. However, it must be remembered that this may interfere with the original idea of dividing
    // into an interface and an implementation, because other implementations most likely will not have
    // the initConnection lateinit variable.
    val dbStorage = DatabaseStorage()
    dbStorage.initConnection(...)  // Here we should implement more details about our connection
    dbStorage.save("String data")
    println("Retrieved: ${dbStorage.retrieve(1)}")
}