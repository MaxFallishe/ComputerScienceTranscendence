// 50.2.1
let fac_seq = seq {
    let mutable counter = 0
    let mutable sum = 1
    while true do
        yield sum
        counter <- counter + 1
        sum <- sum * counter
}


// 50.2.2
let seq_seq = seq {
    let mutable counter = 0
    while true do
        if (counter % 2) = 0 then yield (counter / 2)
        else yield -(counter - (counter / 2))
        counter <- counter + 1
}