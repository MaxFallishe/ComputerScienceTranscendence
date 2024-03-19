from dataclasses import dataclass


class ParentList[T]:
    @dataclass
    class __Node:
        left = None
        right = None
        value = None

    ADDTOEMPTY_NIL = 0  # add_to_empty() ещё не вызывалась
    ADDTOEMPTY_OK = 1  # последняя add_to_empty() отработала нормально
    ADDTOEMPTY_ERR = 2  # add_to_empty была вызвана когда список не пуст

    HEAD_NIL = 0  # head() ещё не вызывалась
    HEAD_OK = 1  # последняя head() отработала нормально
    HEAD_ERR = 2  # head() была вызвана когда список пуст

    TAIL_NIL = 0  # tail() ещё не вызывалась
    TAIL_OK = 1  # последняя tail() отработала нормально
    TAIL_ERR = 2  # tail() была вызвана когда список пуст

    RIGHT_NIL = 0  # right() ещё не вызывалась
    RIGHT_OK = 1  # последняя right() отработала нормально
    RIGHT_ERR = 2  # right() была вызвана когда список пуст
    RIGHT_EMPTY = 3  # right() была вызвана когда справа больше нет узлов

    PUTRIGHT_NIL = 0  # put_right() ещё не вызывалась
    PUTRIGHT_OK = 1  # последняя put_right() отработала нормально
    PUTRIGHT_ERR = 2  # put_right() была вызвана когда список пуст

    PUTLEFT_NIL = 0  # put_left() ещё не вызывалась
    PUTLEFT_OK = 1  # последняя put_left() отработала нормально
    PUTLEFT_ERR = 2  # put_left() была вызвана когда список пуст

    REMOVE_NIL = 0  # remove() ещё не вызывалась
    REMOVE_OK = 1  # последняя remove() отработала нормально
    REMOVE_ERR = 2  # remove() была вызвана когда список пуст

    REPLACE_NIL = 0  # replace() ещё не вызывалась
    REPLACE_OK = 1  # последняя replace() отработала нормально
    REPLACE_ERR = 2  # replace() была вызвана когда список пуст

    FIND_NIL = 0  # find() ещё не вызывалась
    FIND_OK = 1  # последняя find() отработала нормально
    FIND_NOTFOUND = 2  # последняя find() не нашла значения
    FIND_ERR = 3  # find() была вызвана когда список пуст

    GET_NIL = 0  # get() ещё не вызывалась
    GET_OK = 1  # последняя get() отработала нормально
    GET_ERR = 2  # get() была вызвана когда список пуст

    ISHEAD_NIL = 0  # is_head() ещё не вызывалась
    ISHEAD_OK = 1  # последняя is_head() отработала нормально
    ISHEAD_ERR = 2  # is_head() была вызвана когда список пуст

    ISTAIL_NIL = 0  # is_tail() ещё не вызывалась
    ISTAIL_OK = 1  # последняя is_tail() отработала нормально
    ISTAIL_ERR = 2  # is_tail() была вызвана когда список пуст

    ISVALUE_NIL = 0  # is_value() ещё не вызывалась
    ISVALUE_OK = 1  # последняя is_value() отработала нормально
    ISVALUE_ERR = 2  # is_value() была вызвана когда список пуст

    ADDTAIL_NIL = 0  # add_tail() ещё не вызывалась
    ADDTAIL_OK = 1  # последняя add_tail() отработала нормально
    ADDTAIL_ERR = 2  # add_tail() была вызвана когда список пуст (перед вызовом add_empty)

    REMOVEALL_NIL = 0  # remove_all() ещё не вызывалась
    REMOVEALL_OK = 1  # последняя remove_all() отработала нормально
    REMOVEALL_ERR = 2  # remove_all() была вызвана когда список пуст (перед вызовом add_empty)
    # КОНСТРУКТОР

    # постусловие: создан новый пустой список
    def __init__(self):
        self.__head_node = None
        self.__tail_node = None
        self.pointer = None

        self.addtoempty_status = self.ADDTOEMPTY_NIL
        self.head_status = self.HEAD_NIL
        self.tail_status = self.TAIL_NIL
        self.right_status = self.RIGHT_NIL
        self.put_right_status = self.PUTRIGHT_NIL
        self.put_left_status = self.PUTLEFT_NIL
        self.remove_status = self.REMOVE_NIL
        self.replace_status = self.REPLACE_NIL
        self.find_status = self.FIND_NIL
        self.get_status = self.GET_NIL
        self.is_head_status = self.ISHEAD_NIL
        self.is_tail_status = self.ISTAIL_NIL
        self.is_value_status = self.ISVALUE_NIL
        self.add_tail_status = self.ADDTAIL_NIL
        self.remove_all_status = self.REMOVEALL_NIL

    # КОМАНДЫ:

    # предусловие: в списке есть хотя бы один узел
    # постусловие: указатель установлен на первый узел
    def head(self) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.head_status = self.HEAD_ERR
            return
        self.pointer = self.__head_node

    # предусловие: в списке есть хотя бы один узел
    # постусловие: указатель установлен на последний узел
    def tail(self) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.tail_status = self.TAIL_ERR
            return
        self.pointer = self.__tail_node

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: указатель установлен на правый ближайший узел от исходного
    def right(self) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.right_status = self.RIGHT_ERR
            return
        if self.pointer.right is None:
            self.right_status = self.RIGHT_EMPTY
            return
        self.pointer = self.pointer.right
        self.right_status = self.RIGHT_OK

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: справа текущий узел теперь соединен с новым (добавленным) узлом, новый узел соединен с "прошлым" правым узлом текущего узла
    def put_right(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.put_right_status = self.PUTRIGHT_ERR
            return
        new_node = self.__Node()
        new_node.value = value
        new_node.left, new_node.right = self.pointer, self.pointer.right
        if self.pointer.right is not None:
            self.pointer.rihgt.left = new_node
        if self.pointer.right is None:
            self.__tail_node = new_node
        self.pointer.right = new_node
        self.put_right_status = self.PUTRIGHT_OK

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: слева текущий узел теперь соединен с новым (добавленным) узлом, новый узел соединен с "прошлым" левым узлом текущего узла
    def put_left(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.put_left_status = self.PUTLEFT_ERR
            return
        new_node = self.__Node()
        new_node.value = value
        new_node.right, new_node.left = self.pointer, self.pointer.left
        if self.pointer.left is not None:
            self.pointer.left.right = new_node
        if self.pointer.left is None:
            self.__head_node = new_node
        self.pointer.left = new_node
        self.put_left_status = self.PUTLEFT_OK

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел,
    # постусловие: текущий узел удаляется, курсор смещается к правому соседу, если он есть, в противном случае курсор смещается к левому соседу, если он есть
    def remove(self) -> None:
        if self.pointer.right is self.pointer.left is None:
            self.clear()
            return
        if self.pointer.right:
            if self.pointer.left is not None:
                self.pointer.left.right = self.pointer.right
            self.pointer.right.left = self.pointer.left
            self.pointer = self.pointer.right
            self.__head_node = self.pointer
            return
        self.pointer.left.right = None
        self.pointer = self.pointer.left
        self.__tail_node = self.pointer

    # постусловие: указатель не указывает ни на какой узел
    def clear(self) -> None:
        self.pointer = None
        self.__head_node = None
        self.__tail_node = None
        self.addtoempty_status = self.ADDTOEMPTY_NIL

    # предусловие: указатель не установлен не на один из узлов
    # постусловие: добавлен новый узел, указатель установлен на нем
    def add_to_empty(self) -> None:
        if self.addtoempty_status == self.ADDTOEMPTY_NIL:
            new_empty_node = self.__Node()
            self.__head_node = new_empty_node
            self.__tail_node = new_empty_node
            self.pointer = new_empty_node
            self.addtoempty_status = self.ADDTOEMPTY_OK
            return
        self.addtoempty_status = self.ADDTOEMPTY_ERR

    # постусловие: добавлен новый узел в хвост списка, если до этого не было узлов в списке - теперь указаиель установлен на добавленном узле
    def add_tail(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.put_left_status = self.ADDTAIL_ERR
            return
        new_node = self.__Node()
        new_node.value = value
        self.__tail_node.right = new_node
        new_node.left = self.__tail_node
        self.__tail_node = new_node
        self.put_left_status = self.ADDTAIL_OK

    # предусловие: указатель установлен на один из узлов
    # постусловие: значение текущего узла изменено
    def replace(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.replace_status = self.REPLACE_ERR
            return
        self.pointer.value = value
        self.replace_status = self.REPLACE_OK

    # предусловие: указатель установлен на один из узлов
    # постусловие: указатель установлен на узел с искомым значением (правее от исходного)
    def find(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.find_status = self.FIND_ERR
            return
        while self.pointer.right is not None:
            self.pointer = self.pointer.right
            if self.pointer.value == value:
                return
        self.find_status = self.FIND_NOTFOUND

    # постусловие: все узлы с искомым значением удалены из списка, указатель установлен на последний лемент в списке
    def remove_all(self, value: T) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.remove_all_status = self.REMOVEALL_ERR
            return
        while self.pointer is not None:
            self.find(value)
            if self.pointer.get() == value:
                self.remove()

    # ЗАПРОСЫ:

    # предусловие: указатель установлен на один из узлов
    def get(self) -> T:
        if self.addtoempty_status == self.ADDTOEMPTY_NIL:
            self.find_status = self.FIND_ERR
            return
        return self.pointer.value

    def size(self) -> int:
        if self.pointer is None:
            return 0
        init_node = self.__head_node
        node_counter = 1
        while init_node.right is not None:
            node_counter += 1
            init_node = init_node.right
        return node_counter

    # предусловие: указатель установлен на один из узлов
    def is_head(self) -> bool | None:
        if self.addtoempty_status == self.ADDTOEMPTY_NIL:
            self.is_head_status = self.ISHEAD_ERR
            return
        self.is_head_status = self.ISHEAD_OK
        return self.pointer is self.__head_node

    # предусловие: указатель установлен на один из узлов
    def is_tail(self) -> bool | None:
        if self.addtoempty_status == self.ADDTOEMPTY_NIL:
            self.is_tail_status = self.ISTAIL_ERR
            return
        self.is_tail_status = self.ISTAIL_OK
        return self.pointer is self.__tail_node

    # предусловие: указатель установлен на один из узлов
    def is_value(self) -> bool | None:
        if self.addtoempty_status == self.ADDTOEMPTY_NIL:
            self.is_value_status = self.ISVALUE_ERR
            return
        self.is_value_status = self.ISVALUE_OK
        return self.pointer is not None

    # СТАТУСЫ КОММАНД
    def get_addtoempty_status(self) -> int:
        return self.addtoempty_status

    def get_find_status(self) -> int:
        return self.find_status


class LinkedList[T](ParentList):
    pass



class TwoWayList[T](ParentList):
    LEFT_NIL = 0  # left() ещё не вызывалась
    LEFT_OK = 1  # последняя left() отработала нормально
    LEFT_ERR = 2  # left() была вызвана когда список пуст
    LEFT_EMPTY = 3  # left() была вызвана когда справа больше нет узлов

    def __init__(self):
        super().__init__()
        self.left_status = self.LEFT_NIL

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: указатель установлен на правый ближайший узел от исходного
    def left(self) -> None:
        if self.get_addtoempty_status() == self.ADDTOEMPTY_NIL:
            self.left_status = self.LEFT_ERR
            return
        if self.pointer.left is None:
            self.left_status = self.LEFT_EMPTY
            return
        self.pointer = self.pointer.left
        self.right_status = self.LEFT_OK
