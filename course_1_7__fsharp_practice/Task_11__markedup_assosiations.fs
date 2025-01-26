type F =
  | AM
  | PM

type TimeOfDay = { hours : int; minutes : int; f: F }

let transformAmPmToMinutes = function
  | AM -> 0
  | PM -> 12 * 60

let transformTimeOfDay(x: TimeOfDay) =
  transformAmPmToMinutes x.f + x.hours * 60 + x.minutes

let (.>.) x y = (transformTimeOfDay x) > (transformTimeOfDay y)

// Another option to solve:
// let (.>.) x y =
//   (x.f, x.hours, x.minutes) > (y.f, y.hours, y.minutes)