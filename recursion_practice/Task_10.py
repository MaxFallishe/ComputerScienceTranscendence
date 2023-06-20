import math


def find_meet_chance(num_tournament_members) -> float:
    num_tournament_rounds = int(math.log2(num_tournament_members))
    return find_meet_chance_recursion(num_tournament_rounds, num_tournament_rounds)


def find_meet_chance_recursion(num_tournament_rounds, tournament_round) -> float:
    if tournament_round == 0:
        return 0
    cur_chance = 1 / (2 ** tournament_round - 1) * (0.25 ** (num_tournament_rounds - tournament_round))
    num_prev_rounds_members = [2 ** (num_tournament_rounds - i) for i in range(0, num_tournament_rounds - tournament_round)]
    num_prev_rounds_members = [(i-1-1)/(i-1) for i in num_prev_rounds_members]
    cur_chance *= math.prod(num_prev_rounds_members)
    return cur_chance + find_meet_chance_recursion(num_tournament_rounds, tournament_round-1)
