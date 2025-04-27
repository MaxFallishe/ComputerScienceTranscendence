-- TASK DESCRIPTION
-- Develop a query that comprehensively analyzes the security of the fortress, taking into account:
-- - The history of all creature attacks and their outcomes
-- - Effectiveness of protective structures
-- - The relationship between the types of creatures and the effectiveness of defense
-- - Assessment of vulnerable areas based on fortress architecture
-- - Correlation between seasonal factors and frequency of attacks
-- - Readiness of military units and their location
-- - The evolution of the fortress's defensive abilities over time

-- EXAMPLE JSON RESULT:
-- {
--   "total_recorded_attacks": 183,
--   "unique_attackers": 42,
--   "overall_defense_success_rate": 76.50,
--   "security_analysis": {
--     "threat_assessment": {
--       "current_threat_level": "Moderate",
--       "active_threats": [
--         {
--           "creature_type": "Goblin",
--           "threat_level": 3,
--           "last_sighting_date": "0205-08-12",
--           "territory_proximity": 1.2,
--           "estimated_numbers": 35,
--           "creature_ids": [124, 126, 128, 132, 136]
--         },
--         {
--           "creature_type": "Forgotten Beast",
--           "threat_level": 5,
--           "last_sighting_date": "0205-07-28",
--           "territory_proximity": 3.5,
--           "estimated_numbers": 1,
--           "creature_ids": [158]
--         }
--       ]
--     },
--     "vulnerability_analysis": [
--       {
--         "zone_id": 15,
--         "zone_name": "Eastern Gate",
--         "vulnerability_score": 0.68,
--         "historical_breaches": 8,
--         "fortification_level": 2,
--         "military_response_time": 48,
--         "defense_coverage": {
--           "structure_ids": [182, 183, 184],
--           "squad_ids": [401, 405]
--         }
--       }
--     ],
--     "defense_effectiveness": [
--       {
--         "defense_type": "Drawbridge",
--         "effectiveness_rate": 95.12,
--         "avg_enemy_casualties": 12.4,
--         "structure_ids": [185, 186, 187, 188]
--       },
--       {
--         "defense_type": "Trap Corridor",
--         "effectiveness_rate": 88.75,
--         "avg_enemy_casualties": 8.2,
--         "structure_ids": [201, 202, 203, 204]
--       }
--     ],
--     "military_readiness_assessment": [
--       {
--         "squad_id": 403,
--         "squad_name": "Crossbow Legends",
--         "readiness_score": 0.92,
--         "active_members": 7,
--         "avg_combat_skill": 8.6,
--         "combat_effectiveness": 0.85,
--         "response_coverage": [
--           {
--             "zone_id": 12,
--             "response_time": 0
--           },
--           {
--             "zone_id": 15,
--             "response_time": 36
--           }
--         ]
--       }
--     ],
--     "security_evolution": [
--       {
--         "year": 203,
--         "defense_success_rate": 68.42,
--         "total_attacks": 38,
--         "casualties": 42,
--         "year_over_year_improvement": 3.20
--       },
--       {
--         "year": 204,
--         "defense_success_rate": 72.50,
--         "total_attacks": 40,
--         "casualties": 36,
--         "year_over_year_improvement": 4.08
--       }
--     ]
--   }
-- }

