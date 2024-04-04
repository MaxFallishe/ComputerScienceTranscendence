from typing import Self
from copy import deepcopy


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

        self._put_status = self.PUT_NIL
        self._delete_status = self.DELETE_NIL

    # КОМАНДЫ:
    # предусловие: в хэш-таблице имеется свободный слот для нового значения
    # постусловие: в хэш-таблицу добавлен новый элемент
    def put(self, value: T) -> int:
        if self.size == self.max_size:
            self._put_status = self.PUT_ERR
            return
        hash_ind = self._hash_fun(value)
        while self._slots[hash_ind] is not None:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._slots[hash_ind] = value
        self._put_status = self.PUT_OK
        self.size += 1

    # предусловие: в хэш-таблице имеется удаляемое значение
    # постусловие: из хэш-таблицы удалено выбранное значение
    def delete(self, value: T) -> None:
        if not self.find(value):
            self._delete_status = self.DELETE_ERR
            return
        hash_ind = self._hash_fun(value)
        while self._slots[hash_ind] is None or self._slots[hash_ind] != value:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._delete_status = self.DELETE_OK
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
        return self._delete_status

    def get_put_status(self) -> int:
        return self._put_status


class PowerSet[T](HashTable):
    PUT_REWRITE = 3  # метод put() перезаписал значение в множестве

    # КОНСТРУКТОР:
    def __init__(self, max_size):
        super().__init__(max_size)

    # КОМАНДЫ:
    # предусловие: в множестве имеется свободный слот для нового значения и новое значение будет являтся уникальным для множества
    # постусловие: в множестве добавлен новый элемент
    def put(self, value: T) -> int:
        if self.size == self.max_size:
            self._put_status = self.PUT_ERR
            return
        if self.find(value):
            self._put_status = self.PUT_REWRITE
            return
        hash_ind = self._hash_fun(value)
        while self._slots[hash_ind] is not None:
            hash_ind += self.STEP
            if hash_ind >= self.max_size:
                hash_ind = hash_ind % self.max_size
        self._slots[hash_ind] = value
        self._put_status = self.PUT_OK
        self.size += 1

    def intersection(self, set_: Self) -> Self:
        intersection_set = PowerSet(self.max_size)
        for i in self._slots:
            if i is None:
                continue
            if set_.find(i):
                intersection_set.put(i)
        return intersection_set

    def union(self, set_: Self) -> Self:
        union_set = deepcopy(set_)
        for i in self._slots:
            if i is None:
                continue
            union_set.put(i)
        return union_set

    # ЗАПРОСЫ:
    def difference(self, set_: Self) -> Self:
        diff_set = PowerSet(self.max_size)
        for i in self._slots:
            if i is None:
                continue
            if not set_.find(i):
                diff_set.put(i)
        return diff_set

    def issubset(self, set_: Self) -> bool:
        for i in set_._slots:
            if i is None:
                continue
            if not self.find(i):
                return False
        return True
