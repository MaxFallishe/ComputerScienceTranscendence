// At this point in the development of the project, you can see that FindMatches has a hard-wired logic for determining combinations.
// A springboard has already been laid for the extensibility of this logic through the Match type, although it cannot be called flexible enough. It is good and even necessary
// to include scaling of this moment at the design stage, but at the same time, if the project context implies
// a fork between 2-5 types of entities, you can do with a local solution without additional abstraction.
// If we imagine that in our example there is a need to follow the path of implementation through strong abstraction, we can
// use the "Strategy" pattern to separate the processing paths, or rather to find a combination of the desired pattern. Each
// type of combination will get its own type and, depending on the type, the code responsible for searching for combinations will be called. If there is
// a need to implement several patterns at the same time, it will be possible to call methods one after another with the necessary priority (not forgetting about deleting elements so that there are no intersections/conflicts between the found combinations.


// На данный момент разработки проекта можно заметить что FindMatches имеет жестко заложенную логику определения комбинаций.
// Уже заложен трамплин для расширяемости этой логики через тип Match, хоть его и нельзя назвать достаточно гибким. Хорошо и даже необходимо
// заложить на этапе проектирования масштабирование данного момента, но в то же время если контекст проекта подразумевает
// развилку между 2-5 видами сущностей можно обойтись и локальным решением без дополнительной абстракции.
// Если представить что в нашем примере есть необходимость пойти по пути реализации через сильную абстракцию - мы можем
// использовать паттерн "Стретегия для разделения" путей обработки, а точнее нахождения комбинации нужного паттерна. Каждый
// тип комбинации получит свой тип и в зависимости от типа будет вызываться код отвечающий за поиск комбинаций, если будет
// необходимость в реализации поска нескольких паттернов одновременно - то можно будет вызывать методы один за другим с нужным приоритетом (не забывая про удаления элементов чтобы не было пересечений/конфликтов между найденными комбинациями.