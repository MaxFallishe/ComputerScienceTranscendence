# Что такое цикломатическая сложность?
## Введение
Самое важное что нужно знать о **цикломатической сложности** - это то, что это метрика простоты кода, причем крайне эффективная. Если конкретнее: чем она больше, тем запутанее код и тем более сложен он для редактирования и понимание. Применил метрику к коду - получил объективную оценку.

Как это выглядит на практике - есть много разнообразных утилит, расширений и библиотек, но главный концепт таков:
1. На вход подается функция или метод
2. На выход выводится показатель цикломатической сложности

Пример работы утилиты python модуля `radon` (для определения цикломатической сложности):
```
$ radon cc my_file.py -s

M 57:4 FilePermissionCh.validate - A (4)
M 68:4 FullMatchCh.validate - A (4)
F 15:0 parse_users_table - A (1)
M 42:4 DefualtCheckH.__init__ - A (1)
M 52:4 DefualtCheckH.validate - A (1)
```
* `radon` - вызоваем сам модуль
* `cc` - указываем что хотим определить именно цикломатическую сложнсть, radon может работать и с другими метрикаим
* `myfile.py` - указыаем путь к файлу, код которого хотим проаналиировать
* `-s` - указываем что хотим вывести само значение цикломатической сложности, то, что в скобочках  

* `M` - тип того что анализируется M - метод, F - функция и т.д.
* `57:4` -  метод начинается на 57-ой строке и на 4-ом пробеле вложенности
* `FilePermissionCh.validate` - метод validate в классе  FilePermissionCh
* `A` - ранк эффективности кода в рамках цикломатической сложности A(отлично), B(хорошо) и т.д.
* `(4)` - абсолютный показатель цикломатичной сложности от 1 до бесконечности (чем меньше тем лучше)

