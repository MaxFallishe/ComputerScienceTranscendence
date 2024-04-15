import random


# create some sort of abstract class but for this example main class
class ParticleOfVoid:
    # init some attributes which every magic particle should have, In this example, we will not focus on using them
    def __init__(self):
        self.particle_size__px = 10
        self.particle_speed__px_sec = 4

    # an example of an operation on an attribute that all inherited classes will have to support
    def slow_down_speed(self) -> None:
        self.particle_speed__px_sec //= 2

    # a static method that is planned to be used for the main demonstrative functionality
    # in this example, each particle will have its own effect on the stuff submitted to the particle
    @staticmethod
    def interact(obj: str) -> None:
        print(f'{obj}, just nothing happened with it')


# the class that inherits from an empty particle, its heir
# class inherited not only the attributes but also the methods of the parent
class FireMagicParticle(ParticleOfVoid):
    def __init__(self):
        super().__init__()  # special python design for easy access to the methods and attributes of the parent

    # overloading the static method to a specific action with an object,
    # in this case, a fiery magic particle burns the object to... ashes :)
    @staticmethod
    def interact(obj: str) -> None:
        print(f'{obj} was burned, oops')


# a similar example with inheritance, everything is the same as with the fire particle
# only the result of the `interact` method is different
class IceMagicParticle(FireMagicParticle):
    def __init__(self):
        super().__init__()

    # this method is polymorphic now
    @staticmethod
    def interact(obj: str) -> None:
        print(f'{obj} was frozen... probably for a hundred years')


# a similar example with inheritance, everything is the same as with the fire particle
# only the result of the `interact` method is different
class AcidMagicParticle(FireMagicParticle):
    def __init__(self):
        super().__init__()

    # this method is polymorphic now
    @staticmethod
    def interact(obj: str) -> None:
        print(f'Your {obj} ...well, there is a hole in it now.')


# an example of a composition using the implementation of a “magic staff”,
# here we use the functionality of particles, but do not inherit from them
class WizardWand:
    def __init__(self):
        self.magic_tier_list = [FireMagicParticle, IceMagicParticle, AcidMagicParticle]

    # get object that exemplar of some Magic particle
    def _get_random_particle(self) -> object:
        return random.choice(self.magic_tier_list)

    # thanks to polymorphism, we have the opportunity to implement this method
    # in any case the .interact() call will work
    def cast_some_stuff(self, target: str) -> None:
        particle = self._get_random_particle()
        particle.interact(target)
