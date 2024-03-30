class ParentQueue[T]:
    DEQUEUE_NIL = 0  # метод dequeue() ещё не вызывался
    DEQUEUE_OK = 1  # метод dequeue() успешно удалил элемент (из начала очереди)
    DEQUEUE_ERR = 2  # метод dequeue() был вызван когда очередь была пуста

    GET_NIL = 0  # метод get() ещё не вызывался
    GET_OK = 1  # метод get() успешно вернул первый элемент очереди
    GET_ERR = 2  # метод get() был вызван когда очередь была пуста

    # КОНСТРУКТОР
    # постусловие: создание пустой очереди
    def __init__(self):
        self._array = []

        self.__dequeue_status = self.DEQUEUE_NIL
        self.__get_status = self.GET_NIL

    # КОМАНДЫ:
    # постусловие: в конец очереди добавлен новый элемент
    def enqueue(self, value: T) -> None:
        self._array.insert(0, value)

    # предусловие: очередь не пуста
    # постусловие: из начала очереди удален элемент
    def dequeue(self) -> None:
        if self.size() == 0:
            self.__dequeue_status = self.DEQUEUE_ERR
            return
        self.__dequeue_status = self.DEQUEUE_OK
        del self._array[-1]

    # ЗАПРОСЫ
    def size(self) -> int:
        return len(self._array)

    # предусловие: очередь не пуста
    def get(self) -> T:
        if self.size() == 0:
            self.__get_status = self.GET_ERR
            return
        self.__get_status = self.GET_OK
        return self._array[-1]

    # ЗАПРОСЫ СТАТУСОВ:
    def get_dequeue_status(self) -> int:
        return self.__dequeue_status

    def get_get_status(self) -> int:
        return self.__get_status


class Queue[T](ParentQueue):
    pass


class Dequeue[T](ParentQueue):
    DEQUEUE_TAIL_NIL = 0  # метод dequeue_tail() ещё не вызывался
    DEQUEUE_TAIL_OK = 1  # метод dequeue_tail() успешно удалил элемент (из конца очереди)
    DEQUEUE_TAIL_ERR = 2  # метод dequeue_tail() был вызван когда очередь была пуста

    GET_TAIL_NIL = 0  # метод get_tail() ещё не вызывался
    GET_TAIL_OK = 1  # метод get_tail() успешно вернул последний элемент очереди
    GET_TAIL_ERR = 2  # метод get_tail() был вызван когда очередь была пуста

    # КОНСТРУКТОР
    # постусловие: создание пустой двухсторонней очереди
    def __init__(self):
        super().__init__()
        self.__dequeue_tail_status = self.DEQUEUE_TAIL_NIL
        self.__get_tail_status = self.GET_TAIL_NIL
        self._array = self._array

    # КОМАНДЫ
    # постусловие: в начало очереди добавлен новый элемент
    def enqueue_head(self, value) -> None:
        self._array.append(value)

    # предусловие: очередь не пуста
    # постусловие: из начала очереди удален элемент
    def dequeue_tail(self) -> None:
        if self.size() == 0:
            self.__dequeue_tail_status = self.DEQUEUE_TAIL_ERR
            return
        self.__dequeue_tail_status = self.DEQUEUE_TAIL_OK
        del self._array[0]

    # ЗАПРОСЫ
    def get_tail(self) -> T:
        return self._array[0]

    # ЗАПРОСЫ СТАТУСОВ:
    def get_dequeue_tail_status(self) -> int:
        return self.__dequeue_tail_status

    def get_get_tail_status(self) -> int:
        return self.__get_tail_status
