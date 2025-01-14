open System

// 17.1
let rec pow = function
    | (s,1) -> s
    | (_,n) when n <= 0 -> ""
    | (s,n) -> s + pow(s, n-1)

// 17.2
let rec isIthChar = function
    | (_, n, _) when n < 0 -> false
    | (s, n, _) when n >= (String.length s) -> false
    | (s, n, c) -> s.[n] = c

// 17.3
let rec occFromIth = function
    | (s, n, _) when (n >= String.length s) -> 0
    | (s, n, c) when n < 0 -> occFromIth(s, 0, c) + 0
    | (s, n, c) when s.[n] = c -> occFromIth(s, n+1, c) + 1
    | (s, n, c) -> occFromIth(s, n+1, c) + 0