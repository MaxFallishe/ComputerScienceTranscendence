--  1. Get information about all the dwarves who belong to any squad, along with information about their squads.
SELECT dwarves.*, squads.name, squads.mission
FROM dwarves
LEFT JOIN squads ON dwarves.squad_id = squads.squad_id
WHERE dwarves.squad_id IS NOT NULL;

-- 2. Find all the dwarves with the profession "miner" who are not in any squad.
SELECT *
FROM dwarves
WHERE profession='Miner' AND squad_id IS NULL;

-- 3. Get all the tasks with the highest priority that are in the "pending" status.
SELECT *
FROM tasks
WHERE priority = (SELECT MAX(tasks.priority) FROM tasks)
  AND status = 'pending';

-- 4. For each dwarf who owns at least one item, get the number of items he owns.
SELECT d.name AS dwarf_name, COUNT(*) AS dwarf_items_count
FROM items i
JOIN dwarves d on i.owner_id = d.dwarf_id
GROUP BY d.name;

-- 5. Get a list of all squads and the number of dwarves in each squad. Also include non-dwarf units in the output.
SELECT s.name, COUNT(d.dwarf_id) as dwarf_count
FROM squads s
LEFT JOIN dwarves d on s.squad_id = d.squad_id
GROUP BY s.squad_id;

-- 6. Get a list of professions with the most unfinished tasks ("pending" and "in_progress") from the dwarves of these professions.
SELECT d.profession, COUNT(t.task_id) AS undone_tasks_num
FROM tasks t
         JOIN dwarves d on t.assigned_to = d.dwarf_id
WHERE status IN ('in_progress', 'pending')
GROUP BY d.profession
ORDER BY undone_tasks_num DESC;

-- 7. For each type of item, find out the average age of the dwarves who own these items.
SELECT i.type, avg(d.age)
FROM dwarves d
RIGHT JOIN items i on i.owner_id = d.dwarf_id
GROUP BY i.type;

-- 8. Find all dwarves over the average age (according to all dwarves in the database) who do not own any subjects.
WITH avg_age AS (SELECT AVG(age) AS value FROM dwarves)
SELECT d.name, d.age, avg_age.value AS avg_dwarf_age
FROM items i
         RIGHT JOIN dwarves d on i.owner_id = d.dwarf_id
CROSS JOIN avg_age
WHERE item_id is NULL
  AND d.age > avg_age.value;