## Подробная информация
Теперь можно раскрыть несколько более глубоких, но интересных моментов про цикломатическую сложность. Например сам термин был введен ещё в 1976 году ([оригинал статьи](http://www.literateprogramming.com/mccabe.pdf)) Томасом Маккейбом и сама идея концепции понятна и проста - чем большим количеством способом программа может выполниться, то есть чем больше разнообразных условий и проверок, т,ем она становиться сложнее для понимания и осмысления. 

И на этом моменте можно подумать что чем больше проект - тем больше у него цикломатическая сложность, но это не совсем верно. Если взглянуть на пример работы утилиты radon выше, можно заметить что статистика выводиться по функциям, методам и классам вместо одного значения на весь файл. Поэтому даже в сложных и больших программах возможно держать цикломатическую сложность на низких значениях с помощью некоторых хороших практик (подробнее в следующем разделе).

> Кстати, чтобы легче понять цикломатическую сложность, можно разложить данный термин для себя на части его составляющие, а именно "**цикло**" и "**матический**". "Цикло" - подразумевает то, что чтобы определить сложность необходимо циклически изучить все варианты которыми программа может отработать, а "матический" - что для определения сложности также используются математические принципы и методы.
## Способы понизить цикломатическую сложность
Есть несколько способов контролировать цикломатическую сложность, однако прежде чем мы приступим к их рассмотрению, стоит упомянуть один из самых важных принципов - **нужно стараться работать только с формой кода, а не с его содержанием**. 

А вот и сами способы понижения цикломатической сложности, некоторые из них  мы более подробно рассмотрм на практике в следующем разделе:
1.  Минимизировать использование конструкций `if`, `else`, `match/case` в коде
2.  Минимизировать количество циклов
3.  Не использовать `null/None`
4.  Использовать **ad-hoc полиморфизм**
5.  Использовать **параметрический полиморфизм**
6.  Использовать **multiple dispatch**
7.  Использовать механизм дженериков
8.  Использовать паттерн "табличной логики"
# Примеры понижения цикломатической сложности
Сейчас мы посмотрим на то, как можно изменить показатель цикломатической сложности кода в лучшую сторону, а в качестве бесстрастного судьи будем использовать ранее упомянутый модуль `radon`. В рамках каждого примера продемонстрированы изменения произошедшие в коде, а также представлены результаты анализа цикломатической сложности от `radon`,
## Пример №1
В данном примере представлен рефакторинг функции `process_xml`, которая разбирает объекты в xml файлах, в поисках соответсвий с определенными критериями которые передаются в аргумент `json_data`. Далее, в качестве итогов работы, функция возвращает список, состоящий из строк и чисел.

В рамках рефакторинга основной упор был сделан на снижение цикломатической сложности + небольшие стилистические правки, основная логика осталась той же, хотя, если немного изменить её, можно добиться большей выразительности и простоты кода, особенно это касается выходной структуры данных `return results`.

Снижения цикломатической сложности удалось добиться, например, благодаря ликвидации лишних условных операторов (`if`-ов и `else)`
```python
if args.verbose:  
	print("Openning file: " + var_file_short)  

↓↓↓↓↓↓↓↓↓↓↓↓↓

logger.info(f"Openning file: {var_file_short}")  
```

Кроме того, вложенные циклы были заменены на функции из методов itertools и functoold
```python
for obj in json_data:  
	found = True 
	if args.verbose:  
		print("Checking: " + var_file_short + " - " + obj['Name'])  
	for entry in root.findall('entry'): 
	...

↓↓↓↓↓↓↓↓↓↓↓↓↓

results += chain(*map(partial(validate_products, row_counter), checklist_points_and_entries_product)) 
```

Также были добавлены отдельные классы для обработки разного типа объектов по ключевому полю `TypeOfCheck`, с реализацией метода `validate` через наследование и перегрузку, тем самым пытаясь имитировать ad-hoc полиорфизм, насколько это возможно в рамках python без дополнительных внешних модулей
```python
if obj['TypeOfCheck'] == 'FilePermissions':  
	if datagroup == obj['DataGroup'] and subkey == 'Permissions':  
		if value not in obj['value']:  
			results.extend([row_counter, obj['column'], '1'])  
		else:  
			results.extend([row_counter, obj['column'], '0'])  

if obj['TypeOfCheck'] == 'FullMatch':  
	if key == obj['CheckParam'] and subkey == obj['CheckParamSub']:  
		if value == obj['value']:  
			if args.debug:  
				print(var_file_short)  
			results.extend([row_counter, obj['column'], '1'])  
		else:  
			results.extend([row_counter, obj['column'], '0']) 

↓↓↓↓↓↓↓↓↓↓↓↓↓

...
check_handler_instance = DefualtCheckHandler(checklist_point, entry, row_num)  
match checklist_point['TypeOfCheck']:  
	case 'FilePermissions':  
		check_handler_instance = FilePermissionCheckHandler(checklist_point, entry, row_num)  
	case 'FullMatch':  
		check_handler_instance = FullMatchCheckHandler(checklist_point, entry, row_num)  
    return check_handler_instance.validate()  
  
  
class DefualtCheckHandler:  
    def __init__(self, checklist_point, entry, row_num):  
        self.checklist_point = checklist_point  
        self.entry = entry  
        self.row_num = row_num  
        self.key = entry.find('key').text  
        self.subkey = entry.find('subkey').text  
        self.value = entry.find('value').text  
        self.datagroup = entry.find('DataGroup').text  
  
    @classmethod  
    def validate(cls) -> list:  
        return []  
  
  
class FilePermissionCheckHandler(DefualtCheckHandler):  
    def validate(self) -> list:  
        if self.datagroup != self.checklist_point['DataGroup']:  
            return []  
        if self.subkey != 'Permissions':  
            return []  
        if self.value in self.checklist_point['value']:  
            return [self.row_num, self.checklist_point['column'], '0']  
        return [self.row_num, self.checklist_point['column'], '1']  
  
  
class FullMatchCheckHandler(DefualtCheckHandler):  
    def validate(self) -> list:  
        if self.key != self.checklist_point['CheckParam']:  
            return []  
        if self.subkey != self.checklist_point['CheckParamSub']:  
            return []  
        if self.value != self.checklist_point['value']:  
            return [self.row_num, self.checklist_point['column'], '0']  
        return [self.row_num, self.checklist_point['column'], '1']
```

Цикломатическая сложность ДО и ПОСЛЕ:
```
ДО:
F 10:0 process_xml - C (15)


ПОСЛЕ:
C 56:0 FilePermissionCheckHandler - A (5)
C 67:0 FullMatchCheckHandler - A (5)
M 57:4 FilePermissionCheckHandler.validate - A (4)
M 68:4 FullMatchCheckHandler.validate - A (4)
F 30:0 validate_products - A (3)
C 41:0 DefualtCheckHandler - A (2)
F 15:0 process_xml - A (1)
M 42:4 DefualtCheckHandler.__init__ - A (1)
M 52:4 DefualtCheckHandler.validate - A (1)

```

**ДО:**
```python
import xml.etree.ElementTree as ET  
import os  
from src.arg_parser import parse_arguments  
  
args = parse_arguments()  
results = []  
found = False  
  
  
def process_xml(fargs):  
    var_file, json_data, row_counter = fargs  
    tree = ET.parse(os.path.join(args.input_dir, var_file))  
    root = tree.getroot()  
    var_file_short = var_file.split(":")[0]  
    results.extend([row_counter, 1, var_file_short])  
    if args.verbose:  
        print("Openning file: " + var_file_short)  
  
    for obj in json_data:  
        if args.verbose:  
            print("Checking: " + var_file_short + " - " + obj['Name'])  
  
        for entry in root.findall('entry'):  
            key = entry.find('key').text  
            subkey = entry.find('subkey').text  
            value = entry.find('value').text  
            datagroup = entry.find('DataGroup').text  
  
            if obj['TypeOfCheck'] == 'FilePermissions':  
                if datagroup == obj['DataGroup'] and subkey == 'Permissions':  
                    if value not in obj['value']:  
                        results.extend([row_counter, obj['column'], '1'])  
                    else:  
                        results.extend([row_counter, obj['column'], '0'])  
  
            if obj['TypeOfCheck'] == 'FullMatch':  
                if key == obj['CheckParam'] and subkey == obj['CheckParamSub']:  
                    if value == obj['value']:  
                        if args.debug:  
                            print(var_file_short)  
                        results.extend([row_counter, obj['column'], '1'])  
                    else:  
                        results.extend([row_counter, obj['column'], '0'])  
 
    row_counter += 1  
    return results
```

**ПОСЛЕ:**
```python
import logging  
import os  
from functools import partial  
from itertools import product, chain  
from typing import Iterator  
from xml.etree import ElementTree  
  
from src.arg_parser import args  
  
logger = logging.getLogger(__name__)  
  
FIRST_EXCEL_COLUMN_ID = 1  
  
  
def process_xml(var_file, checklist_points, row_counter) -> list[str | int]:  
    tree = ElementTree.parse(os.path.join(args.input_dir, var_file))  
    root = tree.getroot()  
    var_file_short = var_file.split(":")[0]  
    results = [row_counter, FIRST_EXCEL_COLUMN_ID, var_file_short]  
    logger.info(f"Openning file: {var_file_short}")  
  
    entries = root.findall('entry')  
    checklist_points_and_entries_product = product(checklist_points, entries)  
    results += chain(*map(partial(validate_products, row_counter), checklist_points_and_entries_product))  
    return results  
  
  
def validate_products(row_num, iter_product: Iterator) -> list:  
    checklist_point, entry = iter_product  
    check_handler_instance = DefualtCheckHandler(checklist_point, entry, row_num)  
    match checklist_point['TypeOfCheck']:  
        case 'FilePermissions':  
            check_handler_instance = FilePermissionCheckHandler(checklist_point, entry, row_num)  
        case 'FullMatch':  
            check_handler_instance = FullMatchCheckHandler(checklist_point, entry, row_num)  
    return check_handler_instance.validate()  
  
  
class DefualtCheckHandler:  
    def __init__(self, checklist_point, entry, row_num):  
        self.checklist_point = checklist_point  
        self.entry = entry  
        self.row_num = row_num  
        self.key = entry.find('key').text  
        self.subkey = entry.find('subkey').text  
        self.value = entry.find('value').text  
        self.datagroup = entry.find('DataGroup').text  
  
    @classmethod  
    def validate(cls) -> list:  
        return []  
  
  
class FilePermissionCheckHandler(DefualtCheckHandler):  
    def validate(self) -> list:  
        if self.datagroup != self.checklist_point['DataGroup']:  
            return []  
        if self.subkey != 'Permissions':  
            return []  
        if self.value in self.checklist_point['value']:  
            return [self.row_num, self.checklist_point['column'], '0']  
        return [self.row_num, self.checklist_point['column'], '1']  
  
  
class FullMatchCheckHandler(DefualtCheckHandler):  
    def validate(self) -> list:  
        if self.key != self.checklist_point['CheckParam']:  
            return []  
        if self.subkey != self.checklist_point['CheckParamSub']:  
            return []  
        if self.value != self.checklist_point['value']:  
            return [self.row_num, self.checklist_point['column'], '0']  
        return [self.row_num, self.checklist_point['column'], '1']
```
## Пример №2
В данном примере представлен рефакторинг функции `main`, которая которая скачивает и расшифровывает файлы, распечатывая и сохраняя результат. Во время рефакторинга были убраны излишние условные оператора, а также цикл был заменен на функцию `map`.

Цикломатическая сложность ДО и ПОСЛЕ:
```
ДО:
F 183:0 main - B (6)

ПОСЛЕ:
F 5:0 main - A (3)
F 23:0 save_data - A (1)

```

**ДО:**
```python
def main():  
    with open(RSA_PRIVATE_PATH, 'r') as fr:  
        key_data = fr.read()  
        key_data = key_data.encode()  
        if RSA_PRIVATE_PASSWORD:  
            private_rsa = serialization.load_pem_private_key(key_data, RSA_PRIVATE_PASSWORD.encode(), default_backend())  
        else:  
            private_rsa = serialization.load_pem_private_key(key_data, None, default_backend())  
  
    last_telemetry_id = -1  
    url = URL.format(last_telemetry_id + 1)  
  
    with s.get(url, verify=False, headers=HEADER) as response:  
        if (response.status_code != 200):  
            return  
        else:  
            if (response.json() is None or len(response.json()) == 0):  
                return  
            else:  
                for item in response.json():  
                    telemetry_id = item["id"]  
                    last_telemetry_id = max(last_telemetry_id, telemetry_id)  
                    key_field = item['key']  
                    key_name, crypt_aes_key_base64 = key_field.split(' ')  
                    crypt_aes_key = base64.decodebytes(crypt_aes_key_base64.encode())  
                    iv, key = get_aes_key(crypt_aes_key, key_name, private_rsa)  
  
                    with s.get(telURL.format(telemetry_id), verify=False, headers=HEADER) as response2:  
                        body_crypt = response2.content  
  
                        body = AESdecrypt(body_crypt, key, iv)  
                        _check_folder_exist('data/')  
                        _check_folder_exist(f'data/{TYPE}/')  
  
                        path = f'data/{TYPE}/telemetry_{telemetry_id}'  
                        with open(path, 'wb') as fw:  
                            fw.write(body)  
                        print(f"write {path}")  
  
```

**ПОСЛЕ:**
```python
DEFAULT_LAST_TELEMETRY_ID = -1  
  
  
def main() -> None:  
    with open(RSA_PRIVATE_PATH, 'r') as fr:  
        key_data = fr.read()  
    key_data = key_data.encode()  
    if RSA_PRIVATE_PASSWORD:  
        RSA_PRIVATE_PASSWORD = RSA_PRIVATE_PASSWORD.encode()  
  
    private_rsa = serialization.load_pem_private_key(key_data, RSA_PRIVATE_PASSWORD, default_backend())  
  
    url = telURL.format(last_telemetry_id + 1)  
  
    with s.get(url, verify=False, headers=HEADER) as r:  
        response = r  
    if response.status_code != 200:  
        return  
    result = [*(map(save_data, response.json()))]  
  
  
def save_data(item: dict) -> None:  
    telemetry_id = item["id"]  
    last_telemetry_id = max(DEFAULT_LAST_TELEMETRY_ID, telemetry_id)  
    key_field = item['key']  
    key_name, crypt_aes_key_base64 = key_field.split(' ')  
    crypt_aes_key = base64.decodebytes(crypt_aes_key_base64.encode())  
    iv, key = get_aes_key(crypt_aes_key, key_name, private_rsa)  
  
    with s.get(telURL.format(telemetry_id), verify=False, headers=HEADER) as response2:  
        body_crypt = response2.content  
  
    body = AESdecrypt(body_crypt, key, iv)  
    _check_folder_exist('data/')  
    _check_folder_exist(f'data/{TYPE}/')  
  
    path = f'data/{TYPE}/telemetry_{telemetry_id}'  
    with open(path, 'wb') as fw:  
        fw.write(body)  
    print(f"write {path}")  
```
## Пример №3
В данном примере был пеобразован метод `list_pull_store` который использовался для сохранения данных посредством батчей, с большим количеством внутренних счетчиков. После рефакторинга от излишних флагов и счетчиков удалось избавиться, кроме того, обработка данных была переведена на систему из итераторов и единого `reduce` счетчика/обработчика.

**Цикломатическая сложность ДО и ПОСЛЕ:**
```
ДО:
M 1:0 list_pull_store - B (10)


ПОСЛЕ:
M 8:4 list_pull_store.get_info_iterator - A (4)
F 20:4 list_pull_store.store_nd_count - A (3)
F 4:0 list_pull_store - A (1)
```

**ДО:**
```python
def list_pull_store(self, stid, t_count, t_type, out_dir, overwrite='STOP'):  
    total_count = 0  
    fetch_count = t_count  
    if t_count == 0:  
        fetch_count = 100  
  
    while True:  
        stored_count = 0  
        iter_count = 0  
        info = None  
        for info in self.list_info(stid, fetch_count, t_type):  
            iter_count += 1  
            logger.debug('Process: %s', info)  
            key_type = info['key'].split(' ')[0]  
            if key_type not in self._telemetry_key_types:  
                logger.debug('Unsupported key type "%s", id:"%s". skip',  
                             key_type, info['id'])  
                continue  
            try:  
                stored_count += self.store(info, out_dir, overwrite)  
            except OverwriteStopError as e:  
                logger.warning(e)  
                return total_count  
        else:  
            if info is None:  
                break  
            logger.debug('Check next bunch, t_count:%s, iter_count:%s, tid:%s',  
                         t_count, iter_count, info['id'])  
            if t_count == 0:  
                if iter_count == 0:  
                    break  
                stid = info['id'] + 1  
        total_count += stored_count  
  
    return total_count
```

**ПОСЛЕ:**
```python
from functools import reduce  
  
  
def list_pull_store(self, stid, t_count: int, t_type, out_dir, overwrite='STOP'):  
    is_tcount = bool(t_count)  
    fetch_count = t_count * is_tcount + 100 * (not is_tcount)  
  
    def get_info_iterator(curr_id):  
        while True:  
            info = None  
  
            for info in self.list_info(curr_id, fetch_count, t_type):  
                yield info  
            curr_id = info['id'] + 1  
            logger.debug('Check next bunch, t_count:%s, iter_count:%s, tid:%s',  
                         t_count, info['id'])  
            if info is None:  
                break  
  
    def store_nd_count(counter, info_unit):  
        key_type = info_unit['key'].split(' ')[0]  
        if key_type not in self._telemetry_key_types:  
            logger.debug('Unsupported key type "%s", id:"%s". skip', key_type, info_unit['id'])  
            return counter + 0  
        try:  
            return counter + self.store(info_unit, out_dir, overwrite)  
        except OverwriteStopError as e:  
            logger.warning(e)  
            return counter + 0  
  
    info_list = get_info_iterator(stid)  
    return reduce(store_nd_count, info_list, 0)
```

# Заключение
Главные выводы которые можно вынести по теме Цикломатической сложности, таковы:
* Стремиться к ЦС, равной 1, надо тем сильнее и полнее, чем дальше мы уходим в проектирование и бизнес-логику от чистого алгоритмического кодинга
* Стоит сразу же при написании любого кода минимизировать количество условных операторов, избегать `else`, как можно меньше использовать циклы, особенно вложенные.
* При снижении цикломатической сложности, некоторые полезные принципы проектирования следуют автоматически.
* Отдельно бы отметил в рамках python модули itertools и functools, которые позволяют приблизиться к парадигме функционального программирования и получить как снижение цикломатической сложности, так и плюсы функцоинального программирования сами по себе.
* Стоит уделить время для понимания концепции **ad-hoc** полиморфизма и параметрического полиморфизма, так как эти решения позволяют в разы повысить выразительность кода и убрать излишние проверки на типы.
* Минимизация количества None/null, табличная логика, механизмы дженериков, multiple dispatch - все эти варианты снижения цикломатической сложности стоит понимать и держать на готове, даже если не всегда имеется возможность применить их всех для проектов, они могут очень пригодиться в узких случаях.

# Бонус
В качестве интересного дополнения к теме цикломатической сложности, предлагаю рассмотреть интересную задачку - решить задачу FizzBuzz без условных операторов, то есть не используя оператор `if`. Сама задача звучит так - надо вывести значения от 1 до N. Если число делится на 3 и на 5, вывести FizzBuzz; если число делится на 3, вывести Fizz; если число делится на 5, вывести Buzz; иначе вывести само число.

Так выглядит стандартное решение с использование оператора `if`. **Цикломатическая сложность: `B (6)`**
```python
def fizzbuzz_basic(n: int):
    for i in range(1, n+1):
        if (i % 3) == 0 and (i % 5) == 0:
            print('FizzBuzz')
            continue
        if (i % 3) == 0:
            print('Fizz')
            continue
        if (i % 5) == 0:
            print('Buzz')
            continue
        print(i)
```

Версия без использования оператора `if`. Основная идея в том, чтобы сразу заполнить весь список числами, а затем заменить на строки те, которые соответствуют определенным критериям, но не с помощью условий а с помощью итерации только по нужным позициям. **Цикломатическая сложность: `A (5)`**
```python
def fizzbuzz_listbased(n: int):
    result_lst: list = [i for i in range(1, n+1)]
    for i in range(2, n, 3):
        result_lst[i] = 'Fizz'
    for i in range(4, n, 5):
        result_lst[i] = 'Buzz'
    for i in range(14, n, 15):
        result_lst[i] = 'FizzBuzz'
    print(*result_lst, sep='\n')
```

Последняя версия примечательна тем что имеет низкую цикломатическую сложность, а именно `A (2)`. Однако, это достигается благодаря использованию особенностей взаимодействия типов в python. Например, если умножить `False` на строку тип `str` то итогом будет пустая строка `'text' * False = ''`. По итогу, в рамках такого решения radon покажет низкую цикломатическую сложность, когда по факту она снизится не настолько сильно, отчего эффективность использования подобных способов понижения цикломатической сложности - под вопросом.
```python
def fizzbuzz_(n: int):
    for a in range(n+1):
        fizz: str = (1 - (a % 3)) * "Fizz"
        buzz: str = (1 - (a % 5)) * "Buzz"
        num: str = str(a) * bool(a % 3) * bool(a % 5)
        print(fizz + buzz + num)
```