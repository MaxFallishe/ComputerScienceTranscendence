# Рефлексия
Сравнение спроектированного и эталонного варианта АТД структуры данных Bounded Stack. 
Ниже представлены оба варианта и заключение по существующим отличиям. Не смотря на то, 
что эталонный вариант представлен в формте псевдокода, в отличие от спроектированного варианта 
(который написан в рамках python нотации) основное сравнение проводится именно на основе 
1. Разделения на команды и запросы
2. Сформулированные пост и пред условия
3. Наличию методов для запроса статусов основных методов АТД 

## Эталонный вариант
```pseudocode
abstract class BoundedStack<T>

public const int POP_NIL = 0; // push() ещё не вызывалась
public const int POP_OK = 1; // последняя pop() отработала нормально
public const int POP_ERR = 2; // стек пуст

public const int PEEK_NIL = 0; // push() ещё не вызывалась
public const int PEEK_OK = 1; // последняя peek() вернула корректное значение 
public const int PEEK_ERR = 2; // стек пуст

public const int PUSH_OK = 1; // последняя push() отработала нормально
public const int PUSH_ERR = 2; // в стеке нет свободного места 

// конструктор
// постусловие: создан новый пустой стек
public BoundedStack<T> BoundedStack(int max_size);


// команды:
// предусловие: в стеке менее максимального кол-ва элементов
// постусловие: в стек добавлено новое значение
public void push(T value); 

// предусловие: стек не пустой; 
// постусловие: из стека удалён верхний элемент
public void pop(); 

// постусловие: из стека удалятся все значения
public void clear();


// запросы:
// предусловие: стек не пустой
public T peek(); 

public int size();
public int max_size();

// дополнительные запросы:
public int get_pop_status(); // возвращает значение POP_*
public int get_peek_status(); // возвращает значение PEEK_*
public int get_push_status(); // возвращает значение PUSH_*
```

## Спроектированный вариант
```python
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
```

# Заключение 
В рамках сравнения представленных АТД, были найдены следующие отличия и совпадения / положительные и негативные моменты :
1. В спроектированном варианте присутствует три варианта состояния для  операции `push` - PUSH_NIL, PUSH_OK, PUSH_ERR. В то время
   как в эталонном решении присутствует только два статуса PUSH_OK и PUSH_ERR. Данное отличие не является катастрофическим,
   однако всё же показывает, что для операции `push` статус NIL не обязателен. Стоит заметить что во время отладки, этот
   статус может полезен чтобы отследить состояние стека, при котором в него ещё ничего не вставили.
2. Из миноритарных отличий можно заметить отсутствие постусловия у конструктора в спроектированном варианте,
   однако здесь постусловие не имеет никаких важных особенностей, лишь имеет статус "создан новый пустой стек". Все же
   стоит данно постусловие в будущем прописывать.
3. Всё что касается ранее не упомянутых пост и пред условий - они расставлены верно, дополнительные запросы также реализованы
   реализованы в полном объеме. Единственная неточность заключается в формулировке постусловия метода `pop` который
   должен быть больше поход на "из стека удалён верхний элемент"
4. Не прописано явного разделения на запросы и команды, не считая реализованных python typehints
5. Крайне положительно, что во время дизайна АТД на python были использованы, добавленные в python 3.12, инструменты для
   работы с дженериками в синтаксисе программы.