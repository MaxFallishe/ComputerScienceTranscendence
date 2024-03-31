# Рефлексия
Сравнение спроектированного и эталонного варианта АТД структуры данных HashTable. 
Ниже представлены оба варианта и заключение по существующим отличиям. Не смотря на то, 
что эталонный вариант представлен в формате псевдокода, в отличие от спроектированного варианта 
(который написан в рамках python нотации) основное сравнение проводится именно на основе 
1. Разделения на команды и запросы
2. Сформулированные пост и пред условия
3. Наличию методов для запроса статусов основных методов АТД

## Эталонный вариант
```pseudocode
abstract class HashTable<T>

// конструктор
// постусловие: создана пустая хэш-таблица заданного размера
public HashTable<T> HashTable(int sz); 

// команды
// предусловие: в таблице имеется свободный слот для value;
// постусловие: в таблицу добавлено новое значение
public void put(T value); 

// предусловие: в таблице имеется значение value;
// постусловие: из таблицы удалено значение value
public void remove(T value); 

// запросы
public bool get(T value); // содержится ли значение value в таблице

public int size(); // количество элементов в таблице

// запросы статусов (возможные значения статусов)
public int get_put_status(); // успешно; 
// система коллизий не смогла найти свободный слот для значения

public int get_remove_status(); // успешно; значения нету в таблице

```

## Спроектированный вариант
```python
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

```

# Заключение 
В рамках сравнения представленных АТД, были найден следующие отличия и совпадения:
1. Для конструктора в спроектированном варианте не задано постусловие о создании пустой хэш-таблице заданного размера
2. В спроектированном варианте не реализован аналог метода `size()`, однако реализован атрибут отвечающий за размер
3. Запросы статусов в спроектированном и эталонном варианте реализованы идентично
4. Разделение на запросы и команды реализованы также идентично в обоих вариантах
5. Пост и пред условия во всех методов у спроектированного АТД повторяют эталонный вариант во всех моментах, особенно
важный момент - "предусловие: в таблице имеется значение value" у метода `remove()`
6. В спроектированном варианте, функция хэширования была выделена в отдельный метод (protected), когда в эталонном варианте
её не было совсем

