import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.write
import kotlin.concurrent.read
import kotlin.concurrent.thread
import kotlinx.coroutines.*
import java.util.concurrent.Semaphore


// 1. Example of a task for which the "Atomic Operations" type of synchronization is best suited (AtomicInt in this case).
//    Task description: It is necessary to count the number of views, while the number of views increases in different streams.
//    Note: The "Atomic operations" synchronization method is used only in multithreaded programming, not asynchronous.
fun atomicViewCounter() {
    println("\n--| Atomic Counter Example |--")
    val viewCounter = AtomicInteger(0)
    val threads = List(10) {
        thread {
            repeat(100) {
                viewCounter.incrementAndGet()
            }
        }
    }
    threads.forEach { it.join() }
    println("Result: ${viewCounter.get()}")
}


// 2. An example of a task for which the "Barrier" synchronization type is best suited (CyclicBarrier in this case).
//    Task description: It is necessary to implement a MapReduce function where it is necessary for 5 threads to complete the Map stage before they all simultaneously proceed to the Reduce stage
//    Note: The "Barrier" synchronization method is used only in multithreaded programming, not asynchronous programming.
fun mapReduceBarrier() {
    println("\n--| Cyclic Barrier Example |--")

    val numberOfWorkers = 5

    val mapPhaseBarrier = CyclicBarrier(numberOfWorkers) {
        println("All $numberOfWorkers workers finished Map phase")
    }

    val threads = List(numberOfWorkers) { index ->
        thread(name = "Worker-$index") {
            println("${Thread.currentThread().name} started Map phase")
            Thread.sleep((500..1500).random().toLong())

            println("${Thread.currentThread().name} finished Map phase, waiting at barrier")
            mapPhaseBarrier.await()

            println("${Thread.currentThread().name} started Reduce phase")
        }
    }

    threads.forEach { it.join() }
}


// 3. An example of a task for which the "Read-Write Lock" synchronization type (ReentrantReadWriteLock) is best suited
//    Task description: There is a cache that is read by dozens of threads, but updated by only one. Multiple reads should be allowed, but only one thread should be able to write.
//    Note: The "Read-Write Lock" synchronization method is used only in multithreaded programming, not asynchronous programming.
class SafeMultithreadingCache {
    private val rntLock = ReentrantReadWriteLock()
    private var cacheData: String = "initial"

    fun readCache(threadName: String) {
        rntLock.read {
            println("[$threadName] reading cache: $cacheData")
            Thread.sleep(100)
        }
    }

    fun writeCache(newValue: String, threadName: String) {
        rntLock.write {
            println("[$threadName] writing cache: $newValue")
            Thread.sleep(200)
            cacheData = newValue
        }
    }
}

fun reentrantReadWriteLockExample() {
    println("\n--| Read-Write Lock Cache Example |--")
    val cache = SafeMultithreadingCache()

    val readers = List(5) { i ->
        thread(name = "Reader-$i") {
            repeat(3) {
                cache.readCache(Thread.currentThread().name)
            }
        }
    }

    val writer = thread(name = "Writer") {
        repeat(3) {
            Thread.sleep(300)
            val newValue = "value-${System.currentTimeMillis()}"
            cache.writeCache(newValue, Thread.currentThread().name)
        }
    }

    (readers + writer).forEach { it.join() }

}


// 4. An example of a task for which the "CompletableFuture" synchronization type is best suited
//    Task description: An asynchronous HTTP request is being made, and after it is completed, processing must continue. However, the result may come later — it is important not to block the main stream.
//    Note: The "CompletableFuture" synchronization method is used in both asynchronous and multithreaded programming.
fun futureExample() = runBlocking {
    println("\n--| Async HTTP Request Example |--")

    val httpRequest = async {
        delay(1000) // имитируем задержку сети
        "response: {\"status\": 200, \"data\": \"Some data...\"}"
    }

    println("Main coroutine do some other work...")

    val result = httpRequest.await()
    println("Response: $result")
}


// 5. An example of a task for which the "Semaphore" synchronization type is best suited
// Task description: There is a limited pool of 3 database connections. You should allow no more than 3 threads
// to use connections at the same time. The rest should wait their turn.
// Note: The "Semaphore" synchronization method is used in both asynchronous and multithreaded programming.
fun semaphoreExample() {
    println("\n--| Semaphore Example |--")

    val semaphore = Semaphore(3)

    val threads = List(10) { i ->
        thread {
            println("[Thread $i] Waiting for DB connection...")
            semaphore.acquire()
            try {
                println("[Thread $i] Connected to DB")
                Thread.sleep((500..1000).random().toLong())
                println("[Thread $i] Done and disconnected")
            } finally {
                semaphore.release()
            }
        }
    }

    threads.forEach { it.join() }
}


fun main() {
    atomicViewCounter()

    mapReduceBarrier()

    reentrantReadWriteLockExample()

    futureExample()

    semaphoreExample()

}


