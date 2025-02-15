
// 47.4.1
let f n =
    let refN = ref 1
    let sum = ref 1
    while refN.contents < n do
        refN.contents <- refN.contents + 1
        sum.contents <- sum.contents * refN.contents
    sum.contents


// 47.4.2
let fibo n =
    if n = 1 then 0
    elif n = 2 then 1
    else
        let x1 = ref 0
        let x2 = ref 1
        let refN = ref(n - 2)
        while refN.contents > 0 do
            let mid = x1.contents
            x1.contents <- x2.contents
            x2.contents <- mid + x2.contents
            refN.contents <- refN.contents - 1
        x2.contents
