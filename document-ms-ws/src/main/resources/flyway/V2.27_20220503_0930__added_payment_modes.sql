INSERT IGNORE INTO payment_mode
(code, description, is_enabled, document_type_id, payment_mode_ref_id, open_cash_drawer,
 order_no, prepayment_allowed, refund_allowed, change_allowed, cancel_allowed, printer_allowed, pay_entire_transaction,
 receipt_copies, auto_receipt)
VALUES ('FLEET_CREDIT', '{"pt-pt":"Crédito Frota","null":"Fleet Credit","en-en":"Fleet Credit"}', 1, 1, NULL, 0, 1, 1,
        1, 1, 1, 1, 1, 0, 1);
INSERT IGNORE INTO payment_mode
(code, description, is_enabled, document_type_id, payment_mode_ref_id, open_cash_drawer,
 order_no, prepayment_allowed, refund_allowed, change_allowed, cancel_allowed, printer_allowed, pay_entire_transaction,
 receipt_copies, auto_receipt)
VALUES ('LOCAL_CREDIT', '{"pt-pt":"Crédito Local","null":"Local Credit","en-en":"Local Credit"}', 1, NULL, NULL, 0, 2,
        1, 1, 1, 1, 1, 1, 0, 1);
INSERT IGNORE INTO payment_mode
(code, description, is_enabled, document_type_id, payment_mode_ref_id, open_cash_drawer,
 order_no, prepayment_allowed, refund_allowed, change_allowed, cancel_allowed, printer_allowed, pay_entire_transaction,
 receipt_copies, auto_receipt)
VALUES ('MONEY', '{"pt-pt":"Dinheiro","null":"Money","en-en":"Cash"}', 1, 1, NULL, 0, 3, 1, 1, 1, 1, 1, 1, 0, 1);
INSERT IGNORE INTO payment_mode(code, description, is_enabled, document_type_id,
                                payment_mode_ref_id, open_cash_drawer, order_no, prepayment_allowed,
                                refund_allowed, change_allowed, cancel_allowed, printer_allowed,
                                pay_entire_transaction, receipt_copies, auto_receipt)
VALUES ('BANK_CHECK', '{"pt-pt":"Dinheiro","null":"Money","en-en":"Bank check"}', 1, 1, NULL, 0, 3, 1, 1, 1, 1, 1, 1, 0,
        1);



CREATE TABLE `document_sale_type`
(
    `id`          tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code`        varchar(10)         NOT NULL,
    `description` json                NOT NULL,
    `created_on`  datetime            NOT NULL DEFAULT current_timestamp(),
    `updated_on`  datetime            NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `document_sale_typetype_uq` (`code`)
);


INSERT IGNORE INTO document_sale_type(code, description)
values ('SALE', '{
  "en-en": "Sale"
}');
INSERT IGNORE INTO document_sale_type(code, description)
values ('REFUND', '{
  "en-en": "Refund"
}');

ALTER TABLE document
    ADD document_sale_type_id tinyint(3) DEFAULT 1 NOT NULL;
