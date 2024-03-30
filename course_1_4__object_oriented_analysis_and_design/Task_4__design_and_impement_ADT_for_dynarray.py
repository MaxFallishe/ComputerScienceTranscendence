import ctypes
from ctypes import py_object
from typing import Type


class DynArray[T]:
    APPEND_NIL = 0
    APPEND_OK = 1

    INSERT_NIL = 0
    INSERT_ERR = 1
    INSERT_OK = 2
    INSERT_OK_CAPINCREASE = 3

    GETITEM_NIL = 0
    GETITEM_ERR = 1
    GETITEM_OK = 2

    REMOVE_NIL = 0
    REMOVE_ERR = 1
    REMOVE_OK = 2
    REMOVE_OK_CAPINCREASE = 3

    # КОНСТРУКТОР
    # постусловие: создание пустого динамического массива с емкостью в 16 элементов
    def __init__(self):
        self.__count = 0
        self.__min_capacity = 16
        self.__capacity = 16
        self.__array = self.__make_array(self.__capacity)

        self.__append_status = self.APPEND_NIL
        self.__insert_status = self.INSERT_NIL
        self.__remove_status = self.REMOVE_NIL
        self.__getitem_status = self.GETITEM_NIL

    # КОМАНДЫ:
    # постусловие: размер динамического массива изменен в соответстии со значением new_capacity, capcity не может быть меньше значения __min_capacity
    @staticmethod
    def __make_array(new_capacity: int) -> Type[py_object]:
        return (ctypes.py_object * new_capacity)()

    def __increase_array_capacity(self) -> None:
        new_capacity = self.__capacity * 2
        self.__capacity = new_capacity
        new_array = self.__make_array(new_capacity)
        for i in range(self.__count):
            new_array[i] = self.__array[i]
        self.__array = new_array

    def __decrease_array_capacity(self) -> None:
        new_capacity = self.__capacity // 1.5
        if new_capacity < self.__min_capacity:
            new_capacity = self.__min_capacity
        self.__capacity = new_capacity
        new_array = self.__make_array(new_capacity)
        for i in range(self.__count):
            new_array[i] = self.__array[i]

    # предусловие: индекс элемента не превышает числа элементов в массиве
    # постусловие: новый элемент вставлен в массив согласно указанному индексу
    def insert(self, item: T, index: int) -> None:
        if index < 0 or index > self.__count:
            self.__insert_status = self.INSERT_ERR
            return
        self.__insert_status = self.INSERT_OK
        if self.__count + 1 > self.__capacity:
            self.__increase_array_capacity()
            self.__insert_status = self.INSERT_OK_CAPINCREASE
        self.__array[self.__count] = item
        self.__count += 1
        # print(self.__count, self.__str__())
        for i in range(self.__count - 1 - index):
            self.__array[self.__count - 1 - i], self.__array[self.__count - 2 - i] = self.__array[self.__count - 2 - i], self.__array[self.__count - 1 - i]

    # постусловие: в конец динамического массива добавлен новый элемент
    def append(self, item: T) -> None:
        self.insert(item, self.__count)
        self.__append_status = self.APPEND_OK

    # предусловие: индекс элемента не превышает числа элементов в массиве - 1
    # постусловие: элемент удален из массива согласно указанному индекс, теперь его индекс занят значением правого элемента и т.д.
    def remove(self, index: int) -> None:
        if index < 0 or index > self.__count:
            self.__remove_status = self.REMOVE_ERR
            return
        self.__remove_status = self.REMOVE_OK
        if self.__capacity // self.__count > 2:  # TODO make method from decrease logic "self.__count // self.__capacity < 2"
            self.__decrease_array_capacity()
            self.__remove_status = self.REMOVE_OK_CAPINCREASE
        for i in range(index, self.__count-1):
            self.__array[i], self.__array[i+1] = self.__array[i+1], self.__array[i]
        self.__array[self.__count-1] = ctypes.py_object
        self.__count -= 1


    # ЗАПРОСЫ
    # предусловие: индекс элемента не превышает числа элементов в массиве - 1
    def get_item(self, index: int) -> T:
        if index < 0 or index > self.__count - 1:
            self.__getitem_status = self.GETITEM_ERR
            return
        self.__getitem_status = self.GETITEM_OK
        return self.__array[index]

    def __len__(self):
        return self.__count

    def __str__(self):
        return f'DynArray [{', '.join(self.get_item(i).__str__() for i in range(self.__count))}]'

    # ЗАПРОСЫ СТАТУСОВ:
    def get_append_status(self) -> int:
        return self.__append_status

    def get_insert_status(self) -> int:
        return self.__insert_status

    def get_remove_status(self) -> int:
        return self.__remove_status

    def get_getitem_status(self) -> int:
        return self.__getitem_status
