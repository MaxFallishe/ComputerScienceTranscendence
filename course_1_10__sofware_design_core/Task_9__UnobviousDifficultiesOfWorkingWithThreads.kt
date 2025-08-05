import java.util.concurrent.atomic.AtomicInteger

// ORIGINAL VERSION OF CODE:
// fun main() {
//     var counter = 0
//     val task = Runnable {
//         for (i in 0 until 1000) {
//             counter++
//         }
//     }
//
//     val thread1 = Thread(task)
//     val thread2 = Thread(task)
//
//     thread1.start()
//     thread2.start()
//
//     try {
//         thread1.join()
//         thread2.join()
//     } catch (e: InterruptedException) {
//         e.printStackTrace()
//     }
//
//     println("Counter: $counter")
// }


// CORRECTED VERSION OF CODE:
fun main() {

    val counter = AtomicInteger(0)

    val task = Runnable {
        for (i in 0 until 1000) {
            counter.incrementAndGet()
        }
    }

    val thread1 = Thread(task, "Thread-1")
    val thread2 = Thread(task, "Thread-2")

    thread1.start()
    thread2.start()

    try {
        thread1.join()
        thread2.join()
    } catch (e: InterruptedException) {
        throw RuntimeException("Threads interrupted", e)
    }

    println("Counter: $counter")
}