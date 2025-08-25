// Proof of the correctness "customquickSort" function using Hoare triples.
//
// { arr.length >= 0 } customquickSort(arr) { result.length = arr.length AND 0 <= i <= arr.length-2 AND result[i] <= result[i+1] }
//
// The loop { for (i in arr.drop(1))... } invariant:
// I: i ∈ arr AND ∀x ∈ biggerNums, x >= refElement AND ∀y ∈ smallerNums, y < refElement
//
// Initialization:
// At the beginning of the loop, i ∈ arr since the loop iterates exclusively over the values in the set (more precisely, the array) arr, therefore, at the start of the loop
// this invariant is always observed. The invariant "∀x ∈ biggerNums, x >= refElement" and "∀y ∈ smallerNums, y < refElement" also remained true
// at the start of the loop since biggerNums and smallerNums are empty, and this does not contradict the invariant that states that each
// element of the set of biggerNums must be "larger or equal to the " refElement value, similarly for smallerNums with the condition "less than".
//
// Preserving the invariant:
// In each iteration of the loop, all values from arr (not counting the first element) less than refElement are sent to the smallerNums array,
// and values greater than or equal to the biggerNums array. This guarantees parts of the invariant "∀x ∈ biggerNums, x >= refElement" and "∀y ∈ smallerNums, y < refElement".
// In addition, i is not modified in any way, so part of the invariant "I: i ∈ arr" is preserved
//
// Completion:
// No additional actions are performed at the end of the cycle, which means that the proof of the last iteration can be equated to the proof
// of the completion of the cycle. For all iterations, compliance with the invariant was proved in Preserving the invariant, so
// the Completion clause can be considered proven automatically.
//
// The recursion { return customquickSort(smallerNums) + listOf(refElement) + customquickSort(biggerNums) } invariant:
// I: smallerNums.size <= arr.size AND biggerNums.size <= arr.size AND refElement = arr[0]
//
// Initialization:
// At the time of initialization, the size of the biggerNums and smallerNums arrays will always be less than the arr size, since biggerNums
// and smallerNums are empty at the beginning of the recursive function. In addition, the refElement = arr[0] invariant is correct,
// since refElement is assigned only once.
//
// Preserving the invariant:
// Within each iteration of recursion, the invariant "smallerNums.size <= arr.size AND biggerNums.size <= arr.size" is not violated
// since there is only a finite number of values distributed from the arr array, so that sorting occurs correctly,
// also due to this invariant, each subsequent recursive function call it works with a smaller array,
// up to sizes 1 and 0, after which the entire chain of sorted values begins to assemble into a single row.
//
// Completion:
// Recursion begins to unfold when the size of the processed array is 1 or 0. At this stage, all parts of the invariant
// are also preserved.


/**
 * Sort the Int array with quick sort with average complexity O(n log n)
 *
 * @param arr Array of Int numbers
 * @return Sorted array (ascending order)
 */
fun customquickSort(arr: Array<Int>): Array<Int> {
    if (arr.isEmpty()) {
        return emptyArray<Int>()
    }
    val refElement = arr[0]
    if (arr.size == 1) {
        return arrayOf(refElement)
    }

    var biggerNums = arrayOf<Int>()
    var smallerNums = arrayOf<Int>()

    for (i in arr.drop(1)) {
        if (i >= refElement) {
            biggerNums += arrayOf(i)
        } else {
            smallerNums += arrayOf(i)
        }
    }

    return customquickSort(smallerNums) + listOf(refElement) + customquickSort(biggerNums)
}


fun main() {
    println("Result: ${customquickSort(arrayOf(1, 2, 4, 44, 5, 6, 11)).contentToString()}")
    println("Result: ${customquickSort(arrayOf(1, 2, 4, -2, 0, -11, 12)).contentToString()}")
    println("Result: ${customquickSort(arrayOf(1, -1, 2, -2, 3, -3, 99, -99)).contentToString()}")

}