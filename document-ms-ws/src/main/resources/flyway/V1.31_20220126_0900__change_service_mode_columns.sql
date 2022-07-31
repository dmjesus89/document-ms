ALTER TABLE `service_mode`
    CHANGE COLUMN `min_trans_money` `min_trans_amount` DECIMAL(8,4) NULL DEFAULT NULL AFTER `max_pre_authorize_time`,
    CHANGE COLUMN `min_trans_volume` `min_trans_volume` DECIMAL(8,4) NULL DEFAULT NULL AFTER `min_trans_amount`;