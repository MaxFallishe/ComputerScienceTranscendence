# Рефлексия
Сравнение спроектированного и эталонного варианта АТД структуры данных **Deque**. 
Ниже представлены оба варианта и заключение по существующим отличиям. Не смотря на то, 
что эталонный вариант представлен в формате псевдокода, в отличие от спроектированного варианта 
(который написан в рамках python нотации) основное сравнение проводится именно на основе 
1. Разделения на команды и запросы
2. Сформулированные пост и пред условия
3. Наличию методов для запроса статусов основных методов АТД

## Эталонный вариант
```pseudocode
abstract class ParentQueue<T>

// конструктор
public ParentQueue<T> ParentQueue();

// команды
// постусловие: в хвост очереди добавлен новый элемент
public void add_tail(T value);

// предусловие: очередь не пуста;
// постусловие: из головы очереди удалён элемент
public void remove_front();

// запросы
// предусловие: список не пуст
public T get_front(); // значение элемента в голове очереди; 

public int size(); // текущий размер очереди

// запросы статусов (возможные значения статусов)
public int get_remove_front_status(); // успешно; очередь пуста
public int get_get_front_status(); // успешно; очередь пуста


abstract class Queue<T> : ParentQueue<T>

// конструктор
public Queue<T> Queue();


abstract class Deque<T> : ParentQueue<T>

// конструктор
// постусловие: создана пустая очередь
public Deque<T> Deque();

// команды
// постусловие: в голову очереди добавлен новый элемент
public void add_front(T value); 

// предусловие: очередь не пуста;
// постусловие: из хвоста очереди удалён элемент
public void remove_tail(); 

// запросы
// предусловие: список не пуст
public T get_tail(); // значение элемента в хвосте очереди; 

public int size(); // текущий размер очереди

// запросы статусов (возможные значения статусов)
public int get_remove_tail_status(); // успешно; очередь пуста
public int get_get_tail_status(); // успешно; очередь пуста
```

## Спроектированный вариант
```python
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

```

# Заключение 
В рамках сравнения представленных АТД, были найден следующие отличия и совпадения:
1. Разделение на команды и запросы во всех классах в спроетированном варианте эквивалентно эталонному варианту
2. Пост и пред условия для всех эквивалентных методах в обоих вариантах совпадают, за исключением метода `get_tail` в
котором не хватает предусловия "предусловие: список не пуст"
3. Разделение на родительский класс и классы наследуемые в обоих вариантах совпадают, вплоть до названия
4. Все методы запроса статусов методов из эталонного варианта, также реализованы в спроектированном варианте 
