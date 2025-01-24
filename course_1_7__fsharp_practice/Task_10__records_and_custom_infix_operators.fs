type TimeOfDay = { hours: int; minutes: int; f: string }
let (.>.) x y =
    if x.f = "AM" && y.f = "PM" then
        false
    elif x.f = "PM" && y.f = "AM" then
        true
    else
        if x.hours <> y.hours then
            x.hours > y.hours
        else
            x.minutes > y.minutes