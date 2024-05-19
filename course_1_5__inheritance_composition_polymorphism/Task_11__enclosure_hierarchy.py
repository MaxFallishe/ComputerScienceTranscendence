# An example of a small hierarchy, using ready-made General and Any, with the class None closed at the bottom.
# Also, an example of the polymorphic use of Void.

import copy
import json


class General(object):
    def clone(self):
        return copy.deepcopy(self)

    def copy_to(self, other):
        for attr, value in self.__dict__.items():
            setattr(other, attr, copy.deepcopy(value))

    def compare(self, other):
        return self.__dict__ == other.__dict__

    def serialize(self):
        try:
            return json.dumps(self.__dict__, default=str)
        except TypeError as e:
            return str(e)

    def deserialize(self, data):
        for key, value in json.loads(data).items():
            setattr(self, key, value)

    def __str__(self):
        return f"{self.__class__.__name__}({self.__dict__})"

    def is_instance_of(self, cls):
        return isinstance(self, cls)

    def get_type(self):
        return self.__class__


class Any(General):
    pass


class Int(Any):
    pass


class Boolean(Any):
    pass


class Void(Int, Boolean):
    pass


def is_void_item(item: General) -> None:
    if isinstance(item, Void):
        print("Item is of type Void. No operation performed.")
        return
    print(f"Processing item of type {item.get_type().__name__}")
