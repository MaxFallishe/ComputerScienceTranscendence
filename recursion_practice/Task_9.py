def generate_parenthesis(n: int) -> list[str]:
    if n == 0:
        return [""]
    answers = []
    for left_count in range(n):
        for left_string in generate_parenthesis(left_count):
            for right_string in generate_parenthesis(n - 1 - left_count):
                answers.append(f'({left_string}){right_string}')
    return answers
