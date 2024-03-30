class Queue[T]:
    APPEND_NIL = 0  # метод append ещё не вызывался
    APPEND_OK = 1  # в очередь успешно добавился новый элемент (в конец очереди)

    POP_NIL = 0  # метод pop ещё не вызывался
    POP_OK = 1  # метод pop успешно удалил элемент (из начала очереди)
    POP_ERR = 2  # метод pop был вызван когда очередь была пуста

    SIZE_NIL = 0  # метод size ещё не вызывался
    SIZE_OK = 1  # метод size успешно возвратил длину очереди

    PEEK_QUEUE_HEAD_NIL = 0  # метод peek_queue_head() ещё не вызывался
    PEEK_QUEUE_HEAD_OK = 1  # метод peek_queue_head() успешно вернул первый элемент очереди
    PEEK_QUEUE_HEAD_ERR = 2  # метод peek_queue_head() был вызван когда очередь была пуста

    PEEK_QUEUE_TAIL_NIL = 0  # метод peek_queue_tail() ещё не вызывался
    PEEK_QUEUE_TAIL_OK = 1  # метод peek_queue_tail() успешно вернул последний элемент очереди
    PEEK_QUEUE_TAIL_ERR = 2  # метод peek_queue_tail() был вызван когда очередь была пуста

    # КОНСТРУКТОР
    # постусловие: создание пустой очереди
    def __init__(self):
        self.__array = []

        self.__append_status = self.APPEND_NIL
        self.__pop_status = self.POP_NIL
        self.__size_status = self.SIZE_NIL
        self.__peek_queue_head_status = self.PEEK_QUEUE_HEAD_NIL
        self.__peek_queue_tail_status = self.PEEK_QUEUE_TAIL_NIL

    # КОМАНДЫ:
    # постусловие: в конец очереди добавлен новый элемент
    def append(self, value: T) -> None:
        self.__append_status = self.APPEND_OK
        self.__array.insert(0, value)

    # предусловие: очередь не пуста
    # постусловие: из начала очереди удален элемент
    def pop(self) -> T:
        if self.size() == 0:
            self.__pop_status = self.POP_ERR
            return
        self.__pop_status = self.POP_OK
        pop_result = self.__array[-1]
        del self.__array[-1]
        return pop_result

    # ЗАПРОСЫ
    def size(self) -> int:
        self.__size_status = self.SIZE_OK
        return len(self.__array)

    # предусловие: очередь не пуста
    def peek_queue_head(self) -> T:
        if self.size() == 0:
            self.__peek_queue_head_status = self.PEEK_QUEUE_HEAD_ERR
            return
        self.__peek_queue_head_status = self.PEEK_QUEUE_HEAD_OK
        return self.__array[0]

    # предусловие: очередь не пуста
    def peek_queue_tail(self) -> T:
        if self.size() == 0:
            self.__peek_queue_tail_status = self.PEEK_QUEUE_TAIL_ERR
            return
        self.__peek_queue_tail_status = self.PEEK_QUEUE_TAIL_OK
        return self.__array[-1]

    # ЗАПРОСЫ СТАТУСОВ:
    def get_append_status(self) -> int:
        return self.__append_status

    def get_pop_status(self) -> int:
        return self.__pop_status

    def get_size_status(self) -> int:
        return self.__size_status

    def get_peek_queue_head_status(self) -> int:
        return self.__peek_queue_head_status

    def get_peek_queue_tail_status(self) -> int:
        return self.__peek_queue_tail_status
