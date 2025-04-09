-- TASK DESCRIPTION
-- Write a query that will identify the most and least successful expeditions, taking into account:
-- * The ratio of the surviving participants to the total number
-- * The value of the artifacts found
-- * The number of new locations discovered
-- * The success of encounters with creatures (the ratio of favorable outcomes to unfavorable ones)
-- * The experience gained by the participants (comparing skills before and after)
-- Example JSON Result:
-- [
--   {
--     "expedition_id": 2302,
--     "destination": "Crystal Caverns",
--     "status": "Completed",
--     "survival_rate": 42.86,
--     "artifacts_value": 12000,
--     "discovered_sites": 1,
--     "encounter_success_rate": 33.33,
--     "skill_improvement": 5,
--     "expedition_duration": 29,
--     "overall_success_score": 0.39,
--     "related_entities": {
--       "member_ids": [121, 124, 127, 129, 130],
--       "artifact_ids": [2510],
--       "site_ids": [2450]
--     }
--   },
--   ...
-- ]
-- REFERENCE
WITH expedition_stats AS (
    SELECT
        e.expedition_id,
        e.destination,
        e.status,
        COUNT(em.dwarf_id) AS total_members,
        SUM(CASE WHEN em.survived = TRUE THEN 1 ELSE 0 END) AS survivors,
        COALESCE(SUM(ea.value), 0) AS artifacts_value,
        COUNT(DISTINCT es.site_id) AS discovered_sites,
        SUM(CASE WHEN ec.outcome = 'Favorable' THEN 1 ELSE 0 END) AS favorable_encounters,
        COUNT(ec.creature_id) AS total_encounters,
        e.departure_date,
        e.return_date
    FROM
        expeditions e
    LEFT JOIN
        expedition_members em ON e.expedition_id = em.expedition_id
    LEFT JOIN
        expedition_artifacts ea ON e.expedition_id = ea.expedition_id
    LEFT JOIN
        expedition_sites es ON e.expedition_id = es.expedition_id
    LEFT JOIN
        expedition_creatures ec ON e.expedition_id = ec.expedition_id
    GROUP BY
        e.expedition_id, e.destination, e.status, e.departure_date, e.return_date
),
skills_progression AS (
    SELECT
        em.expedition_id,
        SUM(
            COALESCE(ds_after.level, 0) - COALESCE(ds_before.level, 0)
        ) AS total_skill_improvement
    FROM
        expedition_members em
    JOIN
        dwarves d ON em.dwarf_id = d.dwarf_id
    JOIN
        dwarf_skills ds_before ON d.dwarf_id = ds_before.dwarf_id
    JOIN
        dwarf_skills ds_after ON d.dwarf_id = ds_after.dwarf_id
        AND ds_before.skill_id = ds_after.skill_id
    JOIN
        expeditions e ON em.expedition_id = e.expedition_id
    WHERE
        ds_before.date < e.departure_date
        AND ds_after.date > e.return_date
    GROUP BY
        em.expedition_id
)
SELECT
    es.expedition_id,
    es.destination,
    es.status,
    es.survivors AS surviving_members,
    es.total_members,
    ROUND((es.survivors::DECIMAL / es.total_members) * 100, 2) AS survival_rate,
    es.artifacts_value,
    es.discovered_sites,
    COALESCE(ROUND((es.favorable_encounters::DECIMAL /
        NULLIF(es.total_encounters, 0)) * 100, 2), 0) AS encounter_success_rate,
    COALESCE(sp.total_skill_improvement, 0) AS skill_improvement,
    EXTRACT(DAY FROM (es.return_date - es.departure_date)) AS expedition_duration,
    ROUND(
        (es.survivors::DECIMAL / es.total_members) * 0.3 +
        (es.artifacts_value / 1000) * 0.25 +
        (es.discovered_sites * 0.15) +
        COALESCE((es.favorable_encounters::DECIMAL /
            NULLIF(es.total_encounters, 0)), 0) * 0.15 +
        COALESCE((sp.total_skill_improvement / es.total_members), 0) * 0.15,
        2
    ) AS overall_success_score,
    JSON_OBJECT(
        'member_ids', (
            SELECT JSON_ARRAYAGG(em.dwarf_id)
            FROM expedition_members em
            WHERE em.expedition_id = es.expedition_id
        ),
        'artifact_ids', (
            SELECT JSON_ARRAYAGG(ea.artifact_id)
            FROM expedition_artifacts ea
            WHERE ea.expedition_id = es.expedition_id
        ),
        'site_ids', (
            SELECT JSON_ARRAYAGG(es2.site_id)
            FROM expedition_sites es2
            WHERE es2.expedition_id = es.expedition_id
        )
    ) AS related_entities
