-- TASK DESCRIPTION
-- Develop a query that analyzes the effectiveness of each workshop, taking into account:
--
-- Productivity of each craftsman (ratio of products created to time spent)
-- Resource efficiency (the ratio of resources consumed to goods produced)
-- The quality of the goods produced (weighted average by value)
-- Workshop downtime
-- The influence of artisan skills on the quality of goods
-- Example JSON Result:
-- [
--  {
--     "workshop_id": 301,
--     "workshop_name": "Royal Forge",
--     "workshop_type": "Smithy",
--     "num_craftsdwarves": 4,
--     "total_quantity_produced": 256,
--     "total_production_value": 187500,
--
--     "daily_production_rate": 3.41,
--     "value_per_material_unit": 7.82,
--     "workshop_utilization_percent": 85.33,
--
--     "material_conversion_ratio": 1.56,
--
--     "average_craftsdwarf_skill": 7.25,
--
--     "skill_quality_correlation": 0.83,
--
--     "related_entities": {
--       "craftsdwarf_ids": [101, 103, 108, 115],
--       "product_ids": [801, 802, 803, 804, 805, 806],
--       "material_ids": [201, 204, 208, 210],
--       "project_ids": [701, 702, 703]
--     }
--   },
--   ...
-- ]
-- REFERENCE
WITH workshop_activity AS (
    SELECT
        w.workshop_id,
        w.name AS workshop_name,
        w.type AS workshop_type,
        COUNT(DISTINCT wc.dwarf_id) AS num_craftsdwarves,
        COUNT(DISTINCT wp.product_id) AS products_produced,
        SUM(wp.quantity) AS total_quantity_produced,
        SUM(p.value * wp.quantity) AS total_production_value,
        COUNT(DISTINCT wm.material_id) AS materials_used,
        SUM(wm.quantity) AS total_materials_consumed,
        MAX(wp.production_date) - MIN(wc.assignment_date) AS production_timespan,
        -- Days with production vs. total days active
        COUNT(DISTINCT wp.production_date) AS active_production_days,
        EXTRACT(DAY FROM (MAX(wp.production_date) - MIN(wc.assignment_date))) AS total_days
    FROM
        workshops w
    LEFT JOIN
        workshop_craftsdwarves wc ON w.workshop_id = wc.workshop_id
    LEFT JOIN
        workshop_products wp ON w.workshop_id = wp.workshop_id
    LEFT JOIN
        products p ON wp.product_id = p.product_id
    LEFT JOIN
        workshop_materials wm ON w.workshop_id = wm.workshop_id AND wm.is_input = TRUE
    GROUP BY
        w.workshop_id, w.name, w.type
),
craftsdwarf_productivity AS (
    SELECT
        wc.workshop_id,
        wc.dwarf_id,
        COUNT(DISTINCT p.product_id) AS products_created,
        AVG(p.quality::INTEGER) AS avg_quality,
        SUM(p.value) AS total_value_created
    FROM
        workshop_craftsdwarves wc
    JOIN
        products p ON p.created_by = wc.dwarf_id
    GROUP BY
        wc.workshop_id, wc.dwarf_id
),
craftsdwarf_skills AS (
    SELECT
        wc.workshop_id,
        wc.dwarf_id,
        AVG(ds.level) AS avg_skill_level,
        MAX(ds.level) AS max_skill_level
    FROM
        workshop_craftsdwarves wc
    JOIN
        dwarf_skills ds ON wc.dwarf_id = ds.dwarf_id
    JOIN
        skills s ON ds.skill_id = s.skill_id
    WHERE
        s.category = 'Crafting'
    GROUP BY
        wc.workshop_id, wc.dwarf_id
),
material_efficiency AS (
    SELECT
        w.workshop_id,
        SUM(CASE WHEN wm.is_input = TRUE THEN wm.quantity ELSE 0 END) AS input_quantity,
        SUM(CASE WHEN wm.is_input = FALSE THEN wm.quantity ELSE 0 END) AS output_quantity,
        COUNT(DISTINCT CASE WHEN wm.is_input = TRUE THEN wm.material_id END) AS unique_inputs,
        COUNT(DISTINCT CASE WHEN wm.is_input = FALSE THEN wm.material_id END) AS unique_outputs
    FROM
        workshops w
    LEFT JOIN
        workshop_materials wm ON w.workshop_id = wm.workshop_id
    GROUP BY
        w.workshop_id
)
SELECT
    wa.workshop_id,
    wa.workshop_name,
    wa.workshop_type,
    wa.num_craftsdwarves,
    wa.total_quantity_produced,
    wa.total_production_value,

    -- Productivity metrics
    ROUND(wa.total_quantity_produced::DECIMAL / NULLIF(wa.total_days, 0), 2) AS daily_production_rate,
    ROUND(wa.total_production_value::DECIMAL / NULLIF(wa.total_materials_consumed, 0), 2) AS value_per_material_unit,
    ROUND((wa.active_production_days::DECIMAL / NULLIF(wa.total_days, 0)) * 100, 2) AS workshop_utilization_percent,

    -- Efficiency metrics
    ROUND(me.output_quantity::DECIMAL / NULLIF(me.input_quantity, 0), 2) AS material_conversion_ratio,

    -- Craftsdwarf skill influence
    ROUND(AVG(cs.avg_skill_level), 2) AS average_craftsdwarf_skill,

    -- Correlation between skill and productivity
    CORR(cs.avg_skill_level, cp.avg_quality) AS skill_quality_correlation,

    -- Related entities for REST API
    JSON_OBJECT(
        'craftsdwarf_ids', (
            SELECT JSON_ARRAYAGG(wc.dwarf_id)
            FROM workshop_craftsdwarves wc
            WHERE wc.workshop_id = wa.workshop_id
        ),
        'product_ids', (
            SELECT JSON_ARRAYAGG(DISTINCT wp.product_id)
            FROM workshop_products wp
            WHERE wp.workshop_id = wa.workshop_id
        ),
        'material_ids', (
            SELECT JSON_ARRAYAGG(DISTINCT wm.material_id)
            FROM workshop_materials wm
            WHERE wm.workshop_id = wa.workshop_id
        ),
        'project_ids', (
            SELECT JSON_ARRAYAGG(p.project_id)
            FROM projects p
            WHERE p.workshop_id = wa.workshop_id
        )
    ) AS related_entities
