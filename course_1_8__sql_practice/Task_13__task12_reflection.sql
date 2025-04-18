-- TASK DESCRIPTION
-- Create a query evaluating the effectiveness of military units based on:
-- Results of all battles (wins/losses/losses)
-- The ratio of wins to the total number of battles
-- Squad members' skills and their progress
-- Quality of equipment
-- Training history and its impact on results
-- Long-term survival of squad members

-- Example JSON Result:
-- [
--   {
--     "squad_id": 401,
--     "squad_name": "The Axe Lords",
--     "formation_type": "Melee",
--     "leader_name": "Urist McAxelord",
--     "total_battles": 28,
--     "victories": 22,
--     "victory_percentage": 78.57,
--     "casualty_rate": 24.32,
--     "casualty_exchange_ratio": 3.75,
--     "current_members": 8,
--     "total_members_ever": 12,
--     "retention_rate": 66.67,
--     "avg_equipment_quality": 4.28,
--     "total_training_sessions": 156,
--     "avg_training_effectiveness": 0.82,
--     "training_battle_correlation": 0.76,
--     "avg_combat_skill_improvement": 3.85,
--     "overall_effectiveness_score": 0.815,
--     "related_entities": {
--       "member_ids": [102, 104, 105, 107, 110, 115, 118, 122],
--       "equipment_ids": [5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009],
--       "battle_report_ids": [1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108],
--       "training_ids": [901, 902, 903, 904, 905, 906]
--     }
--   },
-- ...
-- ]
-- REFERENCE
WITH squad_battle_stats AS (
    SELECT
        sb.squad_id,
        COUNT(sb.report_id) AS total_battles,
        SUM(CASE WHEN sb.outcome = 'Victory' THEN 1 ELSE 0 END) AS victories,
        SUM(CASE WHEN sb.outcome = 'Defeat' THEN 1 ELSE 0 END) AS defeats,
        SUM(CASE WHEN sb.outcome = 'Retreat' THEN 1 ELSE 0 END) AS retreats,
        SUM(sb.casualties) AS total_casualties,
        SUM(sb.enemy_casualties) AS total_enemy_casualties,
        MIN(sb.date) AS first_battle,
        MAX(sb.date) AS last_battle
    FROM
        squad_battles sb
    GROUP BY
        sb.squad_id
),
squad_member_history AS (
    SELECT
        sm.squad_id,
        COUNT(DISTINCT sm.dwarf_id) AS total_members_ever,
        COUNT(DISTINCT CASE WHEN sm.exit_reason IS NULL THEN sm.dwarf_id END) AS current_members,
        COUNT(DISTINCT CASE WHEN sm.exit_reason = 'Death' THEN sm.dwarf_id END) AS deaths,
        AVG(EXTRACT(DAY FROM (COALESCE(sm.exit_date, CURRENT_DATE) - sm.join_date))) AS avg_service_days
    FROM
        squad_members sm
    GROUP BY
        sm.squad_id
),
squad_skill_progression AS (
    SELECT
        sm.squad_id,
        sm.dwarf_id,
        AVG(ds_current.level - ds_join.level) AS avg_skill_improvement,
        MAX(ds_current.level) AS max_current_skill
    FROM
        squad_members sm
    JOIN
        dwarf_skills ds_join ON sm.dwarf_id = ds_join.dwarf_id AND ds_join.date <= sm.join_date
    JOIN
        dwarf_skills ds_current ON sm.dwarf_id = ds_current.dwarf_id
        AND ds_current.skill_id = ds_join.skill_id
        AND ds_current.date = (
            SELECT MAX(date)
            FROM dwarf_skills
            WHERE dwarf_id = sm.dwarf_id AND skill_id = ds_join.skill_id
        )
    JOIN
        skills s ON ds_join.skill_id = s.skill_id
    WHERE
        s.category IN ('Combat', 'Military')
    GROUP BY
        sm.squad_id, sm.dwarf_id
),
squad_equipment_quality AS (
    SELECT
        se.squad_id,
        AVG(e.quality::INTEGER) AS avg_equipment_quality,
        MIN(e.quality::INTEGER) AS min_equipment_quality,
        COUNT(DISTINCT e.equipment_id) AS unique_equipment_count,
        SUM(CASE WHEN e.type = 'Weapon' THEN 1 ELSE 0 END) AS weapon_count,
        SUM(CASE WHEN e.type = 'Armor' THEN 1 ELSE 0 END) AS armor_count,
        SUM(CASE WHEN e.type = 'Shield' THEN 1 ELSE 0 END) AS shield_count
    FROM
        squad_equipment se
    JOIN
        equipment e ON se.equipment_id = e.equipment_id
    GROUP BY
        se.squad_id
),
squad_training_effectiveness AS (
    SELECT
        st.squad_id,
        COUNT(st.schedule_id) AS total_training_sessions,
        AVG(st.effectiveness::DECIMAL) AS avg_training_effectiveness,
        SUM(st.duration_hours) AS total_training_hours,
        -- Calculate if training improves battle outcomes
        CORR(
            st.effectiveness::DECIMAL,
            CASE WHEN sb.outcome = 'Victory' THEN 1 ELSE 0 END
        ) AS training_battle_correlation
    FROM
        squad_training st
    LEFT JOIN
        squad_battles sb ON st.squad_id = sb.squad_id AND sb.date > st.date
    GROUP BY
        st.squad_id
)
SELECT
    s.squad_id,
    s.name AS squad_name,
    s.formation_type,
    -- Leadership effectiveness
    d.name AS leader_name,
    sbs.total_battles,
    sbs.victories,
    sbs.defeats,
    ROUND((sbs.victories::DECIMAL / NULLIF(sbs.total_battles, 0)) * 100, 2) AS victory_percentage,
    ROUND((sbs.total_casualties::DECIMAL / NULLIF(smh.total_members_ever, 0)) * 100, 2) AS casualty_rate,
    ROUND((sbs.total_enemy_casualties::DECIMAL / NULLIF(sbs.total_casualties, 1)), 2) AS casualty_exchange_ratio,
    -- Member stats
    smh.current_members,
    smh.total_members_ever,
    ROUND((smh.current_members::DECIMAL / NULLIF(smh.total_members_ever, 0)) * 100, 2) AS retention_rate,
    smh.avg_service_days,
    -- Equipment effectiveness
    seq.avg_equipment_quality,
    seq.min_equipment_quality,
    seq.weapon_count + seq.armor_count + seq.shield_count AS total_equipment_pieces,
    -- Training effectiveness
    ste.total_training_sessions,
    ste.total_training_hours,
    ste.avg_training_effectiveness,
    ste.training_battle_correlation,
    -- Skill progression
    ROUND(AVG(ssp.avg_skill_improvement), 2) AS avg_combat_skill_improvement,
    ROUND(AVG(ssp.max_current_skill), 2) AS avg_max_combat_skill,
    -- Years active
    EXTRACT(YEAR FROM (sbs.last_battle - sbs.first_battle)) AS years_active,
    -- Overall effectiveness score
    ROUND(
        (sbs.victories::DECIMAL / NULLIF(sbs.total_battles, 0)) * 0.25 +
        (1 - (sbs.total_casualties::DECIMAL / NULLIF(smh.total_members_ever, 0))) * 0.20 +
        (smh.current_members::DECIMAL / NULLIF(smh.total_members_ever, 0)) * 0.15 +
        (seq.avg_equipment_quality::DECIMAL / 5) * 0.15 +
        (ste.avg_training_effectiveness) * 0.15 +
        (AVG(ssp.avg_skill_improvement) / 5) * 0.10,
        3
    ) AS overall_effectiveness_score,
    -- Related entities for REST API
    JSON_OBJECT(
        'member_ids', (
            SELECT JSON_ARRAYAGG(sm.dwarf_id)
            FROM squad_members sm
            WHERE sm.squad_id = s.squad_id AND sm.exit_date IS NULL
        ),
        'equipment_ids', (
            SELECT JSON_ARRAYAGG(se.equipment_id)
            FROM squad_equipment se
            WHERE se.squad_id = s.squad_id
        ),
        'battle_report_ids', (
            SELECT JSON_ARRAYAGG(sb.report_id)
            FROM squad_battles sb
            WHERE sb.squad_id = s.squad_id
        ),
        'training_ids', (
            SELECT JSON_ARRAYAGG(st.schedule_id)
            FROM squad_training st
            WHERE st.squad_id = s.squad_id
        )
    ) AS related_entities
