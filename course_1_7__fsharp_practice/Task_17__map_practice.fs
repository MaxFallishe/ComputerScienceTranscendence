// 43.3
let try_find key m =
    if Map.containsKey key m = false then None else Some (Map.find key m)