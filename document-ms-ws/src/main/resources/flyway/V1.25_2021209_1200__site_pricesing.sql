CREATE TABLE IF NOT EXISTS `site_device_price_sign` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `price_sign_code` varchar(36) NOT NULL DEFAULT '1',
    `communication_method_data` LONGTEXT NULL,
    `communication_method_id` tinyint(3) unsigned NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `site_device_id_UNIQUE` (`id`) USING BTREE,
    KEY `fk_site_device_price_sign` (`id`) USING BTREE,
   CONSTRAINT `site_device_price_sign_fk` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='conjunto de price signs';



CREATE TABLE IF NOT EXISTS `grade_site_device_price_sign` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_device_price_sign_id` smallint(5) unsigned NOT NULL,
    `grade_id` smallint(5) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_site_device_price_sign_grade` (`site_device_price_sign_id`,`grade_id`),
    KEY `fk_site_price_device_sign_idx2` (`site_device_price_sign_id`),
    KEY `fk_grade_idx2` (`grade_id`),
    CONSTRAINT `fk_site_price_device_sign_idx3` FOREIGN KEY (`site_device_price_sign_id`) REFERENCES `site_device_price_sign` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_grade_idx3` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
