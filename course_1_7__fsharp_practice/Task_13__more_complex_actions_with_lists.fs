
// 39.1
let rec rmodd = function
    | [ ] -> []
    | [_] -> []
    | _ :: (head2 :: tail) -> [head2] @ rmodd tail

let rec lmodd = function
    | [ ] -> []
    | [x] -> [x]
    | head1 :: (_ :: tail) -> [head1] @ lmodd tail

// 39.2
let rec del_even = function
    | [ ] -> []
    | head1 :: tail when head1 % 2 = 0 -> [] @ del_even tail
    | head1 :: tail -> [head1] @ del_even tail


// 39.3
let rec multiplicity x xs =
    match x, xs with
    | _, [] -> 0
    | x, head :: xs when head = x -> 1 + multiplicity x xs
    | x, head :: xs-> 0 + multiplicity x xs


// 39.4
let rec split = function
    | xs -> lmodd xs, rmodd xs


// 39.5
let rec zip (xs1,xs2) =
    match xs1, xs2 with
    | [], [] -> []
    | xs1, xs2 when List.length xs1 <> List.length xs2 -> failwith "Both list should have same amount of elements"
    | head1 :: xs1, head2 ::xs2 -> [(head1, head2)] @ zip(xs1, xs2)