FROM
    workshop_activity wa
LEFT JOIN
    material_efficiency me ON wa.workshop_id = me.workshop_id
LEFT JOIN
    craftsdwarf_skills cs ON wa.workshop_id = cs.workshop_id
LEFT JOIN
    craftsdwarf_productivity cp ON wa.workshop_id = cp.workshop_id AND cs.dwarf_id = cp.dwarf_id
GROUP BY
    wa.workshop_id, wa.workshop_name, wa.workshop_type, wa.num_craftsdwarves,
    wa.total_quantity_produced, wa.total_production_value, wa.total_days,
    wa.active_production_days, wa.total_materials_consumed,
    me.input_quantity, me.output_quantity, me.unique_inputs, me.unique_outputs
ORDER BY
    (wa.total_production_value::DECIMAL / NULLIF(wa.total_materials_consumed, 0)) *
    (wa.active_production_days::DECIMAL / NULLIF(wa.total_days, 0)) DESC;

-- AlTERNATIVE SOLUTION
SELECT jsonb_agg(result)
FROM (

WITH craftsdwarves_agg AS(
    SELECT wcd.workshop_id,
           COUNT(dwarf_id) as num_craftsdwarves
    FROM workshop_craftsdwarves wcd
    GROUP BY wcd.workshop_id
),
quantityproduced_agg AS (
    SELECT wp1.workshop_id,
           SUM(wp1.quantity) as total_quantity_produced
    FROM workshop_products wp1
    GROUP BY wp1.workshop_id
),
totprodvalue_agg AS (
    SELECT wp2.workshop_id,
           SUM(wp2.quantity * p.value) AS "total_production_value"
    FROM workshop_products wp2
    LEFT JOIN products p ON wp2.product_id = p.product_id
    GROUP BY wp2.workshop_id
),
dailyprodrate_agg AS (
    SELECT wp3.workshop_id,
           SUM(wp3.quantity) AS total_quantity,
           CURRENT_DATE - (DATE '2023-01-01') + 1 AS total_days
    FROM workshop_products wp3
    GROUP BY wp3.workshop_id
),
valuepermaterialunit_agg AS (
    SELECT wp4.workshop_id,
           SUM(p2.value) / COUNT(wp4.product_id) AS "value_per_material_unit"
    FROM workshop_products wp4
    LEFT JOIN products p2 ON wp4.workshop_id = p2.workshop_id
    GROUP BY wp4.workshop_id

),
workshoputpercent_agg AS (
    SELECT wp5.workshop_id,
           CURRENT_DATE - (DATE '2023-01-01') + 1 AS total_days,
           COUNT(DISTINCT(wp5.production_date)) AS work_days
    FROM workshop_products wp5
    GROUP BY workshop_id
),
materialconvratio_agg AS (
    SELECT w2.workshop_id,
           COALESCE(SUM(wp6.quantity), 0) AS output,
           COALESCE(SUM(wm2.quantity) FILTER (WHERE wm2.is_input), 0) AS input
    FROM workshops w2
    LEFT JOIN workshop_products wp6 ON w2.workshop_id = wp6.workshop_id
    LEFT JOIN workshop_materials wm2  ON w2.workshop_id = wm2.workshop_id
    GROUP BY w2.workshop_id
),
avcraftsdwarfskill_agg AS (
    SELECT wcd.workshop_id,
           ROUND(AVG(ds.level), 2) AS average_craftsdwarf_skill
    FROM workshop_craftsdwarves wcd
    JOIN (SELECT DISTINCT ON (dwarf_id) dwarf_id, level
          FROM dwarf_skills ds
                   JOIN skills s ON ds.skill_id = s.skill_id
          WHERE s.category = 'Crafting'
          ORDER BY dwarf_id, level DESC) ds ON wcd.dwarf_id = ds.dwarf_id
    JOIN workshops w3 ON wcd.workshop_id = w3.workshop_id
    GROUP BY wcd.workshop_id
),
skillqualcorr_agg AS (
    SELECT
        CORR(avg_level, quality_score) AS skill_quality_correlation
    FROM (
        SELECT
            w.workshop_id,
            AVG(ds.level)::numeric AS avg_level,
            CASE w.quality
                WHEN 'Good' THEN 1
                WHEN 'Excellent' THEN 2
                WHEN 'Rare' THEN 3
            END AS quality_score
        FROM workshop_craftsdwarves wc
        JOIN dwarf_skills ds ON wc.dwarf_id = ds.dwarf_id
        JOIN workshops w ON wc.workshop_id = w.workshop_id
        GROUP BY w.workshop_id, w.quality
    ) temp
)

SELECT
    -- "workshop_id" --
    w.workshop_id       AS "workshop_id",

    -- "workshop_name" --
    w.name              AS "workshop_name",

    -- "workshop_type" --
    w.type              AS "workshop_type",

    -- "num_craftsdwarves" --
    COALESCE(cda.num_craftsdwarves, 0) AS "num_craftsdwarves",

    -- "total_quantity_produced" --
    COALESCE(cpa.total_quantity_produced, 0) AS "total_quantity_produced",

    -- "total_production_value" --
    COALESCE(tpva.total_production_value, 0) AS "total_production_value",

    -- "daily_production_rate" --
    COALESCE(ROUND(dpra.totaL_quantity::DECIMAL / dpra.total_days::DECIMAL, 2), 0) AS "daily_production_rate", -- from 2023-01-01 to current date

    -- "value_per_material_unit" --
    ROUND(COALESCE(vpmua.value_per_material_unit, 0), 2) AS "value_per_material_unit",

    -- "workshop_utilization_percent" --
    ROUND(COALESCE(wupa.work_days::DECIMAL / wupa.total_days::DECIMAL, 0), 4) AS "workshop_utilization_percent",

    -- "material_conversion_ratio" --
    COALESCE(ROUND(mcra.input::DECIMAL / mcra.output::DECIMAL, 2), 0) AS material_conversion_ratio,

    -- "average_craftsdwarf_skill" --
    COALESCE(acsa.average_craftsdwarf_skill, 0) AS average_craftsdwarf_skill,

    -- "skill_quality_correlation" --
    (SELECT COALESCE(skill_quality_correlation, 0) FROM skillqualcorr_agg) AS skill_quality_correlation,

    -- "related-entities" --
    jsonb_build_object(
            'craftsdwarf_ids', (SELECT jsonb_agg(wc.dwarf_id)
                                FROM workshop_craftsdwarves wc
                                WHERE w.workshop_id = wc.workshop_id),
            'product_ids', (SELECT jsonb_agg(wp.product_id)
                            FROM workshop_products wp
                            WHERE w.workshop_id = wp.workshop_id),
            'material_ids', (SELECT jsonb_agg(wm.material_id)
                             FROM workshop_materials wm
                             WHERE w.workshop_id = wm.workshop_id),
            'project_ids', (SELECT jsonb_agg(p.project_id)
                            FROM projects p
                            WHERE w.workshop_id = p.workshop_id)
    )

FROM workshops w
         LEFT JOIN craftsdwarves_agg cda ON w.workshop_id = cda.workshop_id
         LEFT JOIN quantityproduced_agg cpa ON w.workshop_id = cpa.workshop_id
         LEFT JOIN totprodvalue_agg tpva ON w.workshop_id = tpva.workshop_id
         LEFT JOIN dailyprodrate_agg dpra ON w.workshop_id = dpra.workshop_id
         LEFT JOIN valuepermaterialunit_agg vpmua ON w.workshop_id = vpmua.workshop_id
         LEFT JOIN workshoputpercent_agg wupa ON w.workshop_id = wupa.workshop_id
         LEFT JOIN materialconvratio_agg mcra ON w.workshop_id = mcra.workshop_id
         LEFT JOIN avcraftsdwarfskill_agg acsa ON w.workshop_id = acsa.workshop_id
ORDER BY w.workshop_id

)AS result;

