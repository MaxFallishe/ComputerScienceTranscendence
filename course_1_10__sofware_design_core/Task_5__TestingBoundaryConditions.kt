import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.IllegalArgumentException

class GradeCalculator {
    fun <T : Number> calculateAverage(grades: List<T>): Double {
        require(grades.isNotEmpty()) { "Array cannot be empty" }
        require(grades.all() { it.toDouble() >= 0 }) { "List cannot contain negative values" }

        return grades.map { it.toDouble() }.average()
    }
}


class GradeCalculatorTest {

    private val calculator = GradeCalculator()

    @Test
    fun `Average of regular list`() {
        assertEquals(3.0, calculator.calculateAverage(listOf(1, 2, 3, 4, 5)), 0.0001)
    }

    @Test
    fun `Average of single value`() {
        assertEquals(42.0, calculator.calculateAverage(listOf(42)), 0.0001)
    }

    @Test
    fun `Average of only negative values`() {
        val exception = assertThrows<IllegalArgumentException> {
            assertEquals(-6.0, calculator.calculateAverage(listOf(-3, -6, -9)), 0.0001)
        }
        assertEquals("List cannot contain negative values", exception.message)
    }

    @Test
    fun `Average of mixed values`() {
        val exception = assertThrows<IllegalArgumentException> {
            assertEquals(0.0, calculator.calculateAverage(listOf(-10, 0, 10)), 0.0001)
        }
        assertEquals("List cannot contain negative values", exception.message)
    }

    @Test
    fun `Average of mixed types values`() {
        assertEquals(9.0, calculator.calculateAverage(listOf(10.0, 7, 10.toByte())), 0.0001)
    }

    @Test
    fun `Throws on empty array`() {
        val exception = assertThrows<IllegalArgumentException> {
            calculator.calculateAverage(listOf())
        }
        assertEquals("Array cannot be empty", exception.message)
    }

    @Test
    fun `Bad average overflows but safe one works`() {
        val enormousInt = Int.MAX_VALUE
        assertEquals(
            enormousInt.toDouble(),
            calculator.calculateAverage(listOf(enormousInt, enormousInt, enormousInt)),
            0.0001
        )
    }

}