FROM
    military_squads s
JOIN
    dwarves d ON s.leader_id = d.dwarf_id
LEFT JOIN
    squad_battle_stats sbs ON s.squad_id = sbs.squad_id
LEFT JOIN
    squad_member_history smh ON s.squad_id = smh.squad_id
LEFT JOIN
    squad_equipment_quality seq ON s.squad_id = seq.squad_id
LEFT JOIN
    squad_training_effectiveness ste ON s.squad_id = ste.squad_id
LEFT JOIN
    squad_skill_progression ssp ON s.squad_id = ssp.squad_id
GROUP BY
    s.squad_id, s.name, s.formation_type, d.name,
    sbs.total_battles, sbs.victories, sbs.defeats, sbs.total_casualties,
    sbs.total_enemy_casualties, sbs.first_battle, sbs.last_battle,
    smh.current_members, smh.total_members_ever, smh.avg_service_days,
    seq.avg_equipment_quality, seq.min_equipment_quality, seq.weapon_count,
    seq.armor_count, seq.shield_count,
    ste.total_training_sessions, ste.total_training_hours,
    ste.avg_training_effectiveness, ste.training_battle_correlation
ORDER BY
    overall_effectiveness_score DESC;

-- AlTERNATIVE SOLUTION
SELECT jsonb_agg(result)
FROM (

WITH
    leader_name_agg AS (
        SELECT ms.squad_id, d.name AS leader_name
        FROM military_squads ms
        JOIN dwarves d ON ms.leader_id = d.dwarf_id
    ),
    total_battles_agg AS (
        SELECT ms.squad_id, COUNT(sb.report_id) AS total_battles
        FROM military_squads ms
        LEFT JOIN squad_battles sb ON ms.squad_id = sb.squad_id
        GROUP BY ms.squad_id
    ),
    victories_agg AS (
        SELECT ms.squad_id, COUNT(sb.outcome) AS victories
        FROM military_squads ms
        LEFT JOIN squad_battles sb ON ms.squad_id = sb.squad_id AND sb.outcome = 'Victory'
        GROUP BY ms.squad_id
    ),
    casualty_rate_agg AS (
        SELECT ms.squad_id, SUM(sb.casualties) AS casualty_rate
        FROM military_squads ms
        LEFT JOIN squad_battles sb ON ms.squad_id = sb.squad_id
        GROUP BY ms.squad_id
    ),
    casualty_enemy_rate_agg AS (
        SELECT ms.squad_id, SUM(sb.enemy_casualties) AS casualty_enemy_rate
        FROM military_squads ms
        LEFT JOIN squad_battles sb ON ms.squad_id = sb.squad_id
        GROUP BY ms.squad_id
    ),
        current_members_agg AS (
        SELECT ms.squad_id, COUNT(da.dwarf_id) AS current_members
        FROM military_squads ms
        JOIN dwarf_assignments da ON da.assignment_type = 'Military'
        WHERE da.end_date IS NULL
        GROUP BY ms.squad_id
    ),
    total_members_ever_agg AS (
        SELECT ms.squad_id, COUNT(DISTINCT da.dwarf_id) AS total_members_ever
        FROM military_squads ms
        JOIN dwarf_assignments da ON da.assignment_type = 'Military'
        GROUP BY ms.squad_id
    ),
    avg_equipment_quality_agg AS (
        SELECT ms.squad_id, AVG(CASE
            WHEN de.quality = 'Legendary' THEN 5
            WHEN de.quality = 'Excellent' THEN 4
            WHEN de.quality = 'Good' THEN 3
            WHEN de.quality = 'Fair' THEN 2
            WHEN de.quality = 'Poor' THEN 1
            ELSE NULL END) AS avg_equipment_quality
        FROM military_squads ms
        JOIN dwarf_assignments da ON da.assignment_type = 'Military'
        JOIN dwarf_equipment de ON da.dwarf_id = de.dwarf_id
        WHERE da.end_date IS NULL
        GROUP BY ms.squad_id
    ),
    total_training_sessions_agg AS (
        SELECT ms.squad_id, COUNT(st.schedule_id) AS total_training_sessions
        FROM military_squads ms
        JOIN squad_training st ON ms.squad_id = st.squad_id
        GROUP BY  ms.squad_id
    ),
    avg_training_effectiveness_agg AS (
        SELECT st.squad_id, AVG(st.effectiveness::DECIMAL) AS avg_training_effectiveness
        FROM squad_training st
        GROUP BY st.squad_id
    )

SELECT
    -- "squad_id" --
    ms.squad_id,

    -- "squad_name" --
    ms.name,

    -- "formation_type" --
    ms.formation_type,

    -- "leader_name" --
    lna.leader_name,

    -- "total_battles" --
    tba.total_battles,

    -- "victories" --
    va.victories,

    -- "victory_percentage" --
    ROUND(COALESCE(va.victories::DECIMAL / NULLIF(tba.total_battles, 0), 0), 2) AS victory_percentage,

    -- "casualty_rate" --
    COALESCE(cra.casualty_rate, 0) AS casualty_rate,

    -- "casualty_exchange_ratio" --
    ROUND(COALESCE(cra.casualty_rate::DECIMAL / NULLIF(cera.casualty_enemy_rate, 0), 0), 2) AS casualty_exchange_ratio,

    -- "current_members" --
    cma.current_members,

    -- "total_members_ever" --
    tmea.total_members_ever,

    -- "retention_rate" --
    ROUND(COALESCE(cma.current_members::DECIMAL / NULLIF(tmea.total_members_ever, 0), 0), 2) AS retention_rate,

    -- "avg_equipment_quality" --
    ROUND(aeqa.avg_equipment_quality, 2) AS avg_equipment_quality,

    -- "total_training_sessions" --
    ttsa.total_training_sessions,

    -- "avg_training_effectiveness" --
    atea.avg_training_effectiveness,


    -- "related-entities" --
    jsonb_build_object(
            'member_ids', (SELECT jsonb_agg(sm.dwarf_id)
                                FROM squad_members sm
                                WHERE ms.squad_id = sm.squad_id),
            'equipment_ids', (SELECT jsonb_agg(se.equipment_id)
                                FROM squad_equipment se
                                WHERE ms.squad_id = se.squad_id),
            'battle_report_ids', (SELECT jsonb_agg(sb.report_id)
                                FROM squad_battles sb
                                WHERE ms.squad_id = sb.squad_id),
            'training_ids', (SELECT jsonb_agg(st.schedule_id)
                                FROM squad_training st
                                WHERE ms.squad_id = st.squad_id)
    )

FROM military_squads ms
LEFT JOIN leader_name_agg lna ON ms.squad_id = lna.squad_id
LEFT JOIN total_battles_agg tba ON ms.squad_id = tba.squad_id
LEFT JOIN victories_agg va ON ms.squad_id = va.squad_id
LEFT JOIN casualty_rate_agg cra ON ms.squad_id = cra.squad_id
LEFT JOIN casualty_enemy_rate_agg cera ON ms.squad_id = cera.squad_id
LEFT JOIN current_members_agg cma ON ms.squad_id = cma.squad_id
LEFT JOIN total_members_ever_agg tmea ON ms.squad_id = tmea.squad_id
LEFT JOIN avg_equipment_quality_agg aeqa ON ms.squad_id = aeqa.squad_id
LEFT JOIN total_training_sessions_agg ttsa ON ms.squad_id = ttsa.squad_id
LEFT JOIN avg_training_effectiveness_agg atea ON ms.squad_id = atea.squad_id


) AS result;

