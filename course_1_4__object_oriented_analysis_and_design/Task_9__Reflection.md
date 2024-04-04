# Рефлексия
Сравнение спроектированного и эталонного варианта АТД структуры данных PowerSet. 
Ниже представлены оба варианта и заключение по существующим отличиям. Не смотря на то, 
что эталонный вариант представлен в формате псевдокода, в отличие от спроектированного варианта 
(который написан в рамках python нотации) основное сравнение проводится именно на основе 
1. Разделения на команды и запросы
2. Сформулированные пост и пред условия
3. Наличию методов для запроса статусов основных методов АТД

## Эталонный вариант
```pseudocode
abstract class PowerSet<T> : HashTable<T>

// конструктор
// постусловие: создано пустое множество
// на максимальное количество элементов sz
public PowerSet<T> PowerSet(int sz); 

// запросы
// возвращает пересечение текущего множества
// с множеством set
public PowerSet<T> Intersection(PowerSet<T> set);

// возвращает объединение текущего множества
// и множества set
public PowerSet<T> Union(PowerSet<T> set);

// возвращает разницу между текущим множеством
// и множеством set
public PowerSet<T> Difference(PowerSet<T> set);

// проверка, будет ли set подмножеством
// текущего множества
public bool IsSubset(PowerSet<T> set);
```

## Спроектированный вариант
```python
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
        diff_set = PowerSet(self.max_size * 2)
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
```

# Заключение 
В рамках сравнения представленных АТД, были найден следующие отличия и совпадения:
1. В спроектированном варианте не учтено постусловие в конструкторе
2. Разделение на команды и запросы в спроектированном варианте, произведено не корректно - методы `union` и `intersection`
следует отнести к запросам
3. Методы `difference`, `issubset`, `union` и `intersection` в эталонном и спроектированном варианте не имеют пост и пред условий
4. АТД PowerSet наследуется от АТД HashTable в обоих вариантах
5. В спроектированном варианте, в команды добавлен перегруженный метод put, чего нет в эталонном варианте
