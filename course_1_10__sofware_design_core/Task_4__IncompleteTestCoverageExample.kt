import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

// When we try to design and create a function/class, we must assume that it is unlikely that we will be able
// to fully prove the correctness of this element of the program code using a small number of tests.
// You can't trust just a couple of tests with an ideal scenario and a few edge cases.
class AverageCalculator {
    fun calculateAverage(numbers: IntArray): Double {
        require(numbers.isNotEmpty()) { "Array cannot not be empty" }
        // It is always good to use the built-in language tools when possible, as they are a priori tested quite thoroughly.
        // Here better return just numbers.average()
        // At the same time, we need to understand the important points of their work and understand whether
        // their behavior suits us as developers.
        val sum = numbers.sum()
        return sum.toDouble() / numbers.size
    }
}


class AverageCalculatorTest {

    private val calculator = AverageCalculator()

    @Test
    fun `Average of regular list`() {
        assertEquals(3.0, calculator.calculateAverage(intArrayOf(1, 2, 3, 4, 5)), 0.0001)
    }

    @Test
    fun `Average of single value`() {
        assertEquals(42.0, calculator.calculateAverage(intArrayOf(42)), 0.0001)
    }

    @Test
    fun `Average of only negative values`() {
        assertEquals(-6.0, calculator.calculateAverage(intArrayOf(-3, -6, -9)), 0.0001)
    }

    @Test
    fun `Average of mixed values`() {
        assertEquals(0.0, calculator.calculateAverage(intArrayOf(-10, 0, 10)), 0.0001)
    }

    @Test
    fun `Throws on empty array`() {
        val exception = assertThrows<IllegalArgumentException> {
            calculator.calculateAverage(intArrayOf())
        }
        assertEquals("Array cannot not be empty", exception.message)
    }

//    The test coverage looks more than exhaustive, but it is possible to guess that some introductory
//    ones can still ruin the work of our class.
//    @Test
//    fun `Bad average overflows but safe one works`() {
//        val enormousInt = Int.MAX_VALUE
//        assertEquals(enormousInt.toDouble(), calculator.calculateAverage(intArrayOf(enormousInt, enormousInt, enormousInt)), 0.0001)
//    }

}
