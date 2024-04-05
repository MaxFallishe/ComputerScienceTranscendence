class BloomFilter[T]:
    ADD_NIL = 0  # метод add() ещё не вызывался
    ADD_OK = 1  # метод add() успешно вставил значение в Фильтр Блюма
    ADD_ERR = 2  # метод put() был вызван когда превышен лимит элементов в Фильтре Блюма

    # КОНСТРУКТОР:
    # постусловие: создан пустой Фильтр Блюма
    def __init__(self):
        self._bloom_filter: int = 0
        self._filter_max_size: int = 32
        self._filter_max_capacity: int = 10
        self._filter_capacity: int = 0

        self._add_status = self.ADD_NIL

    # КОМАНДЫ:
    # предусловие: в фильтре имеются свободные слоты (capacity)
    # постусловие: в фильтр добавлено новое значение
    def add(self, value: str) -> None:
        if self._filter_capacity >= self._filter_max_capacity:
            self._add_status = self.ADD_ERR
            return
        self._bloom_filter |= 2 ** self._hash1(value)
        self._bloom_filter |= 2 ** self._hash2(value)
        self._filter_capacity += 1
        self._add_status = self.ADD_OK

    # ЗАПРОСЫ:
    def _hash1(self, value: str) -> int:
        RANDOM_INT = 17
        result = 0
        for c in value:
            result = (result * RANDOM_INT + ord(c)) % self._filter_max_size
        return result

    def _hash2(self, value: str) -> int:
        RANDOM_INT = 223
        result = 0
        for c in value:
            result = (result * RANDOM_INT + ord(c)) % self._filter_max_size
        return result

    def is_value(self, value: str) -> bool:
        verification_mask = 0
        verification_mask |= 2 ** self._hash1(value)
        verification_mask |= 2 ** self._hash2(value)
        return verification_mask & self._bloom_filter == verification_mask

    # ЗАПРОСЫ СТАТУСОВ:
    def get_add_status(self) -> int:
        return self._add_status
