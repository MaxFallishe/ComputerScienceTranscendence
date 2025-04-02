-- 1. Create a query that returns information about the dwarf, including the IDs of all his skills, current assignments,
-- squad membership, and equipment used.
SELECT jsonb_agg(result)
FROM (
    SELECT
        d.dwarf_id,
        d.name,
        d.age,
        d.profession,
        jsonb_build_object(
            'skill_ids', (
                SELECT jsonb_agg(ds.skill_id)
                FROM dwarf_skills ds
                WHERE ds.dwarf_id = d.dwarf_id
            ),
            'assignment_ids', (
                SELECT jsonb_agg(da.assignment_id)
                FROM dwarf_assignments da
                WHERE da.dwarf_id = d.dwarf_id
            ),
            'squad_ids', (
                SELECT jsonb_agg(sm.squad_id)
                FROM squad_members sm
                WHERE sm.dwarf_id = d.dwarf_id
            ),
            'equipment_ids', (
                SELECT jsonb_agg(de.equipment_id)
                FROM dwarf_equipment de
                WHERE de.dwarf_id = d.dwarf_id
            )
        ) AS related_entities
    FROM dwarves d
) AS result;

-- QUERY EXAMPLE RESULT
-- [
--   {
--     "age": 195,
--     "name": "Thorin",
--     "dwarf_id": 1,
--     "profession": "Warrior",
--     "related_entities": {
--       "skill_ids": [
--         1
--       ],
--       "squad_ids": [
--         1
--       ],
--       "equipment_ids": [
--         1
--       ],
--       "assignment_ids": [
--         1
--       ]
--     }
--   },
--   {
--     "age": 178,
--     "name": "Balin",
--     "dwarf_id": 2,
--     "profession": "Lookout",
--     "related_entities": {
--       "skill_ids": [
--         2
--       ],
--       "squad_ids": null,
--       "equipment_ids": null,
--       "assignment_ids": [
--         2
--       ]
--     }
--   },
--   {
--     "age": 210,
--     "name": "Dwalin",
--     "dwarf_id": 3,
--     "profession": "Guard",
--     "related_entities": {
--       "skill_ids": null,
--       "squad_ids": null,
--       "equipment_ids": [
--         2
--       ],
--       "assignment_ids": null
--     }
--   },
--   {
--     "age": 82,
--     "name": "Fili",
--     "dwarf_id": 4,
--     "profession": "Scout",
--     "related_entities": {
--       "skill_ids": [
--         3
--       ],
--       "squad_ids": [
--         2
--       ],
--       "equipment_ids": null,
--       "assignment_ids": null
--     }
--   },
--   {
--     "age": 77,
--     "name": "Kili",
--     "dwarf_id": 5,
--     "profession": "Archer",
--     "related_entities": {
--       "skill_ids": null,
--       "squad_ids": null,
--       "equipment_ids": null,
--       "assignment_ids": null
--     }
--   }
-- ]

-- 2. Write a request for information about the workshop, including the IDs of the assigned artisans, current projects,
-- and resources used and produced.
SELECT jsonb_agg(result)
FROM (
    SELECT
        w.workshop_id,
        w.name,
        w.type,
        w.quality,
        jsonb_build_object(
            'craftsdwarf_ids', (
                SELECT jsonb_agg(wc.dwarf_id)
                FROM workshop_craftsdwarves wc
                WHERE w.workshop_id = wc.workshop_id
            ),
            'project_ids', (
                SELECT jsonb_agg(p.workshop_id)
                FROM projects p
                WHERE w.workshop_id = p.workshop_id
            ),
            'input_material_ids', (
                SELECT jsonb_agg(wp.workshop_id)
                FROM workshop_materials wp
                WHERE w.workshop_id = wp.workshop_id
            ),
            'output_product_ids', (
                SELECT jsonb_agg(p2.workshop_id)
                FROM products p2
                WHERE w.workshop_id = p2.workshop_id
            )
        ) AS related_entities
    FROM workshops w
) AS result;

-- QUERY EXAMPLE RESULT
-- [
--   {
--     "name": "Forge Alpha",
--     "type": "Smithy",
--     "quality": "Good",
--     "workshop_id": 1,
--     "related_entities": {
--       "project_ids": [
--         1,
--         1
--       ],
--       "craftsdwarf_ids": [
--         1
--       ],
--       "input_material_ids": [
--         1
--       ],
--       "output_product_ids": [
--         1,
--         1
--       ]
--     }
--   },
--   {
--     "name": "Craft Hall",
--     "type": "Carpentry",
--     "quality": "Excellent",
--     "workshop_id": 2,
--     "related_entities": {
--       "project_ids": null,
--       "craftsdwarf_ids": [
--         2
--       ],
--       "input_material_ids": [
--         2
--       ],
--       "output_product_ids": null
--     }
--   }
-- ]


-- 3. Develop a query that returns information about the military unit, including the IDs of all squad members,
-- equipment used, past and current operations, and training.
SELECT jsonb_agg(result)
FROM (
    SELECT
        ms.squad_id,
        ms.name,
        ms.formation_type,
        ms.leader_id,
        jsonb_build_object(
            'member_ids', (
                SELECT jsonb_agg(sm.dwarf_id)
                FROM squad_members sm
                WHERE ms.squad_id = sm.squad_id
            ),
            'equipment_ids', (
                SELECT jsonb_agg(se.equipment_id)
                FROM squad_equipment se
                WHERE ms.squad_id = se.squad_id
            ),
            'operation_ids', (
                SELECT jsonb_agg(so.operation_id)
                FROM squad_operations so
                WHERE ms.squad_id = so.squad_id
            ),
            'training_schedule_ids', (
                SELECT jsonb_agg(st.schedule_id)
                FROM squad_training st
                WHERE ms.squad_id = st.squad_id
            ),
            'battle_report_ids', (
                SELECT jsonb_agg(sb.report_id)
                FROM squad_battles sb
                WHERE ms.squad_id = sb.squad_id
            )
        ) AS related_entities
    FROM military_squads ms
) AS result;

-- QUERY EXAMPLE RESULT
-- [
--   {
--     "name": "Shieldbearers",
--     "squad_id": 1,
--     "leader_id": 1,
--     "formation_type": "Infantry",
--     "related_entities": {
--       "member_ids": [
--         1
--       ],
--       "equipment_ids": [
--         1
--       ],
--       "operation_ids": [
--         1
--       ],
--       "battle_report_ids": [
--         1
--       ],
--       "training_schedule_ids": [
--         1
--       ]
--     }
--   },
--   {
--     "name": "Stone Guard",
--     "squad_id": 2,
--     "leader_id": 4,
--     "formation_type": "Archers",
--     "related_entities": {
--       "member_ids": [
--         4
--       ],
--       "equipment_ids": [
--         2
--       ],
--       "operation_ids": null,
--       "battle_report_ids": null,
--       "training_schedule_ids": null
--     }
--   }
-- ]