// (int * int -> int) -> int -> int ->int
let curry f = fun y x -> f(y, x)

// (int -> int -> int) -> int * int -> int
let uncurry f = function
    | (x,y) -> f x y
