import math


def GenerateBBSTArray(a):
    a.sort()
    mid_el_ind = len(a)//2
    indexes = [mid_el_ind]
    step = 2 ** (int(math.log2(len(a))) - 1)
    return generate_bbst_arr(a, indexes, step)


def generate_bbst_arr(a, indexes, step):
    if len(indexes) == len(a) + 1:
        return []
    answer = [a[i] for i in indexes]
    indexes = [[i-step, i+step] for i in indexes]
    indexes = sum(indexes, [])
    step = step // 2
    return answer + generate_bbst_arr(a, indexes, step)
