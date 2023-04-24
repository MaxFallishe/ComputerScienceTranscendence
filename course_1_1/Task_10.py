class PowerSet:
    def __init__(self):
        self.bucket_count = 50_000
        self.buckets = [[] for _ in range(self.bucket_count)]
        self.item_count = 0

    def __iter__(self):
        for bucket in self.buckets:
            for item in bucket:
                yield item

    def __contains__(self, item):
        bucket = self.hash_fun(item)
        return item in bucket

    def hash_fun(self, value):
        return self.buckets[hash(value) % self.bucket_count]

    def size(self):
        return self.item_count

    def put(self, value):
        bucket = self.hash_fun(value)
        if value not in bucket:
            bucket.append(value)
            self.item_count += 1

    def get(self, value):
        bucket = self.hash_fun(value)
        return value in bucket

    def remove(self, value):
        bucket = self.hash_fun(value)
        if value in bucket:
            bucket.remove(value)
            self.item_count -= 1
            return True
        return False

    def intersection(self, set2):
        result = PowerSet()
        for item in self:
            if item in set2:
                result.put(item)
        return result

    def union(self, set2):
        result = PowerSet()
        for item in self:
            result.put(item)
        for item in set2:
            result.put(item)
        return result

    def difference(self, set2):
        result = PowerSet()
        for item in self:
            if item not in set2:
                result.put(item)
        return result

    def issubset(self, set2):
        for item in set2:
            if item not in self:
                return False
        return True
