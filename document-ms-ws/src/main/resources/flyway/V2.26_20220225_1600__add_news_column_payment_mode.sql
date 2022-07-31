ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `order_no` smallint(5) unsigned;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `prepayment_allowed` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `refund_allowed` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `change_allowed` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `cancel_allowed` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `printer_allowed` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `pay_entire_transaction` tinyint(1) NOT NULL DEFAULT 1;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `receipt_copies` smallint(5) unsigned;
ALTER TABLE payment_mode
    ADD COLUMN IF NOT EXISTS `auto_receipt` tinyint(1) NOT NULL DEFAULT 1;