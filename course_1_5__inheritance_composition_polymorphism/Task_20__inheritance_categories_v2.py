# Variation inheritance Example
class Sakura:
    def __init__(self):
        self.leaves_count: int = 9000
        self.bloom_month: str = 'march'

    @staticmethod
    def bloom():
        print('There was a flowerin...')


class FallingOffSakura(Sakura):
    def __init__(self):
        super().__init__()

    def bloom(self) -> None:
        self.leaves_count *= 0.9
        print('There was a flowerin...')


class FluffySakura(Sakura):
    def __init__(self):
        super().__init__()

    def bloom(self) -> None:
        self.leaves_count *= 2
        print('There was a flowerin...')


# Reification inheritance Example
class AbstractChat:
    def __init__(self, max_users_num: int):
        self.max_users_num = max_users_num

    def post_message(self, user_name, msg: str) -> None:
        raise NotImplementedError


class HouseChat(AbstractChat):
    def __init__(self):
        super().__init__(max_users_num=120)
        print('House Chat activated')

    def post_message(self, user_name, msg: str) -> None:
        print(f'{user_name}: {msg}')


# Structure inheritance Example
class KindHeart:
    ...


class Human:
    ...


class RobinHood(Human, KindHeart):  # adding a new, autonomous, formal property is a KindHeart
    ...

