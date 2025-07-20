import java.util.concurrent.atomic.AtomicInteger


// Example of a problem situation race condition
object RaceConditionExample {

    private var counter = 0

    fun runMultithreadIncrementing() {
        val numberOfThreads = 10
        val threads = Array(numberOfThreads) {
            Thread {
                for (j in 0 until 100_000) {
                    counter++
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach {
            try {
                it.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        println("Final counter value: $counter")
    }
}


// In the implementation of the "RaceConditionExample" class, you can see a clear example of the consequences
// of the "race condition" problem. Due to the fact that threads randomly access the variable and may not have time
// to perform both reading the value from memory "counter = 92", and calculating "92 + 1", and saving the new value "counter = 93",
// not all iterations of increasing the number will be applied. However, if we want to make sure that a variable
// is atomic and threads also act with it as with atomic, we need to explicitly designate this. For example, in FixedRaceConditionExample
// was used an AtomicInteger, not regular Int.
object FixedRaceConditionExample {

    private var counter = AtomicInteger(0)

    fun runMultithreadIncrementing() {
        val numberOfThreads = 10
        val threads = Array(numberOfThreads) {
            Thread {
                for (j in 0 until 100_000) {
                    counter.incrementAndGet()
                }
            }
        }

        threads.forEach { it.start() }
        threads.forEach { it.join() }

        println("Final counter value!: $counter")
    }
}



// An example of a deadlock problem situation
object DeadlockExample {
    private val lock1 = Any()
    private val lock2 = Any()

    fun runTwothreadedProcess() {
        val thread1 = Thread {
            synchronized(lock1) {
                println("Thread 1 acquired lock1")

                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                synchronized(lock2) {
                    println("Thread 1 acquired lock2")
                }
            }
        }

        val thread2 = Thread {
            synchronized(lock2) {
                println("Thread 2 acquired lock2")

                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                synchronized(lock1) {
                    println("Thread 2 acquired lock1")
                }
            }
        }

        thread1.start()
        thread2.start()

        try {
            thread1.join()
            thread2.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        println("Finished")
    }

}


// In the example above (DeadlockExample), we saw an example of how two objects are locked in threads at once without
// releasing the first object, which then caused a deadlock, and none of the threads could complete their action.
// Inside the FixedDeadlockExample, the lock of the second object was removed from the lock body of the first object,
// so that the second thread intercepts control, but the deadlock does not occur.
object FixedDeadlockExample {
    private val lock1 = Any()
    private val lock2 = Any()

    fun runTwothreadedProcess() {
        val thread1 = Thread {
            synchronized(lock1) {
                println("Thread 1 acquired lock1")
                Thread.sleep(100)
            }

            synchronized(lock2) {
                println("Thread 1 acquired lock2")
                Thread.sleep(100)
            }
        }

        val thread2 = Thread {
            synchronized(lock2) {
                println("Thread 2 acquired lock1")
                Thread.sleep(100)
            }
            synchronized(lock1) {
                println("Thread 2 acquired lock2")
                Thread.sleep(100)
            }
        }

        thread1.start()
        thread2.start()

        thread1.join()
        thread2.join()


        println("Finished")
    }

}


fun main() {
    println("RaceConditionExample:")
    RaceConditionExample.runMultithreadIncrementing()

    println("FixedRaceConditionExample:")
    FixedRaceConditionExample.runMultithreadIncrementing()

//    println("DeadlockExample:")
//    DeadlockExample.runTwothreadedProcess()

    println("FixedDeadlockExample:")
    FixedDeadlockExample.runTwothreadedProcess()

}
