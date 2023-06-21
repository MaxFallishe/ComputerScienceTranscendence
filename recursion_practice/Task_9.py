def generate_parenthesis_front(n: int) -> list[str]:
    return generate_parenthesis_back([], [], 0, 0, n)


def generate_parenthesis_back(answer, cur_string, left_count, right_count, n):
    if len(cur_string) == 2 * n:
        answer.append("".join(cur_string))
        return
    if left_count < n:
        cur_string.append("(")
        generate_parenthesis_back(answer, cur_string, left_count + 1, right_count, n)
        cur_string.pop()
    if right_count < left_count:
        cur_string.append(")")
        generate_parenthesis_back(answer, cur_string, left_count, right_count + 1, n)
        cur_string.pop()
    return answer
