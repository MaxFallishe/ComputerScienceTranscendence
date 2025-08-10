import kotlin.math.min
import kotlin.random.Random


// ORIGINAL VERSION OF CODE:
//import java.util.Random
//
//object ComplexMultiThreadProcessing {
//    private const val SIZE = 1_000_000
//    private const val THREADS = 4
//    private val data = IntArray(SIZE)
//    @Volatile private var sum: Int = 0
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val random = Random()
//        for (i in 0 until SIZE) {
//            data[i] = random.nextInt(100)
//        }
//
//        val threads = arrayOfNulls<Thread>(THREADS)
//        val chunkSize = SIZE / THREADS
//
//        for (i in 0 until THREADS) {
//            val start = i * chunkSize
//            val end = (i + 1) * chunkSize
//            threads[i] = Thread {
//                var localSum = 0
//                for (j in start until end) {
//                    localSum += data[j]
//                }
//                synchronized(ComplexMultiThreadProcessing::class.java) {
//                    sum += localSum
//                }
//            }
//            threads[i]!!.start()
//        }
//
//        for (i in 0 until THREADS) {
//            try {
//                threads[i]!!.join()
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        }
//
//        println("Sum of all elements: $sum")
//    }
//}



// CORRECTED VERSION OF CODE:
object ComplexMultiThreadProcessing {
    private const val SIZE = 1_000_000
    private const val THREADS = 4

    @JvmStatic
    fun main(args: Array<String>) {
        val data = IntArray(SIZE) { Random.nextInt(100)}
        val sums = LongArray(THREADS)
        val chunk = (SIZE + THREADS - 1) / THREADS

        val threads = List(THREADS) { i ->
            Thread {
                val start = i * chunk
                val end = min(SIZE, start + chunk)
                var local = 0L
                for (j in start until end) local += data[j]
                sums[i] = local
            }
        }

        threads.forEach (Thread::start)
        threads.forEach (Thread::join)

        val result = sums.sum()
        println("Sum of all elements: $result")
    }
}
