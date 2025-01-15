// 20.3.1
let vat n x=
    match n,x with
    | _ when n < 0 || n > 100 -> 0.0
    | _ -> (float n / 100.0 + 1.0) * x

// 20.3.2
let unvat n x =
    match n with
    | n when n < 0 || n > 100 -> 0.0
    | _ -> x / (float n / 100.0 + 1.0)

// 20.3.3
let rec min f =
    let rec minIter f i =
        if f i = 0 then i
        else minIter f (i+1)
    minIter f 0