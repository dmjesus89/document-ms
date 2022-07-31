ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `open_cash_drawer` tinyint(1) NOT NULL;