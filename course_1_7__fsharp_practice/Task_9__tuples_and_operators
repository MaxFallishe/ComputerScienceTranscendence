let bronze2silverCoinsRate = 12
let silver2goldCoinsRate = 20

let rpgMoney (goldCoins,silverCoins, bronzeCoins) =
    (
        goldCoins + (silverCoins + bronzeCoins / bronze2silverCoinsRate) / silver2goldCoinsRate,
        (silverCoins + bronzeCoins / bronze2silverCoinsRate) % silver2goldCoinsRate,
        bronzeCoins % bronze2silverCoinsRate
    )

let bronzeMoney (goldCoins,silverCoins, bronzeCoins) =
    (
        0,
        0,
        bronzeCoins + (bronze2silverCoinsRate * silverCoins) + (silver2goldCoinsRate * bronze2silverCoinsRate * goldCoins)
    )

let rpgMoneyAdd ((a, b, c), (e, f, g)) =
    rpgMoney (a + e, b + f, c + g)

let rpgMoneySubtraction ((a, b, c), (e, f, g)) =
    rpgMoney (a - e, b - f, c - g)

let complexNumbersAdd((a, b), (c, d)) =  (a + c, b + d)

let complexNumbersSubtraction((a, b), (c, d)) =  (a - c, b - d)

let complexNumbersMultiplication ((a: float, b: float), (c: float, d: float)) =
    (a * c - b * d, b * c + a * d)

let complexNumbersDivide((a, b), (c, d)) =
    let denominator = c ** 2.0 + d ** 2.0
    complexNumbersMultiplication ((a, b), (c / denominator, -d / denominator))


// 23.4.1
let (.+.) x y = rpgMoneyAdd(x, y)
let (.-.) x y = rpgMoneySubtraction(bronzeMoney(x), bronzeMoney(y))

// 23.4.2
let (.+) x y = complexNumbersAdd(x, y)
let (.-) x y = complexNumbersSubtraction(x, y)
let (.*) x y = complexNumbersMultiplication(x, y)
let (./) x y = complexNumbersDivide(x, y)