-- REFLECTION COMMENT
    -- EN:
        -- Regarding the composition of an SQL query, the main difference is in the number and logic of CTE subqueries. In the original version
        -- only 5 subqueries are used: `squad_battle_stats', `squad_member_history`, `squad_skill_progression`,
        -- `squad_equipment_quality`, `squad_training_effectivness`. Alternatively, 10 CTE subqueries are used.
        -- In addition, an important difference between the two queries is the presence of summary fields that are not implemented
        -- alternatively: `overall_effectiveness_score', `years_active`, `avg_max_combat_skill`,
        -- `avg_combat_skill_improvement`, `training_battle_correlation` and several other less significant and complex fields.
        -- The remaining fields are calculated in a similar way, in particular due to the fact that to calculate a significant number of fields
        -- basic SUM, COUNT, and no more than one or two JOIN operations are sufficient.
        -- One of the notable disadvantages of the original solution is the need to include the final SELECT query
        -- a GROUP BY operation with absolutely all fields used except those that imply aggregate functions.
    -- RU:
        -- Относительно композиции SQL-запроса основное отличие в количестве и логике CTE-подзапросов. В оригинальном варианте
        -- используются только 5 подзапросов `squad_battle_stats`, `squad_member_history`, `squad_skill_progression`,
        -- `squad_equipment_quality`, `squad_training_effectivness`. В альтернативном варианте исопльзуются 10 CTE-подзавпросов.
        -- Кроме того важным отличием двух запросов, является наличие итоговых полей, которые не реализованы
        -- в альтернативном варианте: `overall_effectiveness_score`,  `years_active`, `avg_max_combat_skill`,
        -- `avg_combat_skill_improvement`, `training_battle_correlation` и несколько других менее значимых и комплексных полей.
        -- Остальные поля просчитываются похожим образом, в частности из-за того что для расчета значимого количества полей
        -- достаточно базовых операций SUM, COUNT и не более одного-двух JOIN.
        -- Одним из заметных минусов в оригинальном решении является необходимость включать в итоговый SELECT запрос
        -- операцию GROUP BY с абсолютно всеми используемыми полями кроме тех которые подразумевают агрегатные функции.
