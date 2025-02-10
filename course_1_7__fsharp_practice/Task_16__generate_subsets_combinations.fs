let MakeSetOfSets = fun x -> set( List.map (fun x -> set [x]) x )

let rec multiplySetElements= function
    | [], _ -> set [ ]
    | head :: mainSubset, x -> set [x + set([head])] + multiplySetElements(mainSubset, x)

let rec createOneLvlSubsets (mainSubset : list<int>) (existSet : Set<Set<int>>) k=
    let setOfSets = Set.map (fun x -> multiplySetElements(mainSubset, x) ) existSet
    let setOfSets = Set.fold (fun x y -> x + y) (set []) setOfSets
    let setOfSets = Set.filter (fun x -> (Set.count x) > Set.count(Set.minElement setOfSets)) setOfSets
    match k with
    | 0 -> set [ ]
    | 1 -> existSet
    | _ -> createOneLvlSubsets (mainSubset) (setOfSets) (k-1)

// 42.3
let rec allSubsets n k =
    let startSet = MakeSetOfSets [1 .. n]
    let mainSet = [1 .. n-1] // always startSet Length -1
    createOneLvlSubsets mainSet startSet k