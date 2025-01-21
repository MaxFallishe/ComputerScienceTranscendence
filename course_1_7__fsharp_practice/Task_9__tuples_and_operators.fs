let bronze2silverCoinsRate = 12
let silver2goldCoinsRate = 20

let rpgMoney (goldCoins,silverCoins, bronzeCoins) =
    (
        goldCoins + (silverCoins + bronzeCoins / bronze2silverCoinsRate) / silver2goldCoinsRate,
        (silverCoins + bronzeCoins / bronze2silverCoinsRate) % silver2goldCoinsRate,
        bronzeCoins % bronze2silverCoinsRate
    )


// 23.4.1
let (.+.) (a, b, c) (e, f, g)= rpgMoney (a + e, b + f, c + g)
let (.-.) (a, b, c) (e, f, g) = rpgMoney(
    0,
    0,
    c + (bronze2silverCoinsRate * b) + (silver2goldCoinsRate * bronze2silverCoinsRate * a)
    - (g + (bronze2silverCoinsRate * f) + (silver2goldCoinsRate * bronze2silverCoinsRate * e))
)

// 23.4.2
let (.+) (a, b) (c, d) = (a + c, b + d)
let (.-) (a, b) (c, d) = (a-c, b-d)
let (.*) (a, b) (c, d) = (a * c - b * d, b * c + a * d)
let (./) (a, b) (c, d) = (a, b) .* (c / (c * c + d * d), -d / (c * c + d * d))

// Another reealization for rpgMoney calculator
// let rec iter = function
//     | (a, b, c) when c > 11 -> iter (a, b + (c / 12), c % 12)
//     | (a, b, c) when b > 19 -> iter (a + (b / 20), b % 20, c)
//     | (a, b, c) when c < 0 && b > 0 -> iter (a, b - 1, c + 12)
//     | (a, b, c) when b < 0 && a > 0 -> iter (a - 1, b + 20, c)
//     | (a, b, c) when c < 0 && a > 0 -> iter (a - 1, b + 19, c + 12)
//     | (a, b, c) -> (a, b, c)
//
// let (.+.) (a, b, c) (x, y, z) = iter (a + x, b + y, c + z)
// let (.-.) (a, b, c) (x, y, z) = iter (a - x, b - y, c - z)