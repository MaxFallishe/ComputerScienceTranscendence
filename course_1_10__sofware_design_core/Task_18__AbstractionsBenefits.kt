// In this experimental "Stickman and Chips" pattern, the task of assigning each function a certain price for execution
// is performed. For example, we want to refine the existing class with additional functionality - now calling
// each function is conditionally not free, each of them now costs some kind of currency, conditional chips.
// And if an object runs out of this currency, it can no longer call those functions that require this currency.
// However, it should be borne in mind that there is not a very convenient moment in this implementation,
// namely ".withChips<SellerActions>()" which needs to be added to an object every time a new object
// is created in the code, and if such a pattern is used to adapt existing classes, then correcting all the points
// where an object of the corrected class is created will not be the most pleasant experiment.


// Original MarketplaceSeller class and Seller interface before updating with Croupier logic
//interface SellerActions {
//    fun notifyAllCustomers()
//    fun activateHighPriorityMode()
//    fun deactivateHighPriorityMode()
//}
//
//class MarketplaceSeller(
//    val id: Int,
//    val customers: List<String>,
//    var isInTopSellersList: Boolean = false
//) : SellerActions {
//
//    override fun notifyAllCustomers() {
//        customers.forEach { println("Customer $it was notified") }
//    }
//
//    override fun activateHighPriorityMode() {
//        if (isInTopSellersList) {
//            println("You are already in the Top sellers list.")
//            return
//        }
//        isInTopSellersList = true
//        println("You were added to the Top sellers list ↓↓↓")
//    }
//
//    override fun deactivateHighPriorityMode() {
//        if (!isInTopSellersList) {
//            println("You are already out of the Top sellers list.")
//            return
//        }
//        isInTopSellersList = false
//        println("You were removed from the Top sellers list ↑↑↑")
//    }
//}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Croupier(val cost: Int)


interface CroupierHost { var moneyBalanceUSD: Int }


interface SellerActions {
    @Croupier(cost = 10) fun notifyAllCustomers()
    @Croupier(cost = 10) fun activateHighPriorityMode()
    @Croupier(cost = 10) fun deactivateHighPriorityMode()
}

class MarketplaceSeller(
    val id: Int,
    val customers: List<String>,
    var isInTopSellersList: Boolean = false
) : SellerActions, CroupierHost {

    override var moneyBalanceUSD: Int = 50

    override fun notifyAllCustomers() {
        customers.forEach { println("Customer $it was notified") }
    }

    override fun activateHighPriorityMode() {
        if (isInTopSellersList) {
            println("You are already in the Top sellers list.")
            return
        }
        isInTopSellersList = true
        println("You were added to the Top sellers list ↓↓↓")
    }

    override fun deactivateHighPriorityMode() {
        if (!isInTopSellersList) {
            println("You are already out of the Top sellers list.")
            return
        }
        isInTopSellersList = false
        println("You were removed from the Top sellers list ↑↑↑")
    }
}

object Chips {
    fun <I : Any> wrap(host: CroupierHost, impl: I, iface: Class<I>): I {
        val handler = java.lang.reflect.InvocationHandler { _, m, args ->
            m.getAnnotation(Croupier::class.java)?.let { ann ->
                val need = ann.cost
                if (host.moneyBalanceUSD < need) {
                    println("Not enough USD: need $need USD, have ${host.moneyBalanceUSD} USD. Call blocked.")
                    return@InvocationHandler null
                }
                host.moneyBalanceUSD -= need
                println("Charged $need USD. Balance: ${host.moneyBalanceUSD} USD")
            }
            m.invoke(impl, *(args ?: emptyArray()))
        }
        @Suppress("UNCHECKED_CAST")
        return java.lang.reflect.Proxy.newProxyInstance(
            iface.classLoader, arrayOf(iface), handler
        ) as I
    }
}


inline fun <reified I : Any> I.withChips(): I {
    val host = this as? CroupierHost ?: error("Object must implement CroupierHost")
    return Chips.wrap(host, this, I::class.java)
}


fun main() {
    val seller = MarketplaceSeller(1, listOf("Alice", "Bob")).withChips<SellerActions>()
    seller.notifyAllCustomers()
    seller.activateHighPriorityMode()
    seller.deactivateHighPriorityMode()
    repeat(10) { seller.activateHighPriorityMode() } // will block when money run out
}
