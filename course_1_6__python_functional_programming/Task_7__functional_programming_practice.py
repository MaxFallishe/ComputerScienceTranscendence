# A rectangular territory of size NxM contains square areas indexed from 1 to N (rows) and 1 to M (columns).
#
# On day 1, paratroopers land in M specified areas represented by coordinates (x1, y1), (x2, y2), ..., (xl, yl).
# These areas are considered captured. On subsequent days, they capture all neighboring areas that are adjacent
# vertically and horizontally to the captured areas from the previous day. This process continues until all areas
# are under control.
#
# The task is to determine on which day the entire territory will be captured.
#
# The function accepts:
#
# - N: the number of rows.
# - M: the number of columns.
# - L: the number of initial landing areas.
# - battalion: an array of integers containing 2*L elements. Each pair of elements (indexed from zero) represents
# the coordinates of the landing areas, where elements at even indices correspond to row coordinates (N) and elements
# at odd indices correspond to column coordinates (M).
#
# Note that landing area coordinates may be duplicated.

from collections import Counter
from functools import reduce

from pymonad.operators.maybe import Just
from pymonad.tools import curry


def ConquestCampaign(N: int, M: int, L: int, batallion: Just) -> int:
    if batallion.bind(is_conquered(N, M)):
        return 1
    batallion = (
        batallion
        .bind(stright_expansion)
        .bind(get_batallion_within_borderd(N, M))
        .bind(get_batallion_without_duplicates)
    )
    return ConquestCampaign(N, M, L, batallion) + 1


@curry(3)
def is_conquered(N: int, M: int, batallion: list[int]) -> bool:
    odd_coordinates = batallion[0::2]
    even_coordinates = batallion[1::2]
    if not len(odd_coordinates) >= N * M or not len(even_coordinates) >= N * M:
        return False
    if not all(map(lambda x: 1 if x == M else 0, Counter(odd_coordinates).values())):
        return False
    if not all(map(lambda x: 1 if x == N else 0, Counter(even_coordinates).values())):
        return False
    return True


@curry(1)
def get_batallion_without_duplicates(batallion: list[int]) -> Just:
    odd_coordinates = batallion[0::2]
    even_coordinates = batallion[1::2]
    zipped_coordinates = list(zip(odd_coordinates, even_coordinates))
    coordinates_set = map(lambda x: list(x), set(zipped_coordinates))
    unduplicated_batallion = reduce(lambda x, y: x + y, coordinates_set)
    return Just(unduplicated_batallion)


@curry(3)
def get_batallion_within_borderd(N: int, M: int, batallion: list[int]) -> Just:
    odd_coordinates = batallion[0::2]
    even_coordinates = batallion[1::2]
    zipped_coordinates = list(zip(odd_coordinates, even_coordinates))
    filtered_coordinates = filter(lambda x: N >= x[0] >= 1 and M >= x[1] >= 1, zipped_coordinates)
    result_batallion = list(reduce(lambda x, y: x + y, filtered_coordinates))
    return Just(result_batallion)


@curry(1)
def stright_expansion(batallion: list[int]) -> Just:
    odd_coordinates = batallion[0::2]
    even_coordinates = batallion[1::2]
    zipped_coordinates = list(zip(odd_coordinates, even_coordinates))
    expansioned_batallion = map(
        lambda x: [x[0] + 0, x[1] + 0,
                   x[0] + 1, x[1] + 0,
                   x[0] - 1, x[1] + 0,
                   x[0] + 0, x[1] + 1,
                   x[0] + 0, x[1] - 1],
        zipped_coordinates)
    result_batallion = list(reduce(lambda x, y: x + y, expansioned_batallion))
    return Just(result_batallion)
