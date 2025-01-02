module ConsoleApplication1.Script2
open System

let g n = n + 5 // int -> int

let gg = fun n -> n + 5 // int -> int

let h (x:float,y:float) :float =
    Math.Sqrt(x**2.0 + y**2.0)
