class HashTable[T]:
    PUT_NIL = 0  # метод put() ещё не вызывался
    PUT_OK = 1  # метод put() успешно вставил значение в хэш-таблицу
    PUT_ERR = 2  # метод put() был вызван когда хэщ-таблица заполнена

    DELETE_NIL = 0  # метод delete() ещё не вызывался
    DELETE_OK = 1  # метод delete() успешно удалил значение из хэш-таблицы
    DELETE_ERR = 2  # метод delete() не нашел искомого значения для удаления

    # КОНСТРУКТОР
    def __init__(self, max_size: int):
        if max_size < 0:
            raise ValueError("max_size cannot be negative")
        self.max_size = max_size
        self._slots = [None] * max_size
        self.size = 0
        self.STEP = 2 if self.max_size % 3 == 0 else 3

        self.__put_status = self.PUT_NIL
        self.__delete_status = self.DELETE_NIL

    # КОМАНДЫ:
    # предусловие: в хэш-таблице имеется свободный слот для нового значения
    # постусловие: в хэш-таблицу добавлен новый элемент
    def put(self, value: T) -> int:
        if self.size == self.max_size:
            self.__put_status = self.PUT_ERR
            return
        hash_ind = self._hash_fun(value)
        while self._slots[hash_ind] is not None:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._slots[hash_ind] = value
        self.__put_status = self.PUT_OK
        self.size += 1

    # предусловие: в хэш-таблице имеется удаляемое значение
    # постусловие: из хэш-таблицы удалено выбранное значение
    def delete(self, value: T) -> None:
        if not self.find(value):
            self.__delete_status = self.DELETE_ERR
            return
        hash_ind = self._hash_fun(value)
        while self._slots[hash_ind] is None or self._slots[hash_ind] != value:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self.__delete_status = self.DELETE_OK
        self._slots[hash_ind] = None

    # ЗАПРОСЫ:
    def _hash_fun(self, value: str) -> int:
        return hash(value) % self.max_size

    def find(self, value: T) -> bool:
        return value in self._slots

    def __str__(self):
        return self._slots.__str__()

    # ЗАПРОСЫ СТАТУСОВ:
    def get_delete_status(self) -> int:
        return self.__delete_status

    def get_put_status(self) -> int:
        return self.__put_status
