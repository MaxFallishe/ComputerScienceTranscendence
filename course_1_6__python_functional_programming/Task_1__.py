# 1. Пример проекта из личной практики в котором можно было бы избавиться от хранения промежуточных  результатов и
# использования промежуточных результатов:
# Одним из актуальных примеров по удалению промежуточных значений, для меня было хранение значений
# стейта  в базе данных. Однако, когда какое-то значение (состояние) уже хранится в базе данных, например количество
# выгруженных/скачанных блоков телеметрии это образует новые трудности и как будто бы не может быть эталонным вариантом.
# Далее было бы неплохо привести всё к такой ситуации, где нужные значения могли бы очень быстро вычисляться,
# либо же постоянно кэшироваться.
#
#
# 2. Измышления о том, как в принципе может быть устроена архитектура сложной системы, где промежуточные состояния
# вычислений вообще никак не хранятся в явном виде (за исключением каких-то специфичных настроек):
# При первом взгляде на данную задачу, не появляется простого и интуитивного решения. Однако мне удалось сформулировать
# несколько потенциально-возможных идей, благодаря которым можно было бы свести к минимуму количество промежуточных
# состояний.в сложной системе. К примеру, возможно, с этой задачей могли бы помочь персистентные структуры данных,
# которые сами по себе уже имели бы функционал по получению любых потенциально интересущих нас статистических значений
# без необходимости того, чтобы явно их задавать. Также мне вспоминается концепция из Data Engineering, которая
# назвается DAG (Directed Acyclic Graph). Данная концепция может служить полноправным примером реализации для задач
# по типу сохранения метрик или телеметрии, однако если попровать модифицировать данных подход и сделать графы
# цикличными, возможно это могло бы помочь с минимизацией количества состояний в проекте, без излишних
# значимых обременений.



# 1. An example of a project from personal practice in which it would be possible to get rid of storing intermediate
# results and using intermediate results:
# One of the actual examples of removing intermediate values in my projects, for me, was storing values in a database.
# However, when some value (state) it is already stored in the database, for example, the number of uploaded/downloaded
# telemetry blocks, this creates new difficulties and, as if, cannot be a reference option.
# Next, it would be nice to bring everything to a situation where the necessary values could be calculated very
# quickly, or/and constantly cached.
#
#
# 2. Inventions about how, in principle, the architecture of a complex system can be arranged, where intermediate states
# calculations are not stored explicitly in any way (except for some specific settings):
# When you first look at this task, there is no simple and intuitive solution.
# However, I managed to formulate
# several potentially possible ideas, thanks to which it would be possible to minimize the number of intermediates
# of states.in a complex system.
# For example, perhaps persistent data structures could help with this task,
# which by themselves would already have the functionality to get any potentially interesting statistical values
# without having to explicitly set them.
# I also remember a concept from Data Engineering that
# is called DAG (Directed Acyclic Graph).
# This concept can serve as a full-fledged implementation example for tasks
# by the type of saving metrics or telemetry, however, if you plan to modify the data approach and make graphs
# cyclical, perhaps this could help with minimizing the number of states in the project, without unnecessary
# significant encumbrances.

