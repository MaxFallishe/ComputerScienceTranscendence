from typing import final, Self
from copy import deepcopy
import pickle


class General[T]:
    COPY_NIL = 0  # copy_to() not called yet
    COPY_OK = 1  # last copy_to() call completed successfully
    COPY_ATTR_ERR = 2  # other object have no attribute copied from this object

    def __get_status_fields(self) -> set:
        fields = set(attr for attr in dir(self)
                     if attr.endswith('status'))
        return fields

    def __init__(self, *args, **kwargs):
        self._copy_status = self.COPY_NIL

    # commands:
    @final
    def copy_to(self, other: T) -> None:
        """Deep-copy of attributes of **self** to **other** with
        ignoring status-attributes."""
        status_fields = self.__get_status_fields()
        copy_attrs = filter(lambda a: a not in status_fields,
                            dir(self))

        if not all((hasattr(other, a) for a in copy_attrs)):
            self._copy_status = self.COPY_ATTR_ERR
            return

        for attr in copy_attrs:
            value = deepcopy(getattr(self, attr))
            setattr(other, attr, value)

        self._copy_status = self.COPY_OK

    # requests:
    @final
    def __eq__(self, other: T) -> bool:
        return self.__dict__ == other.__dict__

    @final
    def __repr__(self) -> str:
        s = f'<"{self.__class__.__name__}" instance' \
            f' (id={id(self)})>'
        return s

    @final
    def clone(self) -> T:
        clone = deepcopy(self)
        return clone

    @final
    def serialize(self) -> bytes:
        bs = pickle.dumps(self)
        return bs

    @final
    @classmethod
    def deserialize(cls, bs: bytes) -> T:
        instance = pickle.loads(bs)
        return instance

    # method statuses requests:
    @final
    def get_copy_status(self) -> int:
        """Return status of last copy_to() call:
        one of the COPY_* constants."""
        return self._copy_status

    @final
    def assignment_attempt(self, target: T) -> None:
        """Attempt to assign self to target if types are compatible."""
        target = None
        if isinstance(self, type(target)):
            target = deepcopy(self)


class Any[T](General):
    pass


class Vector[T](Any):
    def __init__(self, nums: list[int | Self]):
        super().__init__()
        self.nums_arr = nums

    def __add__(self, other) -> Self:
        if not isinstance(other, Vector):
            print('Folding is possible only with the Vector type object')
        if len(self) != len(other):
            return None
        return Vector([self[i] + other[i] for i in range(len(self))])

    def __len__(self) -> int:
        return len(self.nums_arr)

    def __getitem__(self, index) -> T:
        return self.nums_arr[index]

    def __str__(self) -> str:
        return str(self.nums_arr)


# a = Vector([1, 11, 30, 1])
# b = Vector([2, 11, 1, 1])
# c = Vector([3, 11, 30, 1])
# v = Vector([4, 11, 1, 1])
#
# f = Vector([a, b])
# g = Vector([c, v])
#
# cc = f + g
# vv = cc + cc
# print(vv[0])
