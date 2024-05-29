# In cases where some characteristic is needed mainly for statistics, or to support specific logic, it can be
# formatted as an attribute. However, if such a characteristic implies different logic depending on its value
# (conditional if else chains), it is better to create a separate class for each value

class Martian:
    def __init__(self):
        self.home_planet: str = "Mars"

    def talk(self):
        print(f"blop-blop, I'm from {self.home_planet}")

    @staticmethod
    def walk():
        print("This creature just walking")


class Venusian(Martian):
    def __init__(self):
        super().__init__()
        self.home_planet: str = "Venus"

    @staticmethod
    def eye_lasers_shot():
        print("Poof")


class Plutonian(Martian):
    def __init__(self):
        super().__init__()
        self.home_planet: str = "Pluto"

    def talk(self):
        print(f"c#1!* (#(@$, I'm from {self.home_planet}")
