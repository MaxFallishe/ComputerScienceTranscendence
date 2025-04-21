SELECT jsonb_agg(result)
FROM (


WITH trades_data AS (
    SELECT
        COALESCE(SUM(td.value), 0) AS all_time_trade_value,
        COALESCE(SUM(td.value) FILTER ( WHERE td.balance_direction = 'Out' ), 0) AS all_time_trade_output_balance,
        COALESCE(SUM(td.value) FILTER ( WHERE td.balance_direction = 'In' ), 0) AS all_time_trade_input_balance
    FROM
        trade_transactions td
),
trading_partners AS (
    SELECT COUNT(DISTINCT(c.civilization_type)) AS total_trading_partners
    FROM caravans c
),
diplomatic_stats AS (
    SELECT
        de.civilization_type,
        CORR(de.relationship_change, td.value) AS diplomatic_correlation
    FROM diplomatic_events de
    JOIN trade_transactions td ON de.caravan_id = td.caravan_id
    GROUP BY de.civilization_type
),
civilization_trades AS (
    SELECT
        c.civilization_type AS civilization_type,
        COUNT(DISTINCT(c.caravan_id)) AS total_caravans,
        jsonb_agg(DISTINCT(c.caravan_id)) AS caravan_ids,
        COALESCE(SUM(td.value), 0) AS total_trade_value,
        COALESCE(SUM(td.value) FILTER ( WHERE td.balance_direction = 'Out' ), 0) AS all_time_trade_output_balance,
        COALESCE(SUM(td.value) FILTER ( WHERE td.balance_direction = 'In' ), 0) AS all_time_trade_input_balance,
        COALESCE(ds.diplomatic_correlation, 0) AS diplomatic_correlation
    FROM caravans c
    LEFT JOIN trade_transactions td ON c.caravan_id = td.caravan_id
    LEFT JOIN diplomatic_stats ds ON c.civilization_type = ds.civilization_type
    GROUP BY c.civilization_type, ds.diplomatic_correlation
),
resources_dependency AS (
    SELECT
        cg.material_type,
        COUNT(DISTINCT(cg.goods_id)) AS times_imported,
        SUM(cg.quantity) AS total_imported,
        SUM(cg.value) AS total_import_value,
        COUNT(DISTINCT c.caravan_id) AS import_diversity,
        COUNT(DISTINCT cg.goods_id) * SUM(cg.quantity) * (1.0 / NULLIF(COUNT(DISTINCT(c.civilization_type)), 0)) AS dependency_score,
        jsonb_agg(DISTINCT(c.caravan_id)) AS resource_ids
    FROM caravan_goods cg
    JOIN caravans c ON cg.caravan_id = c.caravan_id
    JOIN resources r ON cg.material_type = r.name
    WHERE cg.type = 'Import'
    GROUP BY cg.material_type
),
workshop_export_effectiveness AS (
    SELECT
        p.type AS product_type,
        w.type AS workshop_type,
        COUNT(DISTINCT CASE WHEN cg.goods_id IS NOT NULL THEN p.product_id END) AS products_exported,
        COUNT(DISTINCT p.product_id) AS products_created,
        SUM(p.value) AS total_production_value,
        SUM(CASE WHEN cg.goods_id IS NOT NULL THEN cg.value ELSE 0 END) AS export_value,
        AVG(CASE WHEN cg.goods_id IS NOT NULL THEN (cg.value / p.value) ELSE NULL END) AS avg_export_markup,
        jsonb_agg(DISTINCT w.workshop_id) AS workshop_ids
    FROM products p
    JOIN workshops w ON p.workshop_id = w.workshop_id
    LEFT JOIN caravan_goods cg ON p.product_id = cg.original_product_id AND cg.type = 'Export'
    GROUP BY p.type, w.type
),
trade_growth AS (
    SELECT
        EXTRACT(YEAR FROM td.date)::int AS year,
        EXTRACT(QUARTER FROM td.date)::int AS quarter,
        SUM(td.value)::int AS quarterly_value,
        SUM(td.value) FILTER (WHERE td.balance_direction = 'In')
            - SUM(td.value) FILTER (WHERE td.balance_direction = 'Out') AS quarterly_balance,
        COUNT(DISTINCT c.civilization_type) AS trade_diversity
    FROM trade_transactions td
    JOIN caravans c ON td.caravan_id = c.caravan_id
    GROUP BY year, quarter
)
SELECT
    -- "total_trading_partners" --
    tp.total_trading_partners,

    -- "all_time_trade_value" --
    td.all_time_trade_value,

    -- "all_time_trade_balance" --
    td.all_time_trade_output_balance,
    td.all_time_trade_input_balance,
    td.all_time_trade_input_balance - td.all_time_trade_output_balance AS all_time_trade_balance,

    -- "civilization_data" --
    jsonb_build_object(
        'civilization_trade_data', (
            SELECT jsonb_agg(
                jsonb_build_object(
                    'civilization_type', ct.civilization_type,
                    'total_caravans', ct.total_caravans,
                    'total_trade_value', ct.total_trade_value,
                    'trade_balance', ct.all_time_trade_input_balance - td.all_time_trade_output_balance,
                    'trade_relationship',
                    (CASE
                        WHEN ct.all_time_trade_input_balance - ct.all_time_trade_output_balance > 0 THEN 'Favorable'
                        WHEN ct.all_time_trade_input_balance - ct.all_time_trade_output_balance < 0 THEN 'Unfavorable'
                        ELSE 'Neutral'
                    END),
                    'caravan_ids', ct.caravan_ids,
                    'diplomatic_correlation', ct.diplomatic_correlation
                )
            )
            FROM civilization_trades ct
        )
    ) AS civilization_data,

    -- "critical_import_dependencies" --
    jsonb_build_object(
        'resource_dependency', (
            SELECT jsonb_agg(
                jsonb_build_object(
                    'material_type', rd.material_type,
                    'dependency_score', rd.dependency_score,
                    'total_imported', rd.total_imported,
                    'import_diversity', rd.import_diversity,
                    'resource_ids', rd.resource_ids
                )
            ) FROM resources_dependency rd
        )
    ) AS critical_import_dependencies,

    -- "export_effectiveness" --
        jsonb_build_object(
        'export_effectiveness', (
            SELECT jsonb_agg(
                jsonb_build_object(
                    'workshop_type', wee.product_type,
                    'product_type', wee.workshop_type,
                    'export_ratio', ROUND((wee.products_exported::DECIMAL / NULLIF(wee.products_created, 0)) * 100, 2),
                    'avg_markup', wee.avg_export_markup,
                    'workshop_ids', wee.workshop_ids
                )
            ) FROM workshop_export_effectiveness wee
              WHERE wee.products_created > 0
        )
    ) AS export_effectiveness,

    -- "trade_timeline" --
    jsonb_build_object(
        'trade_timeline', jsonb_build_object(
            'trade_growth', (
                SELECT jsonb_agg(
                    jsonb_build_object(
                        'year', tg.year,
                        'quarter', tg.quarter,
                        'quarterly_value', tg.quarterly_value,
                        'quarterly_balance', tg.quarterly_balance,
                        'trade_diversity', tg.trade_diversity
                    )
                ) FROM trade_growth tg
            )
        )
    ) AS trade_timeline

FROM
    trading_partners tp
CROSS JOIN trades_data td




) AS result
