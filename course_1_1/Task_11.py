class BloomFilter:
    def __init__(self, f_len):
        self.filter_len = f_len
        self.filter = 0

    def hash1(self, str1):
        result = 0
        random_number = 17  # set by the task condition
        for c in str1:
            code = ord(c)
            result = (result * random_number + code) % self.filter_len
        return result

    def hash2(self, str1):
        result = 0
        random_number = 223  # set by the task condition
        for c in str1:
            code = ord(c)
            result = (result * random_number + code) % self.filter_len
        return result

    def add(self, str1):  # the degree that the bit denotes grows linearly from right to left
        self.filter |= 2 ** self.hash1(str1)
        self.filter |= 2 ** self.hash2(str1)

    def is_value(self, str1):
        verification_mask = 0
        verification_mask |= 2 ** self.hash1(str1)
        verification_mask |= 2 ** self.hash2(str1)
        return verification_mask & self.filter == verification_mask
