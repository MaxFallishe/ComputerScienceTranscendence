# Рефлексия
Сравнение спроектированного и эталонного варианта АТД структуры данных Queue. 
Ниже представлены оба варианта и заключение по существующим отличиям. Не смотря на то, 
что эталонный вариант представлен в формате псевдокода, в отличие от спроектированного варианта 
(который написан в рамках python нотации) основное сравнение проводится именно на основе 
1. Разделения на команды и запросы
2. Сформулированные пост и пред условия
3. Наличию методов для запроса статусов основных методов АТД

## Эталонный вариант
```pseudocode
abstract class Queue<T>

// конструктор
// постусловие: создана пустая очередь
public Queue<T> Queue();

// команды
// постусловие: в хвост очереди добавлен новый элемент
public void enqueue(T value);

// предусловие: очередь не пуста;
// постусловие: из головы очереди удалён элемент
public void dequeue();

// запросы
// предусловие: очередь не пуста
public T get(); // получить элемент из головы очереди; 

public int size(); // текущий размер очереди

// запросы статусов (возможные значения статусов)
public int get_dequeue_status(); // успешно; очередь пуста
public int get_get_status(); // успешно; очередь пуста
```

## Спроектированный вариант
```python
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
```

# Заключение 
В рамках сравнения представленных АТД, были найден следующие отличия и совпадения:
1. Конструктор в эталонном и спроектированном варианте имеют идентичные постусловия
2. В эталонном и спроектированном варианте идентичны пред/пост условия для методов `enqueue` | `append`, `size` | `size`,
`get` | `peek_queue_head`
3. Пост и предусловия для методов `dequeue` | `pop` идентичны в обоих реализациях, однако отличаются тем, что `pop` 
также возвращает элемент типа `T` из начала очереди. 
4. В спроектированном варианте также представлен дополнительный метод `peek_queue_tail`, не представленный в эталонном
5. Разделение на ЗАПРОСЫ, КОМАНДЫ и ЗАПРОСЫ СТАТУСОВ в обоих вариантах не противоречиво
6. ЗАПРОС СТАТУСОВ в спроектированном варианте, содержит запросы из эталонного варианта с запросами также для методов 
`get_size_status`, `get_append_status`, `get_peek_queue_tail_status`
