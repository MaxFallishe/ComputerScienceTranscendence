// 49.5.1
let even_seq = Seq.initInfinite (fun i -> (i + 1) * 2)

let rec facSeqFunc n =
    match n with
    | 0 | 1 -> 1
    | _ -> n * facSeqFunc(n-1)

// 49.5.2
let fac_seq = Seq.initInfinite facSeqFunc

let rec seqSeqFunc n =
    match n with
    | 0 -> 0
    | n when (n % 2) <> 0 -> -(n - (n / 2))
    | n -> n / 2

// 49.5.3
let seq_seq = Seq.initInfinite seqSeqFunc