import java.math.BigDecimal

// In class below, no check for negative amounts in the 'deposit' method, which allows you to increase the balance by depositing a negative value.
// In class below, no check for negative amounts in the 'withdraw' method, which allows you to reduce the balance by attempting to withdraw a negative value.
// In class below, no verification of the sufficiency of funds in the account in the 'withdraw method', which allows you to withdraw to a negative bank account balance.
class BankAccountWithFalseStability(private var balance: Double) {

    fun deposit(amount: Double) {
        balance += amount // No check for negative amount
    }

    fun withdraw(amount: Double) {
        balance -= amount // No check for negative amount or overdraft
    }

    fun getBalance(): Double {
        return balance
    }
}

// In this implementation, we solved most of the problems with the non-correct functionality of the class methods
// by introducing a new type instead of Double - "Tenge" (each tenge has 100 tiin).
// Checking for the possibility of withdrawing the amount was implemented in the "withdraw" method itself
class BankAccountWithTrueStability(private var balance: Tenge) {

    fun deposit(amount: Tenge) {
        balance = balance + amount
    }

    fun withdraw(amount: Tenge) {
        if (amount > balance) {
            throw IllegalArgumentException("Cannot withdraw $amount, balance is only $balance")
        }
        balance = balance - amount
    }

    fun getBalance(): Tenge {
        return balance
    }
}


@JvmInline
value class Tenge private constructor(val amount: Double) {
    companion object {
        fun of(value: Double): Tenge {
            require(value > 0) { "Tenge currency amount must be greater than 0" }
            val decimal = BigDecimal.valueOf(value)
            require(decimal.scale() <= 2) { "Tenge currency amount must have exactly two signs after dot" }
            return Tenge(value)
        }
    }

    operator fun plus(other: Tenge): Tenge = of(this.amount + other.amount)

    operator fun minus(other: Tenge): Tenge = of(this.amount - other.amount)

    operator fun compareTo(other: Tenge): Int = this.amount.compareTo(other.amount)

}


fun main() {
    val account = BankAccountWithTrueStability(Tenge.of(1000.00))
    println("Initial balance: ${account.getBalance()}") // 1000.00


    account.deposit(Tenge.of(500.00))
    println("Balance status: ${account.getBalance()}") // 1500.00

    account.withdraw(Tenge.of(300.00))
    println("Balance status: ${account.getBalance()}") // 1200.00

    // Attempt to withdraw more than possible
    try {
        account.withdraw(Tenge.of(9000.00))
    } catch (e: IllegalArgumentException) {
        println("Withdraw failed: ${e.message}")
    }

    // Attempt to withdraw negative amount
    try {
        account.withdraw(Tenge.of(-200.00))
    } catch (e: IllegalArgumentException) {
        println("Negative withdraw is not available: ${e.message}")
    }

    // Attempt to deposit a small amount of currency
    try {
        account.deposit(Tenge.of(0.0000000000000001))
    } catch (e: IllegalArgumentException) {
        println("Deposit operation failed: ${e.message}")
    }

}