-- REFERENCE
WITH creature_attack_history AS (
    SELECT
        ca.attack_id,
        ca.creature_id,
        c.type AS creature_type,
        c.threat_level,
        ca.date,
        ca.location_id,
        ca.outcome,
        ca.casualties AS fortress_casualties,
        ca.enemy_casualties,
        ca.defense_structures_used,
        ca.military_response_time_minutes,
        EXTRACT(YEAR FROM ca.date) AS year,
        EXTRACT(MONTH FROM ca.date) AS month,
        EXTRACT(DOW FROM ca.date) AS day_of_week,
        l.zone_type,
        l.depth,
        l.fortification_level
    FROM
        creature_attacks ca
    JOIN
        creatures c ON ca.creature_id = c.creature_id
    JOIN
        locations l ON ca.location_id = l.location_id
),
defense_structure_effectiveness AS (
    SELECT
        ds.structure_id,
        ds.type AS defense_type,
        ds.location_id,
        l.zone_type,
        COUNT(DISTINCT ca.attack_id) AS attacks_defended,
        SUM(CASE WHEN ca.outcome = 'Repelled' THEN 1 ELSE 0 END) AS successful_defenses,
        AVG(ca.enemy_casualties) AS avg_enemy_casualties,
        ds.construction_date,
        ds.last_maintenance_date,
        EXTRACT(DAY FROM (CURRENT_DATE - ds.last_maintenance_date)) AS days_since_maintenance,
        ds.quality,
        ds.material_id
    FROM
        defense_structures ds
    JOIN
        locations l ON ds.location_id = l.location_id
    LEFT JOIN
        creature_attacks ca ON ds.structure_id = ANY(ca.defense_structures_used)
    GROUP BY
        ds.structure_id, ds.type, ds.location_id, l.zone_type,
        ds.construction_date, ds.last_maintenance_date, ds.quality, ds.material_id
),
zone_vulnerability AS (
    SELECT
        l.zone_id,
        l.name AS zone_name,
        l.zone_type,
        l.depth,
        l.access_points,
        l.fortification_level,
        COUNT(DISTINCT ca.attack_id) AS total_attacks,
        SUM(CASE WHEN ca.outcome = 'Breached' THEN 1 ELSE 0 END) AS breaches,
        AVG(ca.fortress_casualties) AS avg_casualties_per_attack,
        COUNT(DISTINCT ds.structure_id) AS defense_structures,
        COUNT(DISTINCT CASE WHEN sm.patrol_zone_id = l.zone_id THEN sm.squad_id END) AS patrolling_squads,
        -- Distance to nearest military response
        MIN(ms.response_time_to_zone) AS min_response_time,
        -- Structural metrics
        l.wall_integrity,
        l.trap_density,
        l.choke_points
    FROM
        locations l
    LEFT JOIN
        creature_attacks ca ON l.zone_id = ca.location_id
    LEFT JOIN
        defense_structures ds ON l.zone_id = ds.location_id
    LEFT JOIN
        squad_movement sm ON l.zone_id = sm.patrol_zone_id
    LEFT JOIN
        military_stations ms ON l.zone_id = ms.coverage_zone_id
    GROUP BY
        l.zone_id, l.name, l.zone_type, l.depth, l.access_points,
        l.fortification_level, l.wall_integrity, l.trap_density, l.choke_points
),
seasonal_attack_patterns AS (
    SELECT
        EXTRACT(YEAR FROM ca.date) AS year,
        EXTRACT(MONTH FROM ca.date) AS month,
        EXTRACT(SEASON FROM ca.date) AS season,
        COUNT(DISTINCT ca.attack_id) AS attack_count,
        COUNT(DISTINCT ca.creature_id) AS unique_attackers,
        AVG(c.threat_level) AS avg_threat_level,
        SUM(ca.fortress_casualties) AS total_casualties,
        SUM(ca.enemy_casualties) AS total_enemy_casualties,
        -- Weather correlation
        w.temperature,
        w.precipitation,
        w.weather_event_type,
        -- Moon phase correlation
        mp.phase AS moon_phase,
        -- Average defense effectiveness during this period
        AVG(CASE
            WHEN ca.outcome = 'Repelled' THEN 1.0
            WHEN ca.outcome = 'Partial Breach' THEN 0.5
            WHEN ca.outcome = 'Breached' THEN 0.0
        END) AS avg_defense_effectiveness
    FROM
        creature_attacks ca
    JOIN
        creatures c ON ca.creature_id = c.creature_id
    JOIN
        weather_records w ON DATE_TRUNC('day', ca.date) = w.date
    JOIN
        moon_phases mp ON DATE_TRUNC('day', ca.date) = mp.date
    GROUP BY
        EXTRACT(YEAR FROM ca.date),
        EXTRACT(MONTH FROM ca.date),
        EXTRACT(SEASON FROM ca.date),
        w.temperature, w.precipitation, w.weather_event_type,
        mp.phase
),
military_readiness AS (
    SELECT
        ms.squad_id,
        s.name AS squad_name,
        s.formation_type,
        ms.station_id,
        ms.current_location_id,
        l.zone_type,
        ms.alert_level,
        ms.readiness_score,
        COUNT(DISTINCT sm.dwarf_id) AS active_members,
        AVG(ds.level) AS avg_combat_skill,
        MIN(eq.quality) AS min_equipment_quality,
        AVG(eq.quality) AS avg_equipment_quality,
        COUNT(DISTINCT ca.attack_id) AS battles_participated,
        SUM(CASE WHEN ca.outcome IN ('Repelled', 'Partial Breach') THEN 1 ELSE 0 END) AS successful_defenses,
        AVG(ms.response_time_to_zone) AS avg_response_time,
        MAX(ms.last_training_date) AS last_training,
        EXTRACT(DAY FROM (CURRENT_DATE - MAX(ms.last_training_date))) AS days_since_training
    FROM
        military_stations ms
    JOIN
        military_squads s ON ms.squad_id = s.squad_id
    JOIN
        locations l ON ms.current_location_id = l.location_id
    JOIN
        squad_members sm ON s.squad_id = sm.squad_id AND sm.active = TRUE
    JOIN
        dwarf_skills ds ON sm.dwarf_id = ds.dwarf_id AND ds.skill_type = 'Combat'
    JOIN
        dwarf_equipment de ON sm.dwarf_id = de.dwarf_id
    JOIN
        equipment eq ON de.equipment_id = eq.equipment_id
    LEFT JOIN
        squad_battle_participation sbp ON s.squad_id = sbp.squad_id
    LEFT JOIN
        creature_attacks ca ON sbp.attack_id = ca.attack_id
    GROUP BY
        ms.squad_id, s.name, s.formation_type, ms.station_id,
        ms.current_location_id, l.zone_type, ms.alert_level, ms.readiness_score
),
fortress_security_evolution AS (
    SELECT
        EXTRACT(YEAR FROM e.date) AS year,
        COUNT(DISTINCT ca.attack_id) AS total_attacks,
        SUM(CASE WHEN ca.outcome = 'Repelled' THEN 1 ELSE 0 END) /
            NULLIF(COUNT(DISTINCT ca.attack_id), 0) AS defense_success_rate,
        COUNT(DISTINCT ds.structure_id) AS total_defenses,
        SUM(ds.quality * ds.effectiveness_rating) /
            NULLIF(COUNT(DISTINCT ds.structure_id), 0) AS avg_defense_quality,
        AVG(zv.fortification_level) AS avg_fortification_level,
        COUNT(DISTINCT ms.squad_id) AS military_squads,
        SUM(ms.readiness_score * ms.active_members) /
            NULLIF(SUM(ms.active_members), 0) AS weighted_military_readiness,
        SUM(e.resource_allocation) AS defense_investment,
        SUM(e.fortress_casualties) AS annual_casualties,
        -- Year over year improvement
        LAG(SUM(CASE WHEN ca.outcome = 'Repelled' THEN 1 ELSE 0 END) /
            NULLIF(COUNT(DISTINCT ca.attack_id), 0))
            OVER (ORDER BY EXTRACT(YEAR FROM e.date)) AS previous_year_success_rate
    FROM
        fortress_events e
    LEFT JOIN
        creature_attacks ca ON EXTRACT(YEAR FROM ca.date) = EXTRACT(YEAR FROM e.date)
    LEFT JOIN
        defense_structures ds ON EXTRACT(YEAR FROM ds.construction_date) <= EXTRACT(YEAR FROM e.date)
            AND (ds.decommission_date IS NULL OR EXTRACT(YEAR FROM ds.decommission_date) > EXTRACT(YEAR FROM e.date))
    LEFT JOIN
        zone_vulnerability zv ON 1=1 -- Join all zones for averaging
    LEFT JOIN
        military_readiness ms ON 1=1 -- Join all military units for counting
    WHERE
        e.event_type = 'Security Assessment'
    GROUP BY
        EXTRACT(YEAR FROM e.date)
    ORDER BY
        EXTRACT(YEAR FROM e.date)
)
SELECT
    -- Overall security metrics
    (SELECT COUNT(*) FROM creature_attacks) AS total_recorded_attacks,
    (SELECT COUNT(DISTINCT creature_id) FROM creature_attacks) AS unique_attackers,
    (SELECT SUM(fortress_casualties) FROM creature_attacks) AS total_historical_casualties,
    (SELECT
        SUM(CASE WHEN outcome = 'Repelled' THEN 1 ELSE 0 END)::DECIMAL /
        NULLIF(COUNT(*), 0) * 100
     FROM creature_attacks) AS overall_defense_success_rate,

    -- Current threat assessment with REST API format
    JSON_OBJECT(
        'threat_assessment', (
            SELECT JSON_OBJECT(
                'current_threat_level', (
                    SELECT
                        CASE
                            WHEN COUNT(*) FILTER (WHERE date > CURRENT_DATE - INTERVAL '30 days') > 10 THEN 'Critical'
                            WHEN COUNT(*) FILTER (WHERE date > CURRENT_DATE - INTERVAL '30 days') > 5 THEN 'High'
                            WHEN COUNT(*) FILTER (WHERE date > CURRENT_DATE - INTERVAL '30 days') > 2 THEN 'Moderate'
                            ELSE 'Low'
                        END
                    FROM creature_attacks
                ),
                'active_threats', (
                    SELECT JSON_ARRAYAGG(
                        JSON_OBJECT(
                            'creature_type', c.type,
                            'threat_level', c.threat_level,
                            'last_sighting_date', cs.date,
                            'territory_proximity', ct.distance_to_fortress,
                            'estimated_numbers', c.estimated_population,
                            'creature_ids', (
                                SELECT JSON_ARRAYAGG(c2.creature_id)
                                FROM creatures c2
                                WHERE c2.type = c.type AND c2.active = TRUE
                            )
                        )
                    )
                    FROM creatures c
                    JOIN creature_sightings cs ON c.creature_id = cs.creature_id
                    JOIN creature_territories ct ON c.creature_id = ct.creature_id
                    WHERE
                        c.active = TRUE AND
                        cs.date > CURRENT_DATE - INTERVAL '90 days'
                    GROUP BY c.type, c.threat_level, c.estimated_population
                ),
                'seasonal_risk_factors', (
                    SELECT JSON_OBJECT(
                        'current_season', EXTRACT(SEASON FROM CURRENT_DATE),
                        'historical_attack_frequency', COALESCE(
                            (SELECT AVG(attack_count)
                             FROM seasonal_attack_patterns
                             WHERE season = EXTRACT(SEASON FROM CURRENT_DATE)),
                            0
                        ),
                        'expected_threat_level', COALESCE(
                            (SELECT AVG(avg_threat_level)
                             FROM seasonal_attack_patterns
                             WHERE season = EXTRACT(SEASON FROM CURRENT_DATE)),
                            0
                        ),
                        'correlation_factors', (
                            SELECT JSON_ARRAYAGG(
                                JSON_OBJECT(
                                    'factor', f.factor_name,
                                    'correlation_strength', f.correlation_value
                                )
                            )
                            FROM (
                                SELECT 'Temperature' AS factor_name,
                                       CORR(sap.attack_count, sap.temperature) AS correlation_value
                                FROM seasonal_attack_patterns sap
                                UNION ALL
                                SELECT 'Precipitation',
                                       CORR(sap.attack_count, sap.precipitation)
                                FROM seasonal_attack_patterns sap
                                UNION ALL
                                SELECT 'Moon Phase',
                                       CORR(sap.attack_count,
                                            CASE
                                                WHEN sap.moon_phase = 'Full' THEN 1.0
                                                WHEN sap.moon_phase = 'Waxing Gibbous' THEN 0.75
                                                WHEN sap.moon_phase = 'First Quarter' THEN 0.5
                                                WHEN sap.moon_phase = 'Waxing Crescent' THEN 0.25
                                                WHEN sap.moon_phase = 'New' THEN 0.0
                                                WHEN sap.moon_phase = 'Waning Crescent' THEN 0.25
                                                WHEN sap.moon_phase = 'Last Quarter' THEN 0.5
                                                WHEN sap.moon_phase = 'Waning Gibbous' THEN 0.75
                                                ELSE 0.5
                                            END)
                                FROM seasonal_attack_patterns sap
                            ) f
                            WHERE f.correlation_value IS NOT NULL
                        )
                    )
                )
            )
        ),
        'vulnerability_analysis', (
            SELECT JSON_ARRAYAGG(
                JSON_OBJECT(
                    'zone_id', zv.zone_id,
                    'zone_name', zv.zone_name,
                    'vulnerability_score', ROUND(
                        (zv.breaches::DECIMAL / NULLIF(zv.total_attacks, 0) * 0.4) +
                        ((5 - zv.fortification_level) * 0.2) +
                        (zv.access_points * 0.1) +
                        ((120 - COALESCE(zv.min_response_time, 120)) / 120 * 0.1) +
                        ((3 - COALESCE(zv.defense_structures, 0)) * 0.1) +
                        ((3 - COALESCE(zv.patrolling_squads, 0)) * 0.1)
                    , 2),
                    'historical_breaches', zv.breaches,
                    'fortification_level', zv.fortification_level,
                    'access_points', zv.access_points,
                    'military_response_time', zv.min_response_time,
                    'defense_coverage', (
                        SELECT JSON_OBJECT(
                            'structure_ids', (
                                SELECT JSON_ARRAYAGG(ds.structure_id)
                                FROM defense_structures ds
                                WHERE ds.location_id = zv.zone_id
                            ),
                            'squad_ids', (
                                SELECT JSON_ARRAYAGG(DISTINCT sm.squad_id)
                                FROM squad_movement sm
                                WHERE sm.patrol_zone_id = zv.zone_id
                            )
                        )
                    )
                )
            )
            FROM zone_vulnerability zv
            ORDER BY
                (zv.breaches::DECIMAL / NULLIF(zv.total_attacks, 0) * 0.4) +
                ((5 - zv.fortification_level) * 0.2) +
                (zv.access_points * 0.1) +
                ((120 - COALESCE(zv.min_response_time, 120)) / 120 * 0.1) +
                ((3 - COALESCE(zv.defense_structures, 0)) * 0.1) +
                ((3 - COALESCE(zv.patrolling_squads, 0)) * 0.1) DESC
            LIMIT 10
        ),
        'defense_effectiveness', (
            SELECT JSON_ARRAYAGG(
                JSON_OBJECT(
                    'defense_type', x.defense_type,
                    'effectiveness_rate', x.effectiveness_rate,
                    'avg_enemy_casualties', x.avg_enemy_casualties,
                    'count', x.count,
                    'avg_quality', x.avg_quality,
                    'structure_ids', (
                        SELECT JSON_ARRAYAGG(ds.structure_id)
                        FROM defense_structures ds
                        WHERE ds.type = x.defense_type
                    )
                )
            )
            FROM (
                SELECT
                    dse.defense_type,
                    ROUND(SUM(dse.successful_defenses)::DECIMAL /
                        NULLIF(SUM(dse.attacks_defended), 0) * 100, 2) AS effectiveness_rate,
                    ROUND(AVG(dse.avg_enemy_casualties), 1) AS avg_enemy_casualties,
                    COUNT(*) AS count,
                    ROUND(AVG(dse.quality), 1) AS avg_quality
                FROM defense_structure_effectiveness dse
                GROUP BY dse.defense_type
                ORDER BY SUM(dse.successful_defenses)::DECIMAL /
                        NULLIF(SUM(dse.attacks_defended), 0) DESC
            ) x
        ),
        'military_readiness_assessment', (
            SELECT JSON_ARRAYAGG(
                JSON_OBJECT(
                    'squad_id', mr.squad_id,
                    'squad_name', mr.squad_name,
                    'readiness_score', mr.readiness_score,
                    'active_members', mr.active_members,
                    'avg_combat_skill', mr.avg_combat_skill,
                    'avg_equipment_quality', mr.avg_equipment_quality,
                    'combat_effectiveness', ROUND(
                        (mr.successful_defenses::DECIMAL / NULLIF(mr.battles_participated, 0) * 0.4) +
                        (mr.avg_combat_skill / 10 * 0.3) +
                        (mr.avg_equipment_quality / 5 * 0.2) +
                        (CASE WHEN mr.days_since_training < 7 THEN 1.0
                              WHEN mr.days_since_training < 14 THEN 0.8
                              WHEN mr.days_since_training < 30 THEN 0.6
                              WHEN mr.days_since_training < 60 THEN 0.4
                              ELSE 0.2 END * 0.1)
                    , 2),
                    'station_location', (
                        SELECT JSON_OBJECT(
                            'zone_id', l.zone_id,
                            'zone_name', l.name,
                            'zone_type', l.zone_type
                        )
                        FROM locations l
                        WHERE l.location_id = mr.current_location_id
                    ),
                    'response_coverage', (
                        SELECT JSON_ARRAYAGG(
                            JSON_OBJECT(
                                'zone_id', mcz.zone_id,
                                'response_time', mcz.response_time_minutes
                            )
                        )
                        FROM military_coverage_zones mcz
                        WHERE mcz.squad_id = mr.squad_id
                    )
                )
            )
            FROM military_readiness mr
            ORDER BY
                (mr.successful_defenses::DECIMAL / NULLIF(mr.battles_participated, 0) * 0.4) +
                (mr.avg_combat_skill / 10 * 0.3) +
                (mr.avg_equipment_quality / 5 * 0.2) +
                (CASE WHEN mr.days_since_training < 7 THEN 1.0
                      WHEN mr.days_since_training < 14 THEN 0.8
                      WHEN mr.days_since_training < 30 THEN 0.6
                      WHEN mr.days_since_training < 60 THEN 0.4
                      ELSE 0.2 END * 0.1) DESC
        ),
        'security_evolution', (
            SELECT JSON_ARRAYAGG(
                JSON_OBJECT(
                    'year', fse.year,
                    'defense_success_rate', ROUND(fse.defense_success_rate * 100, 2),
                    'total_attacks', fse.total_attacks,
                    'avg_defense_quality', fse.avg_defense_quality,
                    'military_readiness', fse.weighted_military_readiness,
                    'casualties', fse.annual_casualties,
                    'year_over_year_improvement', ROUND(
                        CASE
                            WHEN fse.previous_year_success_rate IS NULL THEN NULL
                            ELSE (fse.defense_success_rate - fse.previous_year_success_rate) * 100
                        END
                    , 2),
                    'defense_investment', fse.defense_investment
                )
            )
            FROM fortress_security_evolution fse
        ),
        'recommendation_summary', (
            SELECT JSON_ARRAYAGG(
                JSON_OBJECT(
                    'recommendation_type',
                    CASE r.priority
                        WHEN 1 THEN 'Critical'
                        WHEN 2 THEN 'High'
                        WHEN 3 THEN 'Medium'
                        ELSE 'Low'
                    END,
                    'focus_area', r.focus_area,
                    'recommendation', r.recommendation,
                    'estimated_resources', r.estimated_resources,
                    'expected_improvement', ROUND(r.expected_improvement * 100, 2),
                    'related_entities', (
                        CASE r.focus_area
                            WHEN 'Zone Security' THEN (
                                SELECT JSON_OBJECT('zone_ids', JSON_ARRAYAGG(zv.zone_id))
                                FROM zone_vulnerability zv
                                WHERE zv.vulnerability_score > 0.5
                                LIMIT 5
                            )
                            WHEN 'Military Readiness' THEN (
                                SELECT JSON_OBJECT('squad_ids', JSON_ARRAYAGG(mr.squad_id))
                                FROM military_readiness mr
                                WHERE mr.readiness_score < 0.7
                                LIMIT 5
                            )
                            WHEN 'Defense Structures' THEN (
                                SELECT JSON_OBJECT('structure_ids', JSON_ARRAYAGG(ds.structure_id))
                                FROM defense_structures ds
                                WHERE ds.days_since_maintenance > 90 OR ds.quality < 3
                                LIMIT 5
                            )
                            ELSE NULL
                        END
                    )
                )
            )
            FROM security_recommendations r
            ORDER BY r.priority, r.expected_improvement DESC
        )
    ) AS security_analysis
