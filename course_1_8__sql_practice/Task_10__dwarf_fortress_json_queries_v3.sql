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