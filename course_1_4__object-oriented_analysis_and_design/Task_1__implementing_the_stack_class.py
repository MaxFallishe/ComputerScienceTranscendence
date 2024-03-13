class BoundedStack[T]:
    POP_NIL = 0
    POP_OK = 1
    POP_ERR = 2

    PEEK_NIL = 0
    PEEK_OK = 1
    PEEK_ERR = 2

    PUSH_NIL = 0
    PUSH_OK = 1
    PUSH_ERR = 2

    def __init__(self, max_size: int = 32):  # конструктор
        self.__stack: list[T] = []
        self.max_size: int = max_size
        self.__peek_status = self.PEEK_NIL  # статус команды peek()
        self.__pop_status = self.POP_NIL  # статус команды pop()
        self.__push_status = self.PUSH_NIL  # статус команды push()

    # предусловие: количество элементов стека меньше значения self.max_size
    # постусловие: новый элемент добавляется в вверх стэка
    def push(self, value: T) -> None:
        if self.size() == self.max_size:
            self.__push_status = self.PUSH_ERR
            return
        self.__push_status = self.PUSH_OK
        self.__stack.append(value)

    # предусловие: количество элементов в стеке больше 0
    # постусловие: количество элементов в стеке уменьшается на 1
    def pop(self) -> T | None:
        if self.size() == 0:
            self.__pop_status = self.POP_ERR
            return
        result = self.__stack[-1]
        self.__stack = self.__stack[:-1]
        self.__pop_status = self.POP_OK
        return result

    # постусловие: стек очищается от всех элементов
    def clear(self) -> None:
        self.__stack = []
        self.__peek_status = self.PEEK_NIL
        self.__push_status = self.PUSH_NIL
        self.__pop_status = self.POP_NIL

    # предусловие: количество элементов стека меньше значения self.max_size
    def peek(self) -> T | None:
        if self.size() == 0:
            self.__peek_status = self.PEEK_ERR
            return
        self.__peek_status = self.PEEK_OK
        return self.__stack[-1]

    def size(self) -> int:
        return len(self.__stack)

    def get_pop_status(self) -> int:  # возвращает значение POP_*
        return self.__pop_status

    def get_peek_status(self) -> int:  # возвращает значение PEEK_*
        return self.__peek_status

    def get_push_status(self) -> int:  # возвращает значение PUSH_*
        return self.__push_status

