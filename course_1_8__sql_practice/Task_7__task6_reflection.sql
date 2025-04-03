-- 1. Create a query that returns information about the dwarf, including the IDs of all his skills, current assignments,
-- squad membership, and equipment used.

-- REFERENCE
SELECT
    d.dwarf_id,
    d.name,
    d.age,
    d.profession,
    JSON_OBJECT(
        'skill_ids', (
            SELECT JSON_ARRAYAGG(ds.skill_id)
            FROM dwarf_skills ds
            WHERE ds.dwarf_id = d.dwarf_id
        ),
        'assignment_ids', (
            SELECT JSON_ARRAYAGG(da.assignment_id)
            FROM dwarf_assignments da
            WHERE da.dwarf_id = d.dwarf_id
        ),
        'squad_ids', (
            SELECT JSON_ARRAYAGG(sm.squad_id)
            FROM squad_members sm
            WHERE sm.dwarf_id = d.dwarf_id
        ),
        'equipment_ids', (
            SELECT JSON_ARRAYAGG(de.equipment_id)
            FROM dwarf_equipment de
            WHERE de.dwarf_id = d.dwarf_id
        )
    ) AS related_entities
FROM
    dwarves d;

-- AlTERNATIVE SOLUTION
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

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution. An alternative solution is implemented in
        -- the postgresql syntax due to what makes the data composition functions in a json object different.
        -- Top-level jsonb_agg conversion added to represent the total in the format of a half-valued json object.
    -- RU:
        -- Значимых отличий с эталонным решением нет. Альтернативное решение выполнено в синтаксисе postgresql из-за
        -- чего функции компановки данных в json объект отличаются. Верхнеуровневое jsonb_agg преобразование добавлено
        -- для представления итога в формате полноценного json объекта.


-- 2. Write a request for information about the workshop, including the IDs of the assigned artisans, current projects,
-- and resources used and produced.

-- REFERENCE
SELECT
    w.workshop_id,
    w.name,
    w.type,
    w.quality,
    JSON_OBJECT(
        'craftsdwarf_ids', (
            SELECT JSON_ARRAYAGG(wc.dwarf_id)
            FROM workshop_craftsdwarves wc
            WHERE wc.workshop_id = w.workshop_id
        ),
        'project_ids', (
            SELECT JSON_ARRAYAGG(p.project_id)
            FROM projects p
            WHERE p.workshop_id = w.workshop_id
        ),
        'input_material_ids', (
            SELECT JSON_ARRAYAGG(wm.material_id)
            FROM workshop_materials wm
            WHERE wm.workshop_id = w.workshop_id AND wm.is_input = TRUE
        ),
        'output_product_ids', (
            SELECT JSON_ARRAYAGG(wp.product_id)
            FROM workshop_products wp
            WHERE wp.workshop_id = w.workshop_id
        )
    ) AS related_entities
FROM
    workshops w;

-- AlTERNATIVE SOLUTION
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

-- REFLECTION COMMENT
    -- EN:
        -- A significant difference is the use of data from the "FROM products" table instead of "FROM workshop_products".
        -- An alternative solution is implemented in postgresql syntax, which is why data composition functions in a json object
        -- they differ. Top-level json_agg conversion added
        -- to represent the total in the format of a full-fledged json object.
    -- RU:
        -- Значимое отличие заключается в использовании данных из таблицы "FROM products" вместо "FROM workshop_products".
        -- Альтернативное решение выполнено в синтаксисе postgresql из-за чего функции компановки данных в json объект
        -- отличаются. Верхнеуровневое jsonb_agg преобразование добавлено
        -- для представления итога в формате полноценного json объекта.


-- 3. Develop a query that returns information about the military unit, including the IDs of all squad members,
-- equipment used, past and current operations, and training.

-- REFERENCE
SELECT
    s.squad_id,
    s.name,
    s.formation_type,
    s.leader_id,
    JSON_OBJECT(
        'member_ids', (
            SELECT JSON_ARRAYAGG(sm.dwarf_id)
            FROM squad_members sm
            WHERE sm.squad_id = s.squad_id
        ),
        'equipment_ids', (
            SELECT JSON_ARRAYAGG(se.equipment_id)
            FROM squad_equipment se
            WHERE se.squad_id = s.squad_id
        ),
        'operation_ids', (
            SELECT JSON_ARRAYAGG(so.operation_id)
            FROM squad_operations so
            WHERE so.squad_id = s.squad_id
        ),
        'training_schedule_ids', (
            SELECT JSON_ARRAYAGG(st.schedule_id)
            FROM squad_training st
            WHERE st.squad_id = s.squad_id
        ),
        'battle_report_ids', (
            SELECT JSON_ARRAYAGG(sb.report_id)
            FROM squad_battles sb
            WHERE sb.squad_id = s.squad_id
        )
    ) AS related_entities
FROM
    military_squads s;

-- AlTERNATIVE SOLUTION
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

-- REFLECTION COMMENT
    -- EN:
        -- There are no significant differences with the reference solution. An alternative solution is implemented in
        -- the postgresql syntax due to what makes the data composition functions in a json object different.
        -- Top-level jsonb_agg conversion added to represent the total in the format of a half-valued json object.
    -- RU:
        -- Значимых отличий с эталонным решением нет. Альтернативное решение выполнено в синтаксисе postgresql из-за
        -- чего функции компановки данных в json объект отличаются. Верхнеуровневое jsonb_agg преобразование добавлено
        -- для представления итога в формате полцноценного json объекта.