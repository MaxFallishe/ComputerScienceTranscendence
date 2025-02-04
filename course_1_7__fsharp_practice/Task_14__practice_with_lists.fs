// 40.1
let rec sum (p, xs) =
    match p, xs with
    | _, [] -> 0
    | p, head :: xs when p( head ) = true -> head + sum( p, xs )
    | p, _ :: xs -> 0 + sum( p, xs )


// 40.2.1
let rec count (xs, n) =  // [x1; x2; ...; xn] has logic for elements like x1 <= x2 <= ... <= xn
    match xs, n with
    | [], n -> 0
    | head :: xs, n when head > n -> 0
    | head :: xs, n when head = n -> 1 + count(xs, n)
    | head :: xs, n -> 0 + count(xs, n)

// 40.2.2
let rec insert (xs, n) =  // [x1; x2; ...; xn] has logic for elements like x1 <= x2 <= ... <= xn
    match xs, n with
    | [], n -> [n]
    | head :: xs, n when head > n -> [n] @ [head] @ xs
    | head :: xs, n when head <= n -> [head] @ insert(xs, n)

// 40.2.3
let rec intersect (xs1, xs2) =  // [x1; x2; ...; xn] has logic for elements like x1 <= x2 <= ... <= xn
    match xs1, xs2 with
    | xs1, xs2 when xs1 = [] || xs2 = [] -> []
    | head1 :: xs1, head2 :: xs2 when head1 = head2 -> [head1] @ intersect(xs1, xs2)
    | head1 :: xs1, head2 :: xs2 when head1 > head2 -> intersect([head1] @ xs1, xs2)
    | head1 :: xs1, head2 :: xs2 when head1 < head2 -> intersect(xs1, [head2] @ xs2)


// 40.2.4
let rec plus (xs1, xs2) =  // [x1; x2; ...; xn] has logic for elements like x1 <= x2 <= ... <= xn
    match xs1, xs2 with
    | xs1, [] -> xs1
    | [], xs2 -> xs2
    | head1 :: xs1, head2 :: xs2 when head1 <= head2 -> [head1] @ plus( xs1, [head2] @ xs2)
    | head1 :: xs1, head2 :: xs2 when head1 > head2 ->  [head2] @ plus( [head1] @ xs1, xs2)

// 40.2.5
let rec minus (xs1, xs2) =  // [x1; x2; ...; xn] has logic for elements like x1 <= x2 <= ... <= xn
    match xs1, xs2 with
    | xs1, [] -> xs1
    | [], xs2 -> []
    | head1 :: xs1, head2 :: xs2 when head1 < head2 -> [head1] @ minus(xs1, [head2] @ xs2 )
    | head1 :: xs1, head2 :: xs2 when head1 = head2 -> [] @ minus(xs1, [head2] @ xs2)
    | head1 :: xs1, head2 :: xs2 when head1 > head2 -> [] @ minus([head1] @ xs1, xs2)

// 40.3.1
let rec smallest = function // always non empty list as input
    | [x] -> x
    | head1 :: (head2 :: lst) when head1 <= head2 -> smallest ( [head1] @ lst )
    | head1 :: (head2 :: lst) when head1 > head2 ->  smallest ( [head2] @ lst )

// 40.3.2
let rec delete (n, xs) =
    match n, xs with
    | n, [] -> []
    | n, head :: xs when head = n -> xs
    | n, head :: xs -> [head] @ delete(n, xs)

// 40.3.3
let rec sort = function
    | [] -> []
    | xs -> [smallest(xs)] @ sort(delete( smallest(xs), xs))


let rec revrev2 = function
    | [] -> []
    | head :: xs -> revrev2 xs @ [head]

// 40.4
let rec revrev = function
    | [] -> []
    | head :: xs -> revrev xs @ [revrev2 head]
