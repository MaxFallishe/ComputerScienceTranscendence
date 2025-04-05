-- 1. Write a query that will identify the most and least successful expeditions, taking into account:
-- * The ratio of the surviving participants to the total number
-- * The value of the artifacts found
-- * The number of new locations discovered
-- * The success of encounters with creatures (the ratio of favorable outcomes to unfavorable ones)
-- * The experience gained by the participants (comparing skills before and after)
SELECT jsonb_agg(result)
    FROM (WITH expeditions_metrics AS (
          SELECT
          e.expedition_id,
          e.destination,
          e.status,
          -- "survival_rate": 71.43
          (SELECT AVG(em1.survived::int) * 100
           FROM expedition_members em1
           WHERE e.expedition_id = em1.expedition_id) AS "survival_rate",

          -- "artifacts_value": 28500
          (SELECT SUM(ea1.value)
           FROM expedition_artifacts ea1
           WHERE e.expedition_id = ea1.expedition_id) AS "artifacts_value",

          -- "discovered_sites": 3
          (SELECT COUNT(es1.expedition_id)
           FROM expedition_sites es1
           WHERE e.expedition_id = es1.expedition_id) AS "discovered_sites",

          -- "encounter_success_rate": 66.67
          (SELECT AVG(
              CASE
                  WHEN ec1.outcome = 'Neutralized' THEN 1
                  WHEN ec1.outcome = 'Missed' THEN 0
              END)
           FROM expedition_creatures ec1
           WHERE e.expedition_id = ec1.expedition_id) AS "encounter_success_rate",

          -- "skill_improvement": 14
          (SELECT
               MAX(ds.level) FILTER (WHERE ds.date >= e.departure_date AND ds.date <= e.return_date)
               -
               MAX(ds.level) FILTER (WHERE ds.date < e.departure_date)
           FROM expedition_members em2
               JOIN dwarf_skills ds ON em2.dwarf_id = ds.dwarf_id
           WHERE em2.expedition_id = e.expedition_id) AS skill_improvement,

          -- "expedition_duration": 44 (days)
          (SELECT e.return_date - e.departure_date)   AS "expedition_duration"
            FROM expeditions e
            LEFT JOIN expedition_members em1 ON e.expedition_id = em1.expedition_id
            LEFT JOIN expedition_artifacts ea1 ON e.expedition_id = ea1.expedition_id
            LEFT JOIN expedition_sites es1 ON e.expedition_id = es1.expedition_id
            LEFT JOIN expedition_creatures ec1 ON e.expedition_id = ec1.expedition_id
            LEFT JOIN expedition_members em2 ON e.expedition_id = em2.expedition_id
            LEFT JOIN dwarf_skills ds ON em2.dwarf_id = ds.dwarf_id
        GROUP BY e.expedition_id, e.destination, e.status, e.departure_date, e.return_date)

      SELECT
             em.*,

             -- "overall_success_score": 0.78,
             ROUND((
                    COALESCE(survival_rate / 100, 0) * 0.3 +
                    COALESCE(encounter_success_rate, 0) * 0.3 +
                    COALESCE(artifacts_value / 30000.0, 0) * 0.2 +
                    COALESCE(skill_improvement / 20.0, 0) * 0.1 +
                    COALESCE(discovered_sites / 5.0, 0) * 0.1
                    )::numeric, 2) AS overall_success_score,

             -- "related_entities": {...}
             jsonb_build_object(
                    'member_ids', (SELECT jsonb_agg(em3.dwarf_id)
                                   FROM expedition_members em3
                                   WHERE em.expedition_id = em3.expedition_id),
                    'artifact_ids', (SELECT jsonb_agg(ea2.artifact_id)
                                     FROM expedition_artifacts ea2
                                     WHERE em.expedition_id = ea2.expedition_id),
                    'site_ids', (SELECT jsonb_agg(es2.site_id)
                                 FROM expedition_sites es2
                                 WHERE em.expedition_id = es2.site_id)
            ) AS related_entities
      FROM expeditions_metrics em)
AS result;


-- QUERY EXAMPLE RESULT
-- [
--   {
--     "status": "Completed",
--     "destination": "Eastern Mines",
--     "expedition_id": 1,
--     "survival_rate": 66.66666666666666666700,
--     "artifacts_value": 570,
--     "discovered_sites": 1,
--     "skill_improvement": 2,
--     "expedition_duration": 8,
--     "overall_success_score": 0.38,
--     "encounter_success_rate": 0.50000000000000000000,
--     "related_entities": {
--       "site_ids": [1],
--       "member_ids": [1, 10, 2],
--       "artifact_ids": [1, 2]
--     }
--   },
--   {
--     "status": "Completed",
--     "destination": "North Cold Mountain",
--     "expedition_id": 2,
--     "survival_rate": 0.00000000000000000000,
--     "artifacts_value": 105,
--     "discovered_sites": 0,
--     "skill_improvement": null,
--     "expedition_duration": 60,
--     "overall_success_score": 0.00,
--     "encounter_success_rate": null,
--     "related_entities": {
--       "site_ids": null,
--       "member_ids": [3, 9],
--       "artifact_ids": [3, 4]
--     }
--   }
-- ]