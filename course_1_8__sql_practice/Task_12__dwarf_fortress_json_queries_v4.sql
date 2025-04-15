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


) AS result;