FROM
    expedition_stats es
LEFT JOIN
    skills_progression sp ON es.expedition_id = sp.expedition_id
ORDER BY
    overall_success_score DESC;

-- AlTERNATIVE SOLUTION
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

-- REFLECTION COMMENT
    -- EN:
        -- It is necessary to consider the differences between the two solutions both at the level of the overall query structure and from the point of view of each
        -- a separate summary field (approx. artifacts_value, discovered_sites, etc).
        -- * It is worth mentioning the differences in SQL dialects (mysql and postgresql):
        --   an alternative solution is implemented in the postgres dialect, whereas
        --   original in the mysql dialect. The main differences in dialect relate to the syntax of functions for working with json
        --   objects (JSON_OBJECT vs jsonb_build_object, JSON_ARRAYAGG vs jsonb_agg)
        -- * Differences in the overall structure:
        --   The original solution is based around a structure using two Common Table Expression (WITH ...)
        --   ```
        --   WITH expedition_stats AS ...
        --   WITH skills_progression AS ...
        --   SELECT ...
        --   ```
        --   First, the CTE subqueries expedition_stats and skills_progression are formed, with the help of which in the future
        --   the total required values are calculated, however, thanks to the subqueries expedition_stats and skills_progression
        --   this can be done much more compactly, except that the overall_success_score is as voluminous as in
        --   the original version.
        --   ```
        --   WITH expedition_stats AS ...
        --   SELECT ...
        --   ```
        --   Alternatively, the overall query structure uses only one CTE subquery, expeditions_metrics,
        --   in which all calculations and the entire process of forming the final fields are performed, except that only overall_success_score and
        --   related_entities are generated in the main query (SELECT)
        -- * Now let's look at each of the final fields:
        -- - The "expedition_id", "destination", and "status" fields in both versions are taken directly from the original "expeditions" table.
        -- - The "survival_rate" field is calculated in different ways. In the original version, the "survivors" field is formed separately
        --   and `total_members` after which the ratio is calculated and rounded to two decimal places.
        --   Alternatively, a shorter version, while not inferior in efficiency and correctness.
        -- - The `survived` field from the original table is converted to zeros and ones from bool (`em1.survived::int`) after
        --   which calculates the average value for the desired expedition.
        -- - The "artifacts_value" field in both variants, artifacts_value is calculated
        --   through a simple summation of the value of each of the artifacts found in the expedition, the difference is in the availability of instructions
        --   COALESCE in the original version to avoid null values in the field.
        -- - The "discovered_sites" field in the original version is implemented via DISCTINCT, while in the alternative version
        --   performed using COUNT exclusively without the DISCTINCT function, which is a disadvantage since, if possible,
        --   repeated detection of a place/building will be considered all detections, not unique ones.
        -- - The "encounter_success_rate" field in the original version is calculated using a pre-calculated
        --   `favorable_encounters` with a safety net for null values. Alternatively, the field is calculated
        --   via AVG with the conversion of the winning status to 1 and the losing status to 0. The disadvantage is the zero substitution branch
        --   in case of an unknown status
        -- - The "skill_improvement" field in the original version is calculated using the expression `total_skill_improvement`
        --   which is calculated inside the expression `skills_progression'. Alternatively, this field is calculated using
        --   calculating the maximum dwarf level before and after the expedition, however, without using COALESCE, which is a disadvantage.
        --   Also, the alternative option does not provide for summation of skill upgrades for several dwarves at once.,
        --   it's just that the maximum expenses are taken among all the participants of the expedition, which is incorrect.
        -- - The "expedition_duration" field in the original version is executed using the `EXTRACT` and `DAY FROM` functions.
        --   In the alternative version, no additional functions are used, however, within the framework of the SQL syntax, the value
        --   it is considered correct.
        -- - The "overall_success_score" field in both variants is calculated by summing the other fields.
        --   with custom coefficients, the COALESCE function is used wherever necessary. In an alternative
        --   it's not in use anymore.
        -- - The "related_entities" field in both variants is calculated identically through adjacent tables.
    -- RU:
        -- Следует рассмотреть отличия двух решений как на уровне общей структуры запроса, так и с точки зрения каждого
        -- отдельного итогового поля (прим. artifacts_value, discovered_sites, etc).
        -- * Отдельно стоит упомянуть отличия в SQL диалектах (mysql и postgresql):
        --   альтернативное решение выполнено в postgres диалекте, тогда как
        --   оригинальное в диалекте mysql. Основные отличия по диалекту касаются синтаксиса функций по работе с json
        --   объектами (JSON_OBJECT vs jsonb_build_object, JSON_ARRAYAGG vs jsonb_agg)
        -- * Отличия в общей структуре:
        --   Оригинальное решение строится вокруг структуры c использованием двух Common Table Expression (WITH ...)
        --   ```
        --   WITH expedition_stats AS ...
        --   WITH skills_progression AS ...
        --   SELECT ...
        --   ```
        --   Сначала формируются CTE-подзапросы expedition_stats и skills_progression с помощью которых в дальнейшем
        --   высчитываются итоговые нужные значения, однако благодаря подзапросам expedition_stats и skills_progression
        --   это можно сделать гораздо более компактно, разве что overall_success_score такой же объемный как и в
        --   оригинальном варианте.
        --   ```
        --   WITH expedition_stats AS ...
        --   SELECT ...
        --   ```
        --   В альтернативном варианте общая структура запроса использует только один CTE-подзапрос - expeditions_metrics,
        --   в котором вывполняются все вычисления и весь процесс формирования итоговых полей, разве что только overall_success_score и
        --   related_entities формируются в основном запросе (SELECT)
        -- * Теперь рассмотрим каждое из итоговых полей:
        --   - Поля "expedition_id", "destination", "status" в обоих вариантах берутся из исходной таблицы `expeditions` напрямую.
        --   - Поле "survival_rate" вычисляется разными способами, в оригинальном варианте отдельно формируется поле `survivors`
        --     и `total_members` после чего вычисляется соотношение и происходит округление до двух знаков после запятой.
        --     В альтернативном варианте, более короткая версия, при этом не уступающая в эффективности и корректности.
        --     Поле `survived` из изначальной таблицы преобразуется в нули и единицы из bool (`em1.survived::int`) после
        --     чего высчитывает среднее значение по нужной экспедиции.
        --   - Поле "artifacts_value" в обоих вариантах, просчет artifacts_value происходит
        --     через простое суммирование ценности каждого из артефактов найденного в экспедиции, отличие в наличии инструкции
        --     COALESCE в оригинальном варианте во избежания null значения в поле.
        --   - Поле "discovered_sites" в оригинальном варианте реализовано через DISCTINCT, в то время как в альтернативном варианте
        --     выполнено через исключительно COUNT без функции DISCTINCT, что является недостатком так как при возможности
        --     повторного обнаружения места/строения будут считаться все обнаружения, а не уникальные.
        --   - Поле "encounter_success_rate" в оригинальном варианте просчитывается через предварительно рассчитаный
        --     `favorable_encounters` c подстраховкой при null значении. В альтернативном варианте расчет поля производится
        --     через AVG с преобразованием победного статуса в 1, а проигрышного в 0. Недостатком является ветки подстановки нуля
        --     в случае незнакомого статуса
        --   - Поле "skill_improvement" в оригинальном варианте считается с использованием выражения `total_skill_improvement`
        --     которое просчитывается внутри выражения `skills_progression`. В альтернативном варианте данное поле считается через
        --     вычисление максимального уровня дворфа до и после экспедиции, однако без использования COALESCE что является минусом.
        --     Также в альтернативном варианте не предусмотрено суммирование повышения навыков сразу для нескольких дворфов,
        --     просто берутся максимальные занчения среди всех участников экспедиции, что некорректно.
        --   - Поле "expedition_duration" в оригинальном варианте выполнено с помощью функций `EXTRACT` и `DAY FROM`.
        --     В альтернативном варианте дополнительных функций не использовано, однако в рамках SQL синтаксиса значение
        --     считается корректно.
        --   - Поле "overall_success_score" в обоих вариантах просчитывается через суммирование других полей
        --     с кастомными коэффицентами, везде где необходимо использется функция `COALESCE`. В альтернативном
        --     варианте не использется.
        --   - Поле "related_entities" в обоих вариантах просчитывается идентично через смежные таблицы.
