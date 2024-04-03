class Dict[T]:
    PUT_NIL = 0  # метод put() ещё не вызывался
    PUT_OK = 1  # метод put() успешно вставил значение в ассоциативный массив
    PUT_ERR = 2  # метод put() был вызван когда ассоциативный массив заполнен
    PUT_REWRITE = 3  # метод put() перезаписал значение в ассоциативном массиве

    DELETE_NIL = 0  # метод delete() ещё не вызывался
    DELETE_OK = 1  # метод delete() успешно удалил значение из ассоциативного массива
    DELETE_ERR = 2  # метод delete() не нашел искомого значения для удаления

    GET_NIL = 0  # метод get() ещё не вызывался
    GET_OK = 1  # метод get() успешно выполнился
    GET_ERR = 2  # метод get() не нашел искомого ключа в ассоциативном массиве

    # КОНСТРУКТОР:
    # постусловие: создан пустой ассоциативный массив
    def __init__(self):
        self.max_size = 256
        self._keys = [None] * self.max_size
        self._values = [None] * self.max_size
        self.size = 0
        self.STEP = 2 if self.max_size % 3 == 0 else 3

        self._delete_status = self.DELETE_NIL
        self._put_status = self.PUT_NIL
        self._get_status = self.GET_NIL

    # КОМАНДЫ:
    # предусловие: в ассоциативном массиве имеются свободные слоты для новых значений
    # постусловие: в ассоциативный массив добавлен новый элемент
    def put(self, key: str, value: T) -> None:
        if self.max_size == self.size:
            self._put_status = self.PUT_ERR
            return
        if self.is_key(key):
            self.delete(key)
            self._put_status = self.PUT_REWRITE
        hash_ind = self._hash_fun(key)
        while self._keys[hash_ind] is not None:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._keys[hash_ind] = key
        self._values[hash_ind] = value
        self._put_status = self.PUT_OK
        self.size += 1

    # предусловие: в ассоциативном массиве имеются удаляемый ключ
    # постусловие: из ассоциативного массива удален указанный элемент
    def delete(self, key: str) -> None:
        if not self.is_key(key):
            self._delete_status = self.DELETE_ERR
            return
        hash_ind = self._hash_fun(key)
        while self._keys[hash_ind] is None or self._keys[hash_ind] != key:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._keys[hash_ind] = None
        self._values[hash_ind] = None
        self._delete_status = self.DELETE_OK

    # ЗАПРОСЫ:
    def is_key(self, key: str) -> bool:
        return key in self._keys

    def _hash_fun(self, value: str) -> int:
        return hash(value) % self.max_size

    # предусловие: в ассоциативном массиве имеется искомый ключ
    def get(self, key: str) -> T:
        if not self.is_key(key):
            self._get_status = self.GET_ERR
            return
        hash_ind = self._hash_fun(key)
        while self._keys[hash_ind] != key:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._get_status = self.GET_OK
        return self._values[hash_ind]

    # ЗАПРОСЫ СТАТУСОВ:
    def get_delete_status(self) -> int:
        return self._delete_status

    def get_put_status(self) -> int:
        return self._put_status

    def get_get_status(self) -> int:
        return self._get_status
