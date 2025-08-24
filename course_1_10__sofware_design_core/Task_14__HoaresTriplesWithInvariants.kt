// Proof of the correctness "customMaxAbs" function using Hoare triples.
//
// {arr.length > 0} customFindMax(arr) {result = max(arr)}
//
// The loop invariant:
// I: i ∈ arr AND maxElement ∈ arr AND maxElement >= i
//
// Initialization:
// Before the start of the first iteration of the loop: maxElement = arr[0] AND i = arr[0]
// Checking the invariant: i ∈ arr AND maxElement ∈ arr AND maxElement >= i
// The invariant is true at the beginning, since both i AND maxElement are equal to arr[0], which means they belong to arr,
// also arr[0] = arr[0], so it is correct to say that maxElement >= i
//
// Preserving the invariant:
// 1. The loop passes only through the elements of arr and, except for the beginning of a new iteration, "i" never changes => it always means i ∈ arr
// 2. maxElement is assigned and reassigned only in two places - "var maxElement = arr[0]" and "maxElement = i".
//    In the first case, the invariant i ∈ arr is directly observed, in the second case, we assign the value i to the variable maxElement,
//    and from proof 1, we know that i ∈ arr, which means that maxElement ∈ arr is always present.
// 3. The condition maxElement >= i can be proved by taking an arbitrary iteration of the loop with i, where there are two cases
//    "i < maxElement" and "i > maxElement". In the case of "i <= maxElement", no actions are performed in the body of the loop and "maxElement >=i".
//    In the case of "i > maxElement", the assignment "maxElement = i" occurs, which means that the condition "maxElement >=i" is exactly met.
//
// Completion:
// After iterating through all the values in the arr array by proving that the invariant is preserved at each iteration of the loop "maxElement >= i",
// it can be argued that {result = max(arr)}



/**
 * Calculates the maximum number of Int array.
 *
 * @param arr Array of int numbers
 * @return The biggest int number in array
 */
fun customFindMax(arr: Array<Int>): Int {
    require(arr.isNotEmpty()) { "Input array cannot be empty." }
    var maxElement = arr[0]
    for (i in arr) {
        if (i > maxElement) {
            maxElement = i
        }
    }
    return maxElement
}


fun main() {
    println("Result: ${customFindMax(arrayOf(1,2,4,44,5,6,11))}")

}