import math


class NativeCache:
    def __init__(self, sz):
        if sz < 1:
            raise ValueError("The size of cash must be greater than or equal to 1.")
        self.size = sz
        self.slots = [None] * self.size
        self.values = [None] * self.size
        self.hits = [0] * self.size
        self.step = self.__find_coprime()

    def __contains__(self, item):
        return item in self.slots

    def hash_fun(self, key):
        return ''.join(f'{ord(i):b}' for i in key).count('1') % self.size

    def put(self, key, value):
        if None not in self:
            min_hit_ind = self.hits.index(min(self.hits))
            self.slots[min_hit_ind], self.values[min_hit_ind], self.hits[min_hit_ind] = key, value, 0
            return
        insrt_ind = self.hash_fun(key)
        while self.slots[insrt_ind] != key and self.slots[insrt_ind] is not None:
            insrt_ind = (insrt_ind + self.step) % self.size
        self.slots[insrt_ind], self.values[insrt_ind], self.hits[insrt_ind] = key, value, 0

    def get(self, key):
        ind = self.hash_fun(key)
        if key not in self.slots:
            return None
        while self.slots[ind] != key and self.slots[ind] is not None:
            ind = (ind + self.step) % self.size
        if self.values[ind]:
            self.hits[ind] += 1
        return self.values[ind]

    def __find_coprime(self):
        ind = 2
        while True:
            gcd = math.gcd(self.size, ind)
            if gcd == 1:
                return ind
            ind += 1
