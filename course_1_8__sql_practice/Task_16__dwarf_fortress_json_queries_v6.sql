SELECT jsonb_agg(result)
FROM (

WITH attack_stats AS (
    SELECT
        COUNT(*) AS total_recorded_attacks,
        COUNT(DISTINCT creature_id) AS unique_attackers,
        ROUND(
            (COUNT(*) FILTER (WHERE outcome = 'Victory')::DECIMAL / NULLIF(COUNT(*),0)) * 100, 2
        ) AS overall_defense_success_rate
    FROM creature_attacks
),
active_threats AS (
    SELECT
        c.type AS creature_type,
        c.threat_level AS threat_level,
        MAX(cs.date) AS last_sighting_date,
        MIN(ct.distance_to_fortress) AS territory_proximity,
        COALESCE(c.estimated_population, 0) AS estimated_numbers,
        jsonb_agg(DISTINCT c.creature_id) AS creature_ids
    FROM creatures c
    LEFT JOIN creature_sightings cs ON cs.creature_id = c.creature_id
    LEFT JOIN creature_territories ct ON ct.creature_id = c.creature_id
    WHERE c.active = TRUE
    GROUP BY c.type, c.threat_level, c.estimated_population
),
vulnerability_analysis AS (
    SELECT
        l.zone_id,
        l.name AS zone_name,
        ROUND(
            (1.0 - (l.wall_integrity + l.trap_density)::DECIMAL/200)::NUMERIC, 2
        ) AS vulnerability_score,
        COUNT(ca.attack_id) FILTER (WHERE ca.outcome = 'Defeat') AS historical_breaches,
        l.fortification_level,
        COALESCE(AVG(ca.military_response_time_minutes), 0) AS military_response_time,
        jsonb_agg(DISTINCT l.location_id) AS structure_ids,
        jsonb_agg(DISTINCT ms.squad_id) AS squad_ids
    FROM locations l
    LEFT JOIN creature_attacks ca ON ca.location_id = l.location_id
    LEFT JOIN squad_training st ON st.location_id = l.location_id
    LEFT JOIN military_squads ms ON st.squad_id = ms.squad_id
    GROUP BY l.zone_id, l.name, l.wall_integrity, l.trap_density, l.fortification_level
),
defense_effectiveness AS (
    SELECT
        CASE
            WHEN l.zone_type ILIKE '%bridge%' THEN 'Drawbridge'
            WHEN l.zone_type ILIKE '%trap%' THEN 'Trap Corridor'
            ELSE 'Other'
        END AS defense_type,
        ROUND(
            (COUNT(ca.attack_id) FILTER (WHERE ca.outcome = 'Victory')::DECIMAL / NULLIF(COUNT(ca.attack_id),0)) * 100, 2
        ) AS effectiveness_rate,
        ROUND(AVG(ca.enemy_casualties), 1) AS avg_enemy_casualties,
        jsonb_agg(DISTINCT l.location_id) AS structure_ids
    FROM creature_attacks ca
    JOIN locations l ON l.location_id = ca.location_id
    GROUP BY defense_type
),
squad_readiness AS (
    WITH squad_response_times AS (
        SELECT
            st.squad_id,
            st.location_id,
            MIN(ca.military_response_time_minutes) AS min_response_time
        FROM squad_training st
        LEFT JOIN creature_attacks ca ON ca.location_id = st.location_id
        GROUP BY st.squad_id, st.location_id
    )
    SELECT
        ms.squad_id,
        ms.name AS squad_name,
        ROUND(AVG(st.effectiveness)::DECIMAL / 100, 2) AS readiness_score,
        COUNT(sm.dwarf_id) FILTER (WHERE sm.exit_date IS NULL) AS active_members,
        ROUND(AVG(ds.level),1) AS avg_combat_skill,
        ROUND(AVG(st.effectiveness)::DECIMAL / 100, 2) AS combat_effectiveness,
        jsonb_agg(DISTINCT jsonb_build_object(
            'zone_id', srt.location_id,
            'response_time', COALESCE(srt.min_response_time, 0)
        )) AS response_coverage
    FROM military_squads ms
    LEFT JOIN squad_training st ON st.squad_id = ms.squad_id
    LEFT JOIN squad_members sm ON sm.squad_id = ms.squad_id
    LEFT JOIN dwarf_skills ds ON ds.dwarf_id = sm.dwarf_id
    LEFT JOIN squad_response_times srt ON srt.squad_id = ms.squad_id
    GROUP BY ms.squad_id, ms.name
),
security_evolution AS (
    WITH yearly_stats AS (
        SELECT
            EXTRACT(YEAR FROM ca.date)::INT AS year,
            COUNT(*) FILTER (WHERE ca.outcome = 'Victory')::DECIMAL AS victories,
            COUNT(*)::DECIMAL AS total_attacks,
            SUM(ca.casualties) AS casualties
        FROM creature_attacks ca
        GROUP BY year
    )
    SELECT
        ys.year,
        ROUND((ys.victories / NULLIF(ys.total_attacks, 0)) * 100, 2) AS defense_success_rate,
        ys.total_attacks::INT AS total_attacks,
        ys.casualties,
        ROUND(
            ((ys.victories / NULLIF(ys.total_attacks, 0)) * 100) -
            LAG((ys.victories / NULLIF(ys.total_attacks, 0)) * 100) OVER (ORDER BY ys.year),
        2) AS year_over_year_improvement
    FROM yearly_stats ys
)

SELECT
    ast.total_recorded_attacks,
    ast.unique_attackers,
    ast.overall_defense_success_rate,

    jsonb_build_object(
        'security_analysis', jsonb_build_object(

            'threat_assessment', jsonb_build_object(
                'current_threat_level', 'Moderate',
                'active_threats', (
                    SELECT jsonb_agg(
                        jsonb_build_object(
                            'creature_type', at.creature_type,
                            'threat_level', at.threat_level,
                            'last_sighting_date', to_char(at.last_sighting_date, 'YYYY-MM-DD'),
                            'territory_proximity', at.territory_proximity,
                            'estimated_numbers', at.estimated_numbers,
                            'creature_ids', at.creature_ids
                        )
                    ) FROM active_threats at
                )
            ),
            'vulnerability_analysis', (
                SELECT jsonb_agg(
                    jsonb_build_object(
                        'zone_id', va.zone_id,
                        'zone_name', va.zone_name,
                        'vulnerability_score', va.vulnerability_score,
                        'historical_breaches', va.historical_breaches,
                        'fortification_level', va.fortification_level,
                        'military_response_time', va.military_response_time,
                        'defense_coverage', jsonb_build_object(
                            'structure_ids', va.structure_ids,
                            'squad_ids', va.squad_ids
                        )
                    )
                ) FROM vulnerability_analysis va
            ),
            'defense_effectiveness', (
                SELECT jsonb_agg(
                    jsonb_build_object(
                        'defense_type', de.defense_type,
                        'effectiveness_rate', de.effectiveness_rate,
                        'avg_enemy_casualties', de.avg_enemy_casualties,
                        'structure_ids', de.structure_ids
                    )
                ) FROM defense_effectiveness de
            ),
            'military_readiness_assessment', (
                SELECT jsonb_agg(
                    jsonb_build_object(
                        'squad_id', sr.squad_id,
                        'squad_name', sr.squad_name,
                        'readiness_score', sr.readiness_score,
                        'active_members', sr.active_members,
                        'avg_combat_skill', sr.avg_combat_skill,
                        'combat_effectiveness', sr.combat_effectiveness,
                        'response_coverage', sr.response_coverage
                    )
                ) FROM squad_readiness sr
            ),
            'security_evolution', (
                SELECT jsonb_agg(
                    jsonb_build_object(
                        'year', se.year,
                        'defense_success_rate', se.defense_success_rate,
                        'total_attacks', se.total_attacks,
                        'casualties', se.casualties,
                        'year_over_year_improvement', se.year_over_year_improvement
                    )
                ) FROM security_evolution se
            )

        )
    ) AS security_analysis

FROM attack_stats ast

) AS result;
