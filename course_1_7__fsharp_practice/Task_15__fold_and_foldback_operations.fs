// 41.4.1
let rec list_filter f xs =
    List.foldBack (fun head acc -> if f head then head :: acc else acc) xs []


// 41.4.2
let sum (p, xs) =
    List.fold (fun x y -> if p y then x + y else x) 0 xs

// 41.4.3
let revrev = function
    | xs -> List.fold (fun x y ->  [List.rev y] @ x) [] xs