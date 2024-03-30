class LinkedList[T]:
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
    FIND_ERR = 2  # find() была вызвана когда список пуст

    GET_NIL = 0  # get() ещё не вызывалась
    GET_OK = 1  # последняя get() отработала нормально
    GET_ERR = 2  # get() была вызвана когда список пуст

    # КОМАНДЫ:

    # предусловие: в списке есть хотя бы один узел
    # постусловие: указатель установлен на первый узел
    def head(self) -> None:
        pass

    # предусловие: в списке есть хотя бы один узел
    # постусловие: указатель установлен на последний узел
    def tail(self) -> None:
        pass

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел, указатель установлен не на последний узел
    # постусловие: указатель установлен на правый ближайший узел от исходного
    def right(self) -> None:
        pass

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: справа текущий узел теперь соединен с новым (добавленным) узлом, новый узел соединен с "прошлым" правым узлом текущего узла
    def put_right(self) -> None:
        pass

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел
    # постусловие: слева текущий узел теперь соединен с новым (добавленным) узлом, новый узел соединен с "прошлым" левым узлом текущего узла
    def put_left(self) -> None:
        pass

    # предусловие: в списке есть хотя бы один узел, указатель установлен на какой-либо узел,
    # постусловие: текущий узел удаляется, курсор смещается к правому соседу, если он есть, в противном случае курсор смещается к левому соседу, если он есть
    def remove(self) -> None:
        pass

    # постусловие: указатель не указывает ни на какой узел
    def clear(self) -> None:
        pass

    # предусловие: указатель не установлен не на один из узлов
    # постусловие: добавлен новый узел, указатель установлен на нем
    def add_to_empty(self) -> None:
        pass

    # постусловие: добавлен новый узел в хвост списка, если до этого не было узлов в списке - теперь указаиель установлен на добавленном узле
    def add_tail(self) -> None:
        pass

    # предусловие: указатель установлен на один из узлов
    # постусловие: значение текущего узла изменено
    def replace(self, value) -> None:
        pass

    # предусловие: указатель установлен на один из узлов
    # постусловие: указатель установлен на узел с искомым значением (правее от исходного)
    def find(self, value) -> None:
        pass

    # постусловие: все узлы с искомым значением удалены из списка
    def remove_all(self) -> None:
        pass

    # ЗАПРОСЫ:

    # предусловие: указатель установлен на один из узлов
    def get(self) -> T:
        pass

    def size(self) -> int:
        pass

    # предусловие: указатель установлен на один из узлов
    def is_head(self) -> bool:
        pass

    # предусловие: указатель установлен на один из узлов
    def is_tail(self) -> bool:
        pass

    # предусловие: указатель установлен на один из узлов
    def is_value(self) -> bool:
        pass

    # СТАТУСЫ КОММАНД
    def get_addtoempty_status(self) -> int:
        pass

# Почему операция tail не сводима к другим операциям (если исходить из эффективной реализации)?
# tail не сводима к другим базовым 8 операциям так как при необходимости получить значение последнего элемента пришлось
# бы каждый раз проделать цикл с командой right() с условием которое бы помогало понять что данный узел последний,
# что в свою очередь нарушает момент с тем чтобы ориентироваться именно на концепт указателя, а не связей между узлами


# Операция поиска всех узлов с заданным значением, выдающая список таких узлов, уже не нужна. Почему?
# Так как текущий способ реализации связного списка включает в себя понятие курсора, имеется возможность совместить
# команды head() и множество команд find() для поиска всех значений в списке. Реализация find_all() будет не совсем
# понятна для реализации, так как если реализовать по примеру find(), find_all() просто переберет все подходящие узлы,
# не не получит их значение. Однако присутствует интересный вариант в плане того, чтобы сделать метод find_all
# функцией высшего порядка в которую в качестве аргумента можно передать например get(), remove(), put_left и т.д.
#
#
#