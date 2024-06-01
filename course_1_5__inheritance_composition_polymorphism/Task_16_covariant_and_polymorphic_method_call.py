from typing import TypeVar, Generic


# Polymorphic function call example (block of code below) ↓
class HomoSapiens:
    def is_exist(self) -> bool:
        raise NotImplementedError()


class HomoSapiensSapiens(HomoSapiens):
    def is_exist(self) -> bool:
        return True


class HomoSapiensIdaltu(HomoSapiens):
    def is_exist(self) -> bool:
        return False


def is_homo_sapiens_alive(homo_sapiens: HomoSapiens):
    return homo_sapiens.is_exist()


idaltu = HomoSapiensIdaltu()
sapiens = HomoSapiensSapiens()

print(is_homo_sapiens_alive(sapiens))  # Output: True
print(is_homo_sapiens_alive(idaltu))  # Output: False

# Covariance function call example (block of code below) ↓
octopus = TypeVar('octopus', covariant=True)


class Octopus(Generic[octopus]):
    def to_be_scared(self):
        print('He sailed away in a sad mood :(')


class CoconutOctopus(Octopus):
    def to_be_scared(self):
        print('He hid in his favorite coconut')


def spook(octo_friend: Octopus):
    return octo_friend.to_be_scared()


octo1: Octopus = Octopus()
octo2: CoconutOctopus = CoconutOctopus()

spook(octo1)
spook(octo2)
