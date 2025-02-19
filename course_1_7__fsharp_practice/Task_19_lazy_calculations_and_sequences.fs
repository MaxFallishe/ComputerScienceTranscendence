// 48.4.1
let rec fibo1 n n1 n2 =
    if n > 0 then fibo1 (n-1) (n1+n2) n1 else n2

// 48.4.2
let rec fibo2 n (c: int -> int) =
    if n <= 1 then c n
    else fibo2 (n-1) (fun f1 -> fibo2 (n-2) (fun f2 -> c (f1 + f2)))


// 48.4.3
let rec bigList n k =
  let rec f x a =
   if x = 0 then a
   else f (x-1) (1 :: a)
  f n []