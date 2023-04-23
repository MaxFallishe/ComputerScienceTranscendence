import random
from typing import *


class HashTable:
    def __init__(self, sz, stp):
        self.hash_size = sz
        self.hash_step = stp
        self.slots = [None] * self.hash_size

    def hash_fun(self, value):
        return ''.join(f'{ord(i):b}' for i in value).count('1') % self.hash_size


    def seek_slot(self, value):
        init_hash_ind = self.hash_fun(value)
        while None in self.slots:
            if self.slots[init_hash_ind] is None or self.slots[init_hash_ind] == value:
                return init_hash_ind
            init_hash_ind = (init_hash_ind + self.hash_step) % self.hash_size
        return None

    def put(self, value):
        insrt_ind = self.seek_slot(value)
        if insrt_ind is not None:
            self.slots[insrt_ind] = value
        return insrt_ind


    def find(self, value):
        for slot_ind in range(len(self.slots)):
            if self.slots[slot_ind] == value:
                return slot_ind
        return None


class PowerSet(HashTable):
    def __init__(self, hash_size=100_000, hash_step=3):
        super().__init__(hash_size, hash_step)

    def size(self) -> int:
        return len([slot for slot in self.slots if slot is not None])

    def seek_slot(self, value: str) -> Optional[int]:
        init_hash_ind = self.hash_fun(value)
        while None in self.slots and self.slots[init_hash_ind] != value:
            if self.slots[init_hash_ind] is None or self.slots[init_hash_ind] == value:
                return init_hash_ind
            init_hash_ind = (init_hash_ind + self.hash_step) % self.hash_size
        return None

    def put(self, value: str) -> None:
        insrt_ind = self.seek_slot(value)
        if insrt_ind is not None:
            self.slots[insrt_ind] = value

    def get(self, value: str) -> bool:
        return value in self.slots

    def remove(self, value: str) -> bool:
        init_hash_ind = self.hash_fun(value)
        while self.slots[init_hash_ind] != value:
            if self.slots[init_hash_ind] is None:
                return False
            init_hash_ind = (init_hash_ind + self.hash_step) % self.hash_size
        self.slots[init_hash_ind] = None
        return True

    def intersection(self, set2: 'PowerSet') -> 'PowerSet':
        intersec_pset = PowerSet()
        [intersec_pset.put(slot) for slot in self.slots if slot in set2.slots and slot is not None]
        return intersec_pset

    def union(self, set2: 'PowerSet') -> 'PowerSet':
        union_pset = PowerSet()
        union_pset.slots = self.slots
        [union_pset.put(slot) for slot in set2.slots if slot is not None]
        return union_pset

    def difference(self, set2: 'PowerSet') -> 'PowerSet':
        diff_pset = PowerSet()
        [diff_pset.put(slot) for slot in self.slots if slot not in set2.slots and slot is not None]
        return diff_pset

    def issubset(self, set2: 'PowerSet') -> bool:
        if self.size() == 0:
            return False
        for slot in [i for i in set2.slots if i is not None]:
            if slot not in self.slots:
                return False
        return True
