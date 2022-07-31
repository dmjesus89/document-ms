ALTER TABLE `fuelling_mode`
    CHANGE COLUMN `max_transaction_volume` `max_transaction_volume` MEDIUMINT(7) UNSIGNED NOT NULL AFTER `max_fuelling_time`,
    CHANGE COLUMN `max_transaction_amount` `max_transaction_amount` MEDIUMINT(7) UNSIGNED NOT NULL AFTER `max_transaction_volume`;