FROM (SELECT 1) AS dummy;

-- ALTERNATIVE SOLUTION
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


-- REFLECTION COMMENT
    -- EN:
        -- The main difference between the original and the alternative version is their syntax, the original version uses
        -- MySQL syntax, while the alternative version is made using PostgreSQL syntax. This
        -- the difference in the scope of the task is not critical, since no specific DBMS is assumed in the task.
        -- The number of CTE subqueries in both versions is six, but in most cases in the original version
        -- CTE subqueries contain a much larger number of calculated fields, for example, in CTE subqueries
        -- `creature_attack_history' (original version) and `attack_stats' (alternative version) different number of calculated
        -- fields - 17 vs. 3. An important difference is the final layout of the total values,
        -- values that do not require a separate json layout (approx. total_recorded_attacks, unique_attackers, etc),
        -- the data values in the alternative version are already calculated, while in the original version
        -- these values are calculated inside the final SELECT expression. Another difference is the presence in the final
        -- SELECT in the original version, more fields describing the state of the fortress and threat statistics in more detail.
        -- Also, the difference of the original version is the presence of the reception `FROM (SELECT 1) AS dummy`
        -- for the final SELECT, while in the alternative, only one FROM is used.
        -- Some of the minor differences between the alternative version and the original one can be listed:
        -- * Absence of the total_historical_casualities field in the alternative
        -- * When calculating overall_defense_success_rate, the filter is alternatively passed through the values of `Victory`,
        --   not `Repelled' as in the original one. The logic of the calculation itself is identical.
        -- * In the json object `threat_assessment`, in the original version the indicator `current_threat_level`
        --   is calculated, while in the alternative version a static value is fixed, also in the original version it is calculated
        --   the `seasonal_risk_factors` indicator. In addition, in the original version, only the values are included in the general selection of `active_threats`
        --   suitable for the filter `cs.date > CURRENT_DATE - INTERVAL '90 days".
        -- * The original version uses a more advanced logic based on the calculation of `vulnerabley_score`
        -- * The alternative is missing the json object `recommendation_summary`
    -- RU:
        -- Главное отличие между оригинальным и альтернативным вариантом - это их синтаксис, оригинальный вариант использует
        -- синтаксис MySQL, в то время как альтернативный вариант выполнен с использованием PostgreSQL синтаксиса. Данное
        -- отличие в рамках задачи не является критичным, так как не предполагается конкретной СУБД в задании.
        -- Количество CTE-подзапросов в обоих вариантах равняется шести, однако в большинстве случаев в оригинальном варианте
        -- CTE-подзапросы содержат в себе гораздо большее количество высчитываемых полей, к примеру в CTE-подзапросах
        -- `creature_attack_history` (оригинальный вариант) и `attack_stats` (альтернативны вариант) разное количество высчитываемых
        -- полей - 17 против 3. Важным отличием является финальная компоновка итоговых значений,
        -- значения не требующие отдельной json компоновки (прим. total_recorded_attacks, unique_attackers, etc),
        -- данные значения в альтернативном варианте передаются уже просчитанные, в то время как в оригинальном варианте
        -- эти значения просчитываются внутри финального SELECT выражения. Ещё одним отличием является наличие в итоговом
        -- SELECT в оригинальном варианте, большего количества полей более подробно описывающих состояние крепости и статистики по угрозам.
        -- Также отличием оригинального варианта - является присутствие приема `FROM (SELECT 1) AS dummy`
        -- для итогового SELECT, в то время как в альтернативном варианте используются лишь один FROM.
        -- Некоторые из незначительных отличий альтернативного варианта от оригинанального можно перечислить:
        -- * Отсутсвие поля total_historical_casualties в альтернативном варианте
        -- * При просчете overall_defense_success_rate в альтернативном варианте фильтр проходится по значениям `Victory`,
        --   а не `Repelled`, как в оригинальном. Сама логика просчета идентична.
        -- * В json объекте `threat_assessment`, в оригинальном варианте просчитывается показатель `current_threat_level` в
        --   время как в альтернативном варианте закреплено статичное значение, также в оригинальном варианте просчитывается
        --   показатель `seasonal_risk_factors`. Кроме того в оригинальном варианте в общую выборку `active_threats` включаются только значения
        --   подходящие под фильтр `cs.date > CURRENT_DATE - INTERVAL '90 days'`.
        -- * В оригинальном варианте используются более продвинутая логика по просчету `vulnerability_score`
        -- * В альтернативном варианте отсутствует json объект `recommendation_summary`
