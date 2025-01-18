// (int * int -> int) -> int -> int ->int
let curry f = fun (y: int) (x: int) -> f(y, x) :int

// (int -> int -> int) -> int * int -> int
let uncurry f = function
    | (x:int,y:int) -> f x y :int
