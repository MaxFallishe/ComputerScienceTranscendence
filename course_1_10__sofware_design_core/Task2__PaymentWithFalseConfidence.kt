// Using the example of a simple class implementing a bank account, let's see how a program
// that works without exception and works stably can contain bugs.

class BankAccount(private var balance: Double) {
    fun deposit(amount: Double) {
        balance += amount
    }

    fun withdraw(amount: Double) {
        balance -= amount
    }

    fun getBalance() : Double {
        return balance
    }
}



fun main() {
    // Here we see that the main operations are working stably, and it
    // would seem that there is something to worry about.
    val account = BankAccount(1000.0)
    println("Initial balance: ${account.getBalance()}") // Balance is 1000.0

    // Simple transfer works well
    account.deposit(50.0)
    println("Balance status: ${account.getBalance()}") // The balance is 1050.0

    // Withdrawal works fine too
    account.withdraw(30.0)
    println("Balance status: ${account.getBalance()}") // The balance is 1020.0

    // However, suddenly we can withdraw more than we have in the account, which does not correspond to logic
    account.withdraw(1100.0)
    println("Balance status: ${account.getBalance()}") // The balance is -80.0

    // Also, it should not be that we can deposit a negative amount and withdraw a negative amount
    account.withdraw(-200.0)
    println("Balance status: ${account.getBalance()}") // The balance is 120.0

    account.deposit(-100.0)
    println("Balance status: ${account.getBalance()}") // The balance is 20.0

    // Now let's try to add a very small number to our account amount, the program does this even though
    // such an operation should most likely be impossible according to the system's design
    account.deposit(0.0000000000000001)
    println("После депозита 1e-16: ${account.getBalance()}")
    val stateOne = account.getBalance()

    account.deposit(0.0000000000000001)
    println("После второго депозита 1e-16: ${account.getBalance()}")

    // Although the values should differ because one is smaller than the other,
    // albeit by a very small value, the values are equal, which is also a bug.
    val stateTwo = account.getBalance()
    println("После второго депозита 1e-16: ${stateOne == stateTwo}")
}

// In this case, this is a clear example of why the working code at first glance with working
// basic scripts does not guarantee the correctness of the program as a whole.