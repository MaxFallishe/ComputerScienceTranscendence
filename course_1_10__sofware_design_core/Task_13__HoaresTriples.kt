// Proof of the correctness "customMaxAbs" function using Hoare triples.
//
// {P: a = num1, b = num2} customMaxAbs(num1, num2) {Q: res = |∣a∣-∣b∣| + ∣b∣}
// Pre-condition: `a = num1; b = num2` (num1 is a representation of the number a, num2 is a representation of the number of b).
// Command: `customMaxAbs(num1, num2)` (num1 and num2 are passed to the function customMaxAbs).
// Post-condition: `res = |∣num1∣-∣num2∣| + ∣num2∣` (the result of the work function is the difference of a and b modulo added to module b).


fun customMax(num1: Int, num2: Int): Int {
    return if (num1 > num2) {
        num1
    } else {
        num2
    }
}


fun customAbs(num: Int): Int {
    return if (num >= 0) {
        num
    } else {
        num * -1
    }
}


/**
 * Calculates the maximum modulus of two numbers.
 *
 * @param num1 First number.
 * @param num2 Second number.
 * @return The biggest modulus of two numbers
 */
fun customMaxAbs(num1: Int, num2: Int): Int {
    return customMax(customAbs(num1), customAbs(num2))
}


fun main() {
    println("Result: ${customMaxAbs(1, 3)}") // 3
    println("Result: ${customMaxAbs(-20, -21)}") // 21
    println("Result: ${customMaxAbs(10, -11)}") // 11
}