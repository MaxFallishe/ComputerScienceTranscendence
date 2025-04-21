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
    ) AS civilization_data

FROM
    trading_partners tp
CROSS JOIN trades_data td




) AS result