-- REFLECTION COMMENT
    -- EN:
        -- Let's look at the general high-level differences between the original and the alternative SQL query.
        -- One of the main differences between the two SQL queries is the number of CTE subqueries. The original version has 4 CTE subqueries - `workshop_activity`,
        -- `craftsdwarf_productivity`, `craftsdwarf_skills`, `material_efficiency`, while the alternative version has 9
        -- CTE subqueries. In the original version, the CTE subqueries rather form separate groups of intermediate values
        -- for further calculation of the required final values, while in the alternative query each individual
        -- CTE subquery forms only those values that are needed for specific final values
        -- (i.e. 1 CTE subquery for each final value). Also, as a difference, we can highlight the sorting of final answers
        -- in the original version using the construction (i.e., essentially, the fields value_per_material_unit, workshop_utilization_percent):
        -- ```
        -- ORDER BY
        -- (wa.total_production_value::DECIMAL / NULLIF(wa.total_materials_consumed, 0)) *
        -- (wa.active_production_days::DECIMAL / NULLIF(wa.total_days, 0)) DESC;`
        -- ```
        -- There is no sorting in the alternative version.
        -- ! Important point - when trying to execute the CTE subquery of the original version to form `workshop_activity`, a Cartesian Explosian occurs,
        -- which causes an incorrect calculation of `total_quantity_produced` and `total_production_value`, provided that:
        --   cardinality LEFT JOIN workshop_craftsdwarves = `1<->0..n`,
        --   cardinality LEFT JOIN workshop_products = `1<->0..n`,
        --   cardinality LEFT JOIN products = `1<->0..1`,
        -- Comparisons of final fields:
        -- * The fields `workshop_id`, `workshop_name` and `workshop type` are implemented identically, only in the original version
        --    they are calculated in the CTE subquery `workshop_activity` and then used without changes in the main SELECT query
        --    in the alternative version - they are directly calculated in the final SELECT query.
        -- * The field `num_craftsdwarves` is calculated identically, with the only difference being the use of DISTINCT, since in the CTE subquery
        --   `workshop_activity` several tables are joined at once using JOIN.
        -- * The fields `total_quantity_produced` and `total_production_value` are calculated identically, with the only difference being that in the original
        --   version there is one CTE subquery for both values, and in the alternative version there are separate CTE subqueries.
        -- * The `daily_production_rate` field is calculated identically, to find it we divide `total_quantity_produced` by the
        --   number of days. There is a difference in how the `total_days` quantity is calculated:
        --   ```
        --   MAX(wp.production_date) - MIN(wc.assignment_date)) -- original version
        --   vs
        --   CURRENT_DATE - (DATE '2023-01-01') + 1 -- alternative version
        -- * The `value_per_material_unit` field is calculated with differences, where in the original version the calculation is rather more correct:
        --   ```
        --   SUM(p.value * wp.quantity) / SUM(wm.quantity) -- original version
        --   vs
        --   SUM(p2.value) / COUNT(wp4.product_id) -- alternative version
        --   ```
        -- * The `workshop_utilization_percent` field is generally calculated identically, but as in the case of the field daily_production_rate
        --   there are differences in the positioning of the extreme dates.
        -- * The `material_conversion_ratio` field differs in terms of calculation logic, the calculation of the intermediate values ​​of `output_quantity`
        --   is calculated in the same way, and `input_quantity` with a difference - in the alternative version, an aggregate is calculated from the
        -- `workshop_products` table, and not from `workshop_materials`
        -- * The `average_craftsdwarf_skill` field is calculated in a similar way, but in the original version, a more concise version is implemented, in particular without using the `workshops` table. An important common point in the formula for calculating the field in both methods is the presence of the condition `s.category = 'Crafting'`
        -- * The `skill_quality_correlation` field is calculated in different ways, in particular, in the alternative version,
        --   the calculation is based on the idea that it is necessary to convert quality indicators from a text classification
        --   into a digital one explicitly through the CASE function...

    -- RU:
        -- Рассмотрим общие верхнеуровневые отличия оригинального и альтернативного SQL запроса. Одно из главных отличий
        -- двух SQL запросов в количестве CTE-подзапросов. В оригинальном варианте 4 СTE-подзапросов - `workshop_activity`,
        -- `craftsdwarf_productivity`, `craftsdwarf_skills`, `material_efficiency`, а в альтернативном варианте 9
        -- CTE-подзапросов. В оригинальном варианте CTE-подзапросы скорее формируют отдельные группы промежуточных значений
        -- для дальнейшего вычисления нужных финальных значений, в то время как в альтернативном запросе каждый отдельный
        -- CTE-подзапрос формирует только те значения, которые нужны для конкретных финальных значений
        -- (т.е. 1 CTE-подзапрос под каждое финальное значение). Также в качетсве отличия можно выделить сортировку итоговых
        -- ответов в оригианльном варианте с помощью конструкции (т.е. по сути полей value_per_material_unit, workshop_utilization_percent):
        -- ```
        -- ORDER BY
        -- (wa.total_production_value::DECIMAL / NULLIF(wa.total_materials_consumed, 0)) *
        -- (wa.active_production_days::DECIMAL / NULLIF(wa.total_days, 0)) DESC;`
        -- ```
        -- В альтернативном варианте сортировки нет.
        -- ! Важный момент - при попытке выполнить CTE-подзапрос оригинального варианта для формирования `workshop_activity` происходит Cartesian Explosian,
        --   что вызывает неккоректный просчет `total_quantity_produced` и `total_production_value` при условии того, что:
        --   cardinality LEFT JOIN workshop_craftsdwarves = `1<->0..n`,
        --   cardinality LEFT JOIN workshop_products = `1<->0..n`,
        --   cardinality LEFT JOIN products = `1<->0..1`,
        -- Сравнения финальных полей:
        -- * Поля `workshop_id`, `workshop_name` и `workshop type` реализованы идентично, только в оригинальном варианте
        --   вычисляются в CTE-подзапросе `workshop_activity` и затем без измененений используются в основном SELECT запросе
        --   в альтернативном варианте - напрямую вычисляются в финальном SELECT запросе.
        -- * Поле `num_craftsdwarves` вычисляется идентично, лишь с отличием в использовании DISTINCT, так как в CTE-подзапрое
        --   `workshop_activity` с помощью JOIN объединяются сразу несколько таблиц.
        --  * Поля `total_quantity_produced` и `total_production_value` вычисляются идентично с отличием лишь в том что в оригианльном
        --    варианте один CTE-подзапрос для обоих значений, а в альтернативном варианте отдельные CTE-подзапросы.
        --  * Поле `daily_production_rate` вычислятся идентично, чтобы его найти поризводим деление `total_quantity_produced` на
        --    количество дней. Присутствует отличие в том как расчитывается количество `total_days`:
        --    ```
        --    MAX(wp.production_date) - MIN(wc.assignment_date))  -- оригаинальный вариант
        --    vs
        --    CURRENT_DATE - (DATE '2023-01-01') + 1  -- альтернативный вариант
        --  * Поле `value_per_material_unit` рассчитвается с отличиями, где в оригианальном варианте скорее более корректен рассчет:
        --    ```
        --    SUM(p.value * wp.quantity) / SUM(wm.quantity) -- оригаинальный вариант
        --    vs
        --    SUM(p2.value) / COUNT(wp4.product_id)   -- альтернативный вариант
        --    ```
        --  * Поле `workshop_utilization_percent` рассчитывается в общем случае идентично, но как и в случае с полем daily_production_rate
        --    имеются отличия в позиционировании крайних дат.
        --  * Поле `material_conversion_ratio` отличается в плане логики расчета, расчет промежуточного значений`output_quantity`
        --    рассчитывается одинаково, а `input_quantity` с отличием - в альтернативном варианте считается агрегат из
        --    таблицы `workshop_products`, а не из `workshop_materials`
        --  * Поле `average_craftsdwarf_skill`рассчитывается похожис образом, однако в оригинальном варианте реализована
        --    более лаконичная версия, в частности без использования таблицы `workshops. Важным общим моментов в формуле
        --    вчисления поля в обоих способоах является наличие условия `s.category = 'Crafting'`
        --  * Поле `skill_quality_correlation` рассчитывается разными способами, в частности, в альтернативном варианте
        --    происходит расчет из представления что необходимо преобразовать показатели качества из текстовой классификации
        --    в цифровую явным путем через функцию CASE...
