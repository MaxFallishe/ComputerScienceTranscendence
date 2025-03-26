-- 1. Find all the squads that don't have a leader.
-- REFERENCE
SELECT *
FROM Squads
WHERE leader_id IS NULL;

-- AlTERNATIVE SOLUTION
SELECT name
FROM squads s
WHERE s.leader_id IS NULL;

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.


-- 2. Get a list of all dwarves over the age of 150 who have the profession of "Warrior".
-- REFERENCE
SELECT *
FROM Dwarves
WHERE age > 150
  AND profession = 'Warrior';

-- AlTERNATIVE SOLUTION
SELECT name
FROM dwarves
WHERE age > 150
  AND profession = 'Warrior';

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.


-- 3. Find dwarves who have at least one "weapon" type item.
-- REFERENCE
SELECT DISTINCT D.*
FROM Dwarves D
         JOIN Items I
         ON D.dwarf_id = I.owner_id
WHERE I.type = 'weapon';

-- AlTERNATIVE SOLUTION
SELECT DISTINCT d.name
FROM dwarves d
         JOIN items i
         ON d.dwarf_id = i.owner_id
WHERE i.type = 'weapon';

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.


-- 4. Get the number of tasks for each dwarf by grouping them by status.
-- REFERENCE
SELECT assigned_to, status, COUNT(*) AS task_count
FROM Tasks
GROUP BY assigned_to, status;

-- AlTERNATIVE SOLUTION
SELECT d.dwarf_id, d.name, t.status, COUNT(t.status)
FROM dwarves d
JOIN tasks t
ON d.dwarf_id = t.assigned_to
GROUP BY d.dwarf_id, t.status;

-- REFLECTION COMMENT
    -- EN:
        -- The main difference is the presence of an additional JOIN operand in the alternative implementation,
        -- however, this is a consequence of the presence of the dwarf's name in the final table.
        -- There are no significant differences with the reference version.
    -- RU:
        -- Основное отличие заключается в наличии дополнительного операнда JOIN в альтернативной реализации,
        -- однако это следствие наличие имени дворфа в итоговой таблице. Значимых отличий с эталонным вариантом нет.


-- 5. Find all the tasks that were assigned to the dwarves from the squad named "Guardians".
-- REFERENCE
SELECT T.*
FROM Tasks T
         JOIN Dwarves D
             ON T.assigned_to = D.dwarf_id
         JOIN Squads S
             ON D.squad_id = S.squad_id
WHERE S.name = 'Guardians';

-- AlTERNATIVE SOLUTION
SELECT t.task_id, t.description, t.status, s.name
FROM tasks t
         JOIN dwarves d
              ON t.assigned_to = d.dwarf_id
         LEFT JOIN squads s
                   ON d.squad_id = s.squad_id
WHERE s.name = 'Guardians';

-- REFLECTION COMMENT
    -- EN:
        -- The only difference between the reference version and the alternative one is in the construction of
        -- "JOIN Squad S" and "LEFT JOIN squads s". Within the query, LEFT is redundant.
    -- RU:
        -- Единственное отличие между эталонным вариантом и альтернативным в конструкции "JOIN Squad S"
        -- и "LEFT JOIN squads s", в рамках запроса LEFT является избыточным.


-- 6. Bring out all the dwarves and their closest relatives, indicating the type of kinship relationship.
-- REFERENCE
SELECT
    D1.name AS dwarf_name,
    D2.name AS relative_name,
    R.relationship
FROM Relationships R
         JOIN Dwarves D1
             ON R.dwarf_id = D1.dwarf_id
         JOIN Dwarves D2
             ON R.related_to = D2.dwarf_id;

-- AlTERNATIVE SOLUTION
SELECT d.dwarf_id  AS dwarf_id,
       d.name      AS dwarf_name,
       r.relationship,
       d2.dwarf_id AS relate_dwarf_id,
       d2.name     AS relate_dwarf_name
FROM relationships r
         JOIN dwarves d
              ON r.dwarf_id = d.dwarf_id
         JOIN dwarves d2
              ON r.related_to = d2.dwarf_id;

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution.
    -- RU:
        -- Значимых отличий с эталонным решением нет.
