CREATE TABLE IF NOT EXISTS `site_payment_mode` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_id` smallint(5) unsigned,
    `payment_mode_id` smallint(5) unsigned,
     PRIMARY KEY (`id`) USING BTREE,
     CONSTRAINT `site_device_fk` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
     CONSTRAINT `payment_mode_fk` FOREIGN KEY (`payment_mode_id`) REFERENCES `payment_mode` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);


ALTER TABLE payment_mode ADD COLUMN IF NOT EXISTS  payment_mode_ref_id SMALLINT(5) UNSIGNED;