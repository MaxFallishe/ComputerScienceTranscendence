class NativeDictionary:
    def __init__(self, sz):
        self.size = sz
        self.slots = [None] * self.size
        self.values = [None] * self.size
        self.step = 3

    def hash_fun(self, key):
        return ''.join(f'{ord(i):b}' for i in key).count('1') % self.size

    def is_key(self, key):
        return key in self.slots

    # Outside the theoretical task, of course, it would be necessary to make the hash table dynamically expand
    # or raise an error if the hash table has run out of space
    def put(self, key, value):
        if not self.is_key(None):
            return
        insrt_ind = self.hash_fun(key)
        while self.slots[insrt_ind] != key and self.slots[insrt_ind] is not None:
            insrt_ind = (insrt_ind + self.step) % self.size
        self.slots[insrt_ind], self.values[insrt_ind] = key, value

    def get(self, key):
        ind = self.hash_fun(key)
        while self.slots[ind] != key and self.slots[ind] is not None:
            ind = (ind + self.step) % self.size
        return self.values[ind]
