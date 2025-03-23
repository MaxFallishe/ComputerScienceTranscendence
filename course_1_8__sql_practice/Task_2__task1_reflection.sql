--  1. Get information about all the dwarves who belong to any squad, along with information about their squads.
-- REFERENCE
SELECT
   D.name AS DwarfName,
   D.profession AS Profession,
   S.name AS SquadName,
   S.mission AS Mission
FROM
   Dwarves D
JOIN
   Squads S
ON
   D.squad_id = S.squad_id;


-- AlTERNATIVE SOLUTION
SELECT
    dwarves.*,
    squads.name,
    squads.mission
FROM
    dwarves
LEFT JOIN
    squads
ON
    dwarves.squad_id = squads.squad_id
WHERE
    dwarves.squad_id IS NOT NULL;

-- REFLECTION COMMENT
    -- EN:
        -- The main significant difference between the reference version is the use of "JOIN" (INNER JOIN) in its structure,
        -- which allows you to avoid using the WHERE construction when selecting dwarves who do not belong to any squad.
    -- RU:
        -- Основным значимым отличием эталонного варианта является использование в своей структуре "JOIN" (INNER JOIN)
        -- который позволяет отказаться от использования  конструкции WHERE при выборке дворфов которые не состоят ни в
        -- каком-либо отряде.


-- 2. Find all the dwarves with the profession "miner" who are not in any squad.
-- REFERENCE
SELECT
   name,
   age
FROM
   Dwarves
WHERE
   profession = 'miner' AND squad_id IS NULL;

-- AlTERNATIVE SOLUTION
SELECT
    *
FROM
    dwarves
WHERE
    profession='Miner' AND squad_id IS NULL;

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.


-- 3. Get all the tasks with the highest priority that are in the "pending" status.
-- REFERENCE
SELECT
   task_id,
   description,
   assigned_to
FROM
   Tasks
WHERE
   priority = (SELECT MAX(priority) FROM Tasks WHERE status = 'pending')
   AND status = 'pending';

-- AlTERNATIVE SOLUTION
SELECT
    *
FROM
    tasks
WHERE
    priority = (SELECT MAX(tasks.priority) FROM tasks)
    AND status = 'pending';

-- REFLECTION COMMENT
    -- EN:
        -- Unlike the reference solution, there is no additional "WHERE status = 'pending'" instruction when searching
        -- for the maximum priority value among Tasks objects. This difference will lead to incorrect operation of the
        -- intended SQL query.
    -- RU:
        -- В отличие от эталонного решения, отсутствует дополнительная инструкция "WHERE status = 'pending'" при поиске
        -- значения максимального приоритета среди объектов Tasks. Данное отличие будет приводить к некорректной работе
        -- предполагаемого SQL запроса.


-- 4. For each dwarf who owns at least one item, get the number of items he owns.
-- REFERENCE
SELECT
   D.name AS DwarfName,
   D.profession AS Profession,
   COUNT(I.item_id) AS ItemCount
FROM
   Dwarves D
JOIN
   Items I
ON
   D.dwarf_id = I.owner_id
GROUP BY
   D.dwarf_id, D.name, D.profession;


-- AlTERNATIVE SOLUTION
SELECT
    d.name AS dwarf_name,
    COUNT(*) AS dwarf_items_count
FROM
    items i
JOIN
    dwarves d
ON
    i.owner_id = d.dwarf_id
GROUP BY
    d.name;

-- REFLECTION COMMENT
    -- EN:
        -- An important difference between the reference version is the grouping using GROUP BY across multiple columns,
        -- not just the name column. Because of this, when executing an alternative SQL query, there may be a situation
        -- with incorrect counting if there are two dwarves with the same name in the table.
    -- RU:
        -- Важным отличием эталонного варианта является группировка с помощью GROUP BY по нескольким столбцам,
        -- а не только по столбцу name. Из-за этого при выполнении альтернативного SQL запроса возможна ситуация
        -- с неправильным подсчетом при наличии в таблице двух дворфов с одинаковыми именами.


-- 5. Get a list of all squads and the number of dwarves in each squad. Also include non-dwarf units in the output.
-- REFERENCE
SELECT
   S.squad_id,
   S.name AS SquadName,
   COUNT(D.dwarf_id) AS NumberOfDwarves
FROM
   Squads S
LEFT JOIN
   Dwarves D
ON
   S.squad_id = D.squad_id
GROUP BY
   S.squad_id, S.name;

-- AlTERNATIVE SOLUTION
SELECT
    s.name,
    COUNT(d.dwarf_id) as dwarf_count
