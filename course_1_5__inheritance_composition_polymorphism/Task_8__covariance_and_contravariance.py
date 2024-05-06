# Covariant typing is the ability to associate, as you descend through the hierarchy, the attribute types of a class
# with the type of the current object without explicitly redefining the logic (field type definitions are set relative,
# not absolute, relative to the current type).
#
# Below is an example of covariance on python:
class AmmoPistol:
    @staticmethod
    def boom():
        print('Pistol .*boom*.')


class AmmoShotgun:
    @staticmethod
    def boom():
        print('ShotGun >* boom *<')


class Pistol:
    def __init__(self, ammo: type):
        self.ammo = ammo

    def fire(self) -> None:
        ammo = self.ammo()
        ammo.boom()


class Shotgun(Pistol):
    def __init__(self, ammo: type):
        super().__init__(ammo)


weapon_box = [Pistol(AmmoPistol), Shotgun(AmmoShotgun), Shotgun(AmmoShotgun)]
list(map(lambda x: x.fire(), weapon_box))
# Output:
# Pistol .*boom*.
# ShotGun >* boom *<
# ShotGun >* boom *<


# The opposite approach is covariance - contravariance, when the inheritance scheme is reversed.
# The demo is below:
class SniperRifle:
    def __init__(self, shot_distance__metres: int):
        self.shot_distance__metres = shot_distance__metres


class RealyLongDistanceSniperRifle(SniperRifle):
    def __init__(self, shot_distance__metres):
        super().__init__(shot_distance__metres*2)


def test_sniper_rifle(obj) -> None:
    print(f'A successful shot at a distance {obj.shot_distance__metres}')


RL4000 = SniperRifle(100)
MG45F = RealyLongDistanceSniperRifle(200)

test_sniper_rifle(RL4000)  # A successful shot at a distance 100 metres
test_sniper_rifle(MG45F)  # A successful shot at a distance 400 metres
