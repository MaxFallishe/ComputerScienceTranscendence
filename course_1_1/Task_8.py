class HashTable:
    def __init__(self, sz, stp):
        self.size = sz
        self.step = stp
        self.slots = [None] * self.size

    def hash_fun(self, value):
        return ''.join(f'{ord(i):b}' for i in value).count('1') % self.size


    def seek_slot(self, value):
        init_hash_ind = self.hash_fun(value)
        while None in self.slots:
            if self.slots[init_hash_ind] is None or self.slots[init_hash_ind] == value:
                return init_hash_ind
            init_hash_ind = (init_hash_ind + self.step) % self.size
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
#
#
# hash_t = HashTable(19, 3)
# print(hash_t.hash_fun('a'))
# print(hash_t.hash_fun('b'))
#
# print(hash_t.put('a'))
# print(hash_t.put('b'))
# print(hash_t.put('a'))
# print("FIND", hash_t.find('a'))
# print("FIND", hash_t.find('b'))
# print(hash_t.slots)
