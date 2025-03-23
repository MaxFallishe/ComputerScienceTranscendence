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



-- TABLES FOR TASKS --
CREATE TABLE Squads (
    squad_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mission VARCHAR(100) NOT NULL
);

CREATE TABLE Dwarves (
    dwarf_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    profession VARCHAR(100) NOT NULL,
    squad_id INT NULL,
    CONSTRAINT fk_squad FOREIGN KEY (squad_id) REFERENCES Squads(squad_id) ON DELETE SET NULL
);

CREATE TABLE Tasks (
    task_id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    priority INT NOT NULL,
    assigned_to INT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('pending', 'in_progress', 'completed')),
    CONSTRAINT fk_assigned_to FOREIGN KEY (assigned_to) REFERENCES Dwarves(dwarf_id) ON DELETE SET NULL
);

CREATE TABLE Items (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL CHECK (type IN ('weapon', 'armor', 'tool')),
    owner_id INT NULL,
    CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES Dwarves(dwarf_id) ON DELETE SET NULL
);



-- MOCK DATA --
INSERT INTO Squads (name, mission) VALUES
('Iron Fangs', 'Mine deep veins of mithril'),
('Stone Guards', 'Defend the mountain stronghold'),
('Ember Forgers', 'Craft legendary weapons and armor'),
('Shadow Delvers', 'Explore ancient underground ruins'),
('Ale Brewers', 'Brew the finest dwarven mead');

INSERT INTO Dwarves (name, age, profession, squad_id) VALUES
('Thrain Ironfist', 140, 'Miner', 1),
('Borin Stonehelm', 132, 'Blacksmith', 3),
('Gimli Firebeard', 120, 'Warrior', 2),
('Durin Deepdelver', 150, 'Explorer', 4),
('Thorin Aleborn', 110, 'Brewer', 5),
('Dwalin Ironfoot', 145, 'Guard', 2),
('Balin Oakenshield', 135, 'Carpenter', NULL),
('Oin Hammerhand', 125, 'Engineer', 3),
('Gloin Goldseeker', 138, 'Jeweler', 1),
('Fili Brightaxe', 118, 'Scout', 4);

INSERT INTO Tasks (description, priority, assigned_to, status) VALUES
('Extract mithril from the eastern tunnel', 5, 1, 'in_progress'),
('Forge a new batch of battleaxes', 4, 2, 'pending'),
('Guard the western gate during the night', 3, 3, 'in_progress'),
('Scout the abandoned dwarven halls', 5, 4, 'pending'),
('Prepare a new batch of fire ale', 2, 5, 'completed'),
('Reinforce the northern wall', 4, 6, 'in_progress'),
('Repair the wooden bridge over the lava stream', 3, 7, 'pending'),
('Install a new water pump in the lower mines', 4, 8, 'completed'),
('Evaluate gold purity in the latest haul', 3, 9, 'in_progress'),
('Map the hidden tunnels beyond the Great Cavern', 5, 10, 'pending');

INSERT INTO Items (name, type, owner_id) VALUES
('Battle Axe of the Ancestors', 'weapon', 3),
('Mithril Chainmail', 'armor', 6),
('Runed Pickaxe', 'tool', 1),
('Emberforged Hammer', 'weapon', 2),
('Sturdy Oak Shield', 'armor', 3),
('Crystal Flask', 'tool', 5),
('Engraved Smithing Tongs', 'tool', 2),
('Diamond-bladed Knife', 'weapon', 9),
('Golden Compass', 'tool', 10),
('Ancient Dwarven Helmet', 'armor', 7);
