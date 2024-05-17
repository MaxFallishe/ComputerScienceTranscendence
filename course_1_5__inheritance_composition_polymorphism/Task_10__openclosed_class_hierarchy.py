# Python has a special decorator that allows you to prohibit the redefinition of methods from the class - @final,
# however, it is necessary to understand that if you ignore the rules of this decorator, the code will not throw
# an exception, but only a warning.
import copy
from typing import final


class General(object):
    @final
    def clone(self):
        return copy.deepcopy(self)

    @final
    def copy_to(self, other):
        for attr, value in self.__dict__.items():
            setattr(other, attr, copy.deepcopy(value))


class SomeObject(General):
    def clone(self):
        print("Bad action", self)

