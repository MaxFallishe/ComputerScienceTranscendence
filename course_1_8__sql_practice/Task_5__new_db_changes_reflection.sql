-- Reflection on sql queries that return ready-made json objects.
-- Рефлексия об sql запросах возвращающих готовые json объекты.


--------------------------------------  EN --------------------------------------
-- If we want to get a json result, similar to what is shown below within MySQL, we can use
-- JSON_OBJECT and JSON_ARRAYAGG constructs.
-- [
--   {
--     "fortress_id": 1,
--     "name": "Mountainhome",
--     "location": "Eastern Mountains",
--     "founded_year": 205,
--     "related_entities": {
--       "dwarf_ids": [101, 102, 103, 104, 105],
--       "resource_ids": [201, 202, 203],
--       "workshop_ids": [301, 302],
--       "squad_ids": [401]
--     }
--   }
-- ]

-- An example of a query that returns a response in json format (for MySQL):
-- SELECT
--     f.fortress_id,
--     f.name,
--     f.location,
--     f.founded_year,
--     JSON_OBJECT(
--         'dwarf_ids', (
--             SELECT JSON_ARRAYAGG(d.dwarf_id)
--             FROM dwarves d
--             WHERE d.fortress_id = f.fortress_id
--         ),
--         'resource_ids', (
--             SELECT JSON_ARRAYAGG(fr.resource_id)
--             FROM fortress_resources fr
--             WHERE fr.fortress_id = f.fortress_id
--         ),
--         'workshop_ids', (
--             SELECT JSON_ARRAYAGG(w.workshop_id)
--             FROM workshops w
--             WHERE w.fortress_id = f.fortress_id
--         ),
--         'squad_ids', (
--             SELECT JSON_ARRAYAGG(s.squad_id)
--             FROM military_squads s
--             WHERE s.fortress_id = f.fortress_id
--         )
--     ) AS related_entities
-- FROM
--     fortresses f;


-- The main conclusions:
-- JSON_OBJECT generates a json object from the results of an SQL query, and adaptively enough, there is no need
-- specify the conversion rules for each field in detail. The JSON_ARRAY AGG function is needed to generate
-- a list in json notation consisting of several elements.

-- An analog of a query in Postgres syntax:
-- SELECT jsonb_agg(result)
-- FROM (
--     SELECT
--         f.fortress_id,
--         f.name,
--         f.location,
--         f.founded_year,
--         jsonb_build_object(
--             'dwarf_ids', (
--                 SELECT jsonb_agg(d.dwarf_id)
--                 FROM dwarves d
--                 WHERE d.fortress_id = f.fortress_id
--             ),
--             'resource_ids', (
--                 SELECT jsonb_agg(fr.resource_id)
--                 FROM fortress_resources fr
--                 WHERE fr.fortress_id = f.fortress_id
--             ),
--             'workshop_ids', (
--                 SELECT jsonb_agg(w.workshop_id)
--                 FROM workshops w
--                 WHERE w.fortress_id = f.fortress_id
--             ),
--             'squad_ids', (
--                 SELECT jsonb_agg(s.squad_id)
--                 FROM military_squads s
--                 WHERE s.fortress_id = f.fortress_id
--             )
--         ) AS related_entities
--     FROM fortresses f
-- ) AS result;



--------------------------------------  RU --------------------------------------
-- Если мы хотим получить json результат, подобно тому что показан ниже в рамках MySQL мы можем использовать
-- конструкции JSON_OBJECT и JSON_ARRAYAGG.
-- [
--   {
--     "fortress_id": 1,
--     "name": "Mountainhome",
--     "location": "Eastern Mountains",
--     "founded_year": 205,
--     "related_entities": {
--       "dwarf_ids": [101, 102, 103, 104, 105],
--       "resource_ids": [201, 202, 203],
--       "workshop_ids": [301, 302],
--       "squad_ids": [401]
--     }
--   }
-- ]

-- Пример запроса который вернет ответ в json формате (для MySQL):
-- SELECT
--     f.fortress_id,
--     f.name,
--     f.location,
--     f.founded_year,
--     JSON_OBJECT(
--         'dwarf_ids', (
--             SELECT JSON_ARRAYAGG(d.dwarf_id)
--             FROM dwarves d
--             WHERE d.fortress_id = f.fortress_id
--         ),
--         'resource_ids', (
--             SELECT JSON_ARRAYAGG(fr.resource_id)
--             FROM fortress_resources fr
--             WHERE fr.fortress_id = f.fortress_id
--         ),
--         'workshop_ids', (
--             SELECT JSON_ARRAYAGG(w.workshop_id)
--             FROM workshops w
--             WHERE w.fortress_id = f.fortress_id
--         ),
--         'squad_ids', (
--             SELECT JSON_ARRAYAGG(s.squad_id)
--             FROM military_squads s
--             WHERE s.fortress_id = f.fortress_id
--         )
--     ) AS related_entities
-- FROM
--     fortresses f;


-- Основные выводы:
-- JSON_OBJECT формирует json объект из результатов SQL запроса, причем достаточно адаптивно, нет необходимости
-- детально прописывать правила преобразования для каждого поля. Функция JSON_ARRAYAGG нужна для формирования
-- списка в json нотации из нескольких элементов.

-- Аналог запроса в синтаксисе Postgres:
-- SELECT jsonb_agg(result)
-- FROM (
--     SELECT
--         f.fortress_id,
--         f.name,
--         f.location,
--         f.founded_year,
--         jsonb_build_object(
--             'dwarf_ids', (
--                 SELECT jsonb_agg(d.dwarf_id)
--                 FROM dwarves d
--                 WHERE d.fortress_id = f.fortress_id
--             ),
--             'resource_ids', (
--                 SELECT jsonb_agg(fr.resource_id)
--                 FROM fortress_resources fr
--                 WHERE fr.fortress_id = f.fortress_id
--             ),
--             'workshop_ids', (
--                 SELECT jsonb_agg(w.workshop_id)
--                 FROM workshops w
--                 WHERE w.fortress_id = f.fortress_id
--             ),
--             'squad_ids', (
--                 SELECT jsonb_agg(s.squad_id)
--                 FROM military_squads s
--                 WHERE s.fortress_id = f.fortress_id
--             )
--         ) AS related_entities
--     FROM fortresses f
-- ) AS result;
