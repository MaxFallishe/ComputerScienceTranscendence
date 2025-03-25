-- 1. Find all the squads that don't have a leader.
SELECT name
FROM squads
WHERE leader_id IS NULL;

-- 2. Get a list of all dwarves over the age of 150 who have the profession of "Warrior".
SELECT name
FROM dwarves
WHERE age > 150
  AND profession = 'Warrior';

-- 3. Find dwarves who have at least one "weapon" type item.
SELECT DISTINCT d.name
FROM dwarves d
         JOIN items i
         ON d.dwarf_id = i.owner_id
WHERE i.type = 'weapon';

-- 4. Get the number of tasks for each dwarf by grouping them by status.
SELECT d.dwarf_id, d.name, t.status, COUNT(t.status)
FROM dwarves d
JOIN tasks t
ON d.dwarf_id = t.assigned_to
GROUP BY d.dwarf_id, t.status;

-- 5. Find all the tasks that were assigned to the dwarves from the squad named "Guardians".
SELECT t.task_id, t.description, t.status, s.name
FROM tasks t
JOIN dwarves d
ON t.assigned_to = d.dwarf_id
LEFT JOIN squads s
ON d.squad_id = s.squad_id
WHERE s.name = 'Guardians';

-- 6. Bring out all the dwarves and their closest relatives, indicating the type of kinship relationship.
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


-- TABLES FOR TASKS --
CREATE TABLE Dwarves (
    dwarf_id   INT PRIMARY KEY,
    name       VARCHAR(100),
    age        INT,
    profession VARCHAR(100),
    squad_id   INT
);


CREATE TABLE Squads (
    squad_id  INT PRIMARY KEY,
    name      VARCHAR(100),
    leader_id INT REFERENCES Dwarves(dwarf_id)
);


CREATE TABLE Tasks (
    task_id     INT PRIMARY KEY,
    description TEXT,
    assigned_to INT REFERENCES Dwarves(dwarf_id),
    status      VARCHAR(50)
);


CREATE TABLE Items (
    item_id  INT PRIMARY KEY,
    name     VARCHAR(100),
    type     VARCHAR(50),
    owner_id INT REFERENCES Dwarves(dwarf_id)
);


CREATE TABLE Relationships (
    dwarf_id     INT REFERENCES Dwarves(dwarf_id),
    related_to   INT REFERENCES Dwarves(dwarf_id),
    relationship VARCHAR(50)
);


INSERT INTO Dwarves (dwarf_id, name, age, profession, squad_id) VALUES
(1, 'Thorin', 195, 'Warrior', 1),
(2, 'Balin', 178, 'Lookout', 1),
(3, 'Dwalin', 210, 'Guard', 1),
(4, 'Fili', 82, 'Scout', 2),
(5, 'Kili', 77, 'Archer', 2),
(6, 'Oin', 200, 'Healer', 2),
(7, 'Gloin', 199, 'Smith', NULL),
(8, 'Bifur', 190, 'Miner', 3),
(9, 'Bofur', 185, 'Cook', 3),
(10, 'Bombur', 170, 'Cook', 3),
(11, 'Dori', 195, 'Musician', NULL),
(12, 'Nori', 180, 'Thief', NULL),
(13, 'Ori', 175, 'Scribe', NULL);


INSERT INTO Squads (squad_id, name, leader_id) VALUES
(1, 'Mountain Breakers', 1),
(2, 'Guardians', 4),
(3, 'Stone Eaters', 8),
(4, 'Relics Owners', NULL),
(5, 'Drop static', NULL);


INSERT INTO Tasks (task_id, description, assigned_to, status) VALUES
(1, 'Guard the entrance', 1, 'in_progress'),
(2, 'Scout the eastern path', 4, 'pending'),
(3, 'Cook meals for the squad', 10, 'completed'),
(4, 'Repair the forge', 7, 'in_progress'),
(5, 'Collect herbs', 6, 'pending'),
(6, 'Patrol the mines', 3, 'completed'),
(7, 'Sharpen weapons', NULL, 'pending'),
(8, 'Translate ancient scroll', 13, 'in_progress'),
(9, 'Secure the camp perimeter', 2, 'completed'),
(10, 'Deliver message to elves', 5, 'pending'),
(11, 'Brew healing potion', 6, 'in_progress'),
(12, 'Map the underpass', 12, 'in_progress'),
(13, 'Test new armor set', NULL, 'pending'),
(14, 'Test beer', 2, 'completed');

INSERT INTO Items (item_id, name, type, owner_id) VALUES
(1, 'Orcrist', 'weapon', 1),
(2, 'Mithril Vest', 'armor', 4),
(3, 'Healing Kit', 'tool', 6),
(4, 'Battle Axe', 'weapon', 3),
(5, 'Cooking Pot', 'tool', 10),
(6, 'Map Scroll', 'tool', 12),
(7, 'Crossbow', 'weapon', 5),
(8, 'Mining Pick', 'tool', 8),
(9, 'Anvil Hammer', 'tool', 7),
(10, 'Spyglass', 'tool', NULL),
(11, 'Thief’s Dagger', 'weapon', 12),
(12, 'Scribe’s Quill', 'tool', 13),
(13, 'Steel Shield', 'armor', 2),
(14, 'Magic Ring', 'tool', NULL);

INSERT INTO Relationships (dwarf_id, related_to, relationship) VALUES
(1, 2, 'Brother'),
(1, 3, 'Brother'),
(4, 5, 'Brother'),
(2, 6, 'Friend'),
(6, 7, 'Spouse'),
(8, 9, 'Brother'),
(10, 9, 'Brother'),
(11, 12, 'Brother'),
(12, 13, 'Brother'),
(3, 1, 'Brother'),
(5, 4, 'Brother'),
(13, 11, 'Friend');
