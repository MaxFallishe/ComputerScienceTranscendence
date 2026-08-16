// Continuing the discussion of designing the Match type and its subsequent processing mechanism, but now taking into account
// the principle that “code should follow from data structures, not the other way around,” it is worth considering the possibility
// of using a pattern in conjunction with the data structure in a more explicit way. The “strategy” pattern is quite versatile,
// even when the logic of the Match data structure’s implementation changes. The most obvious way to implement a representation of a combination i
// s to use an array with filled and empty cells that would constitute the “pattern” of the combination; however,
// this implementation seems overly complex from the perspective of the code that would implement it (the upper limit of the time complexity will also be potentially high).
// If we think about the task of selecting the appropriate abstraction, it makes sense to first consider trees,
// but they are not suitable here because there are difficulties with representing the field and combinations
// in the form of trees (with an efficient final structure for searching combinations).
// Therefore, one of the best solutions here may be to use graphs (albeit slightly modified);
// each node will be connected to a maximum of 8 other nodes (the four nearest nodes — above, below, to the right, to the left, and four more diagonally).
// Thanks to this, we will be able to search for combinations in the form of small graphs on a field that will represent one large graph.


// Продолжая обсуждение проектирования типа Match и механизма его последующей обработки, но теперь с учетом принципа
// что "код должен следовать из структур данных, а не наоборот", следует рассмотреть возможность использования паттерна
// в совокупности со структорой данных более явно. Паттерн "стратегия" достаточно универсален даже при смене
// логики устройства структуры данных Match. Самый явный вариант реализации представления комбинации - это массив с заполненными
// и пустыми ячейкамм которые бы и составляли "рисунок" комбинации, однако данная реализация выглядит избыточно сложной
// с точки зрения кода который будет её воплощать (верхний пределе временной сложности также будет потенциально высок).
// Если размышлять о задаче подбора нужной абстракции - релевантно первым делом подумать о деревьях, но здесь они не подходят
// так как есть сложности с репрезентацией поля и комбинаций в виде деревьев (с эффективной итоговой структурой для поиска комбинаций).
// Поэтому одно из лучших решений здесь может быть использование графов (но немного модифицированных), каждый узел будет соединен
// с максимум 8 другими узлами (четыре ближайших узла - сверху, снизу, справа, слева и ещё четыре по диагонали). Благодаря этому
// мы сможем искать комбинации в виде небольших графов на поле, которое будет представлять из себя один большой граф.