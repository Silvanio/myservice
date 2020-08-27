CREATE INDEX "IDX_DATE_QUOTE"
    ON market.tb_stock_historical_data USING btree
    (code_stock ASC NULLS LAST, dt_quote ASC NULLS LAST)
    INCLUDE(code_stock, dt_quote)
;

COMMENT ON INDEX market."IDX_DATE_QUOTE" IS 'IDX_DATE_QUOTE';