// 16.1
let notDivisible = function
    | (n, m) -> m % n = 0


let rec isNotDivisibleInSeq = function
    | n, m when n = m -> true
    | n, m -> isNotDivisibleInSeq(n+1, m) && not (notDivisible(n, m))


// 16.2
let  prime  = function
    | n when n <= 1 -> false
    | n when n = 2 -> true
    | n -> isNotDivisibleInSeq(2, n)