FROM
    squads s
LEFT JOIN
    dwarves d
ON
    s.squad_id = d.squad_id
GROUP BY
    s.squad_id;

-- REFLECTION COMMENT
    -- EN:
        -- There are no serious differences with the reference version, however, it is worth noting that the alternative
        -- version does not explicitly specify the grouping by the name of the dwarf, which is often not a critical
        -- point, but it is always better to explicitly specify the grouping for all fields that are used in the SELECT
        -- construct (fields with which the COUNT aggregate is used, etc. may not participate in the grouping)
    -- RU:
        -- Серьезных отличий с эталонным вариантом нет, однако стоит заметить что в альтернативном варианте явно
        -- не указана группировка и по имени дворфа, часто не является критичным моментом, но лучше всегда явно
        -- указывать группировку по всем полям которые исопльзуются в SELECT конструкции (поля с которыми вместе
        -- используются агрегат COUNT и т.д. могут не участвовать в группировке)


-- 6. Get a list of professions with the most unfinished tasks ("pending" and "in_progress") from the dwarves of these professions.
-- REFERENCE
SELECT
   D.profession,
   COUNT(T.task_id) AS UnfinishedTasksCount
FROM
   Dwarves D
JOIN
   Tasks T
ON
   D.dwarf_id = T.assigned_to
WHERE
   T.status IN ('pending', 'in_progress')
GROUP BY
   D.profession
ORDER BY
   UnfinishedTasksCount DESC;

-- AlTERNATIVE SOLUTION
SELECT
    d.profession,
    COUNT(t.task_id) AS undone_tasks_num
FROM
    tasks t
JOIN
    dwarves d
ON
    t.assigned_to = d.dwarf_id
WHERE
    status IN ('in_progress', 'pending')
GROUP BY
    d.profession
ORDER BY
    undone_tasks_num DESC;

-- REFLECTION COMMENT
    -- EN:
        -- There are no important differences with the reference version. There are differences in the order of the
        -- table for the FROM and JOIN constructions, but since INNER JOIN is used, there will
        -- be no difference in the result.
    -- RU:
        -- Важных отличий с эталонным вариантом нет. Имеются отличия в порядке таблице для конструкций FROM и JOIN,
        -- но так как используется INNER JOIN разницы в результате не будет.


-- 7. For each type of item, find out the average age of the dwarves who own these items.
-- REFERENCE
SELECT
   I.type AS ItemType,
   AVG(D.age) AS AverageAge
FROM
   Items I
JOIN
   Dwarves D
ON
   I.owner_id = D.dwarf_id
GROUP BY
   I.type;

-- AlTERNATIVE SOLUTION
SELECT
    i.type,
    avg(d.age)
FROM
    dwarves d
RIGHT JOIN
    items i
ON
    i.owner_id = d.dwarf_id
GROUP BY
    i.type;

-- REFLECTION COMMENT
    --  EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.

-- 8. Find all dwarves over the average age (according to all dwarves in the database) who do not own any subjects.
-- REFERENCE
SELECT
   D.name,
   D.age,
   D.profession
FROM
   Dwarves D
WHERE
   D.age > (SELECT AVG(age) FROM Dwarves)
   AND D.dwarf_id NOT IN (SELECT owner_id FROM Items);

-- AlTERNATIVE SOLUTION
WITH
    avg_age AS (SELECT AVG(age) AS value FROM dwarves)
SELECT
    d.name,
    d.age,
    avg_age.value AS avg_dwarf_age
FROM
    items i
RIGHT JOIN
    dwarves d
ON
    i.owner_id = d.dwarf_id
CROSS JOIN
    avg_age
WHERE
    item_id is NULL AND d.age > avg_age.value;

-- REFLECTION COMMENT
    --  EN:
        -- The reference version is more concise with respect to the task at hand, it does not use unnecessary JOIN and
        -- WITH constructions. The alternative option is also valid, but quite voluminous. The main advantage of the
        -- reference version is in the line "AND D.dwarf_id NOT IN (SELECT owner_id FROM Items);" which allows you to
        -- replace the use of RIGHT JOIN.
    -- RU:
        -- Эталонный вариант является более лаконичным  относительно поставленной задачей, в нем не используется
        -- излишних JOIN и WITH конструкций. Альтернативный вариант также валиден, но достаточно объемный.
        -- Основное преимущество эталонного варианта в строчке "AND D.dwarf_id NOT IN (SELECT owner_id FROM Items);"
        -- что позволяет заменить использование RIGHT JOIN.
