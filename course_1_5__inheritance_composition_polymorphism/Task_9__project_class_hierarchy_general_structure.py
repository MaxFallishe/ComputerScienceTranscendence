# Despite the fact that for users of the type system application in the project, each class should ideally be flat,
# inside the project all classes should also ideally be combined into one hierarchy.
#
# At the head of this hierarchy is the most basic, abstract (non-instantiating) class (for example, General),
# containing a fundamental set of operations:
# * copying an object
# * cloning an object
# * object comparison (including the deep version);
# * serialization/deserialization
# * printing
# * type check
# * getting the real object type

import copy
import json

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
