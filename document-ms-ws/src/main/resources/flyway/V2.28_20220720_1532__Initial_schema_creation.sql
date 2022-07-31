
CREATE TABLE `device_type` (
                               `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                               `code` varchar(36) CHARACTER SET utf8 NOT NULL,
                               `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                               `is_enabled` tinyint(1) NOT NULL,
                               `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                               `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `device_type_uq` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;



CREATE TABLE `device_subtype` (
                                  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                                  `code` varchar(36) CHARACTER SET utf8 NOT NULL,
                                  `device_type_id` tinyint(3) unsigned NOT NULL,
                                  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                  `is_enabled` tinyint(1) NOT NULL,
                                  `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                  `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `device_subtype_uq` (`code`,`device_type_id`),
                                  KEY `fk_device_subtype_device_type1_idx` (`device_type_id`),
                                  CONSTRAINT `fk_device_subtype_device_type1` FOREIGN KEY (`device_type_id`) REFERENCES `device_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;



CREATE TABLE `device` (
                          `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                          `code` varchar(36) NOT NULL,
                          `device_type_id` tinyint(3) unsigned NOT NULL,
                          `device_subtype_id` tinyint(3) unsigned DEFAULT NULL,
                          `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                          `configuration` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`configuration`)),
                          `is_enabled` tinyint(1) NOT NULL,
                          `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                          `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `device_uq` (`code`),
                          KEY `fk_device_device_type_idx` (`device_type_id`),
                          KEY `fk_device_device_subtype1_idx` (`device_subtype_id`),
                          CONSTRAINT `fk_device_device_subtype1` FOREIGN KEY (`device_subtype_id`) REFERENCES `device_subtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                          CONSTRAINT `fk_device_device_type` FOREIGN KEY (`device_type_id`) REFERENCES `device_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;



CREATE TABLE `document_type` (
                                 `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                 `code` varchar(50) NOT NULL,
                                 `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                 `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;



CREATE TABLE `device_pos` (
                              `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                              `device_id` smallint(5) unsigned NOT NULL,
                              `number` smallint(6) NOT NULL,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `device_pos_uq` (`device_id`),
                              KEY `fk_device_pos_device1_idx` (`device_id`),
                              CONSTRAINT `fk_device_pos_device1` FOREIGN KEY (`device_id`) REFERENCES `device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;



CREATE TABLE `document` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                            `code` varchar(255) NOT NULL,
                            `document_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                            `document_date` datetime NOT NULL,
                            `created_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            `integration_status_id` smallint(5) unsigned DEFAULT NULL,
                            `vatin` varchar(255) DEFAULT NULL,
                            `total_amount` decimal(10,4) DEFAULT NULL,
                            `device_pos_id` smallint(5) unsigned DEFAULT NULL,
                            `customer_code` varchar(255) DEFAULT NULL,
                            `is_pending_prepayment_change` tinyint(1) DEFAULT NULL,
                            `is_stock_movement` tinyint(1) DEFAULT 1,
                            `parent_id` bigint(20) unsigned DEFAULT NULL,
                            `child_id` bigint(20) unsigned DEFAULT NULL,
                            `rectification_id` bigint(20) unsigned DEFAULT NULL,
                            `document_type_id` smallint(5) unsigned DEFAULT NULL,
                            `qr_code` varchar(55) DEFAULT NULL,
                            `invoice_no` varchar(100) DEFAULT NULL,
                            `pos_number` smallint(5) unsigned DEFAULT 1,
                            `tax_amount` decimal(13,4) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `fk_document_integration_status_idx` (`integration_status_id`),
                            KEY `fk_document_device_pos` (`device_pos_id`),
                            KEY `fk_document_document_parent` (`parent_id`),
                            KEY `fk_document_document_child` (`child_id`),
                            KEY `fk_document_document_rectification` (`rectification_id`),
                            KEY `document_FK_document_type` (`document_type_id`),
                            KEY `idx_document_date_id` (`id`,`document_date`),
                            CONSTRAINT `document_FK_document_type` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`id`),
                            CONSTRAINT `fk_document_device_pos` FOREIGN KEY (`device_pos_id`) REFERENCES `device_pos` (`id`),
                            CONSTRAINT `fk_document_document_child` FOREIGN KEY (`child_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                            CONSTRAINT `fk_document_document_parent` FOREIGN KEY (`parent_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                            CONSTRAINT `fk_document_document_rectification` FOREIGN KEY (`rectification_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=520 DEFAULT CHARSET=latin1;




CREATE TABLE `tax` (
                       `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                       `code` varchar(255) NOT NULL,
                       `description` varchar(255) NOT NULL,
                       `receipt_description` varchar(255) NOT NULL,
                       `amount_value` decimal(13,4) DEFAULT NULL,
                       `percentage_value` decimal(13,4) DEFAULT NULL,
                       `tax_type_code` varchar(255) NOT NULL,
                       `tax_type_description` varchar(255) NOT NULL,
                       `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                       `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `tax_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;



CREATE TABLE `product_family` (
                                  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                  `parent_id` smallint(5) unsigned DEFAULT NULL,
                                  `code` varchar(36) NOT NULL,
                                  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                  `is_item_allowed` tinyint(1) NOT NULL,
                                  `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                  `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                  `color` varchar(10) DEFAULT NULL,
                                  `image_url` varchar(350) DEFAULT NULL,
                                  `is_enabled` tinyint(1) DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `product_family_uq` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `product_group` (
                                 `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                 `code` varchar(36) CHARACTER SET utf8 NOT NULL,
                                 `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                 `is_enabled` tinyint(1) NOT NULL,
                                 `created_on` datetime NOT NULL,
                                 `updated_on` datetime NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;


CREATE TABLE `product` (
                           `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                           `code` varchar(36) CHARACTER SET utf8 NOT NULL,
                           `product_group_id` smallint(5) unsigned DEFAULT NULL,
                           `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                           `short_description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`short_description`)),
                           `is_fuel` tinyint(1) NOT NULL,
                           `is_enabled` tinyint(1) NOT NULL,
                           `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                           `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                           `created_by` varchar(45) DEFAULT NULL,
                           `updated_by` varchar(45) DEFAULT NULL,
                           `reference_unit_price` decimal(10,4) NOT NULL,
                           `uom_code` varchar(15) NOT NULL,
                           `fcc_product_code` varchar(15) DEFAULT NULL,
                           `color` varchar(10) DEFAULT NULL,
                           `image_url` varchar(350) DEFAULT NULL,
                           `family_id` smallint(5) unsigned DEFAULT NULL,
                           `tax_id` tinyint(3) unsigned DEFAULT NULL,
                           `unit_price` decimal(9,3) DEFAULT NULL,
                           `default_quantity` decimal(9,3) DEFAULT NULL,
                           `prompt_quantity` bit(1) DEFAULT NULL,
                           `custom_price` bit(1) DEFAULT NULL,
                           `prompt_price` bit(1) DEFAULT NULL,
                           `price_by_weight` bit(1) DEFAULT NULL,
                           `fiscal_info_required` bit(1) DEFAULT NULL,
                           `refundable` bit(1) DEFAULT NULL,
                           `refund_max_period` smallint(5) DEFAULT NULL,
                           `is_favorite` tinyint(1) DEFAULT 0,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `product_group` (`code`),
                           KEY `fk_product_product_group1_idx` (`product_group_id`),
                           KEY `fk_product_family1_idx` (`family_id`),
                           KEY `fk_product_tax_id` (`tax_id`),
                           CONSTRAINT `fk_product_family1` FOREIGN KEY (`family_id`) REFERENCES `product_family` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                           CONSTRAINT `fk_product_product_group1` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                           CONSTRAINT `fk_product_tax_id` FOREIGN KEY (`tax_id`) REFERENCES `tax` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;



CREATE TABLE `document_line_detail` (
                                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                        `document_id` bigint(20) unsigned DEFAULT NULL,
                                        `tax_id` tinyint(3) unsigned DEFAULT NULL,
                                        `product_id` smallint(5) unsigned NOT NULL,
                                        `is_fuel` tinyint(1) NOT NULL,
                                        `unit_price_gross` decimal(10,4) NOT NULL,
                                        `unit_price_net` decimal(10,4) NOT NULL,
                                        `warehouse_code` varchar(255) NOT NULL,
                                        `pump_number` tinyint(3) unsigned DEFAULT NULL,
                                        `nozzle_number` tinyint(3) unsigned DEFAULT NULL,
                                        `paid_amount` decimal(13,4) NOT NULL,
                                        `paid_quantity` decimal(13,4) NOT NULL,
                                        `paid_discount` decimal(13,4) NOT NULL,
                                        `paid_tax` decimal(13,4) NOT NULL,
                                        `provided_amount` decimal(13,4) DEFAULT NULL,
                                        `provided_quantity` decimal(13,4) DEFAULT NULL,
                                        `code` varchar(255) DEFAULT NULL,
                                        `refunded_document_id` bigint(20) unsigned DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `idx_document_line_detail_document` (`document_id`),
                                        KEY `idx_document_line_detail_tax` (`tax_id`),
                                        KEY `idx_document_line_detail_product` (`product_id`),
                                        KEY `fk_document_document_refunded` (`refunded_document_id`),
                                        CONSTRAINT `fk_document_document_refunded` FOREIGN KEY (`refunded_document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_document_line_detail_document` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_document_line_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                        CONSTRAINT `fk_document_line_detail_tax` FOREIGN KEY (`tax_id`) REFERENCES `tax` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=795 DEFAULT CHARSET=latin1;



CREATE TABLE `document_mop_movement_type` (
                                              `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                              `code` varchar(50) NOT NULL,
                                              `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                              `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                              `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                              PRIMARY KEY (`id`),
                                              UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;



CREATE TABLE `payment_mode` (
                                `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
                                `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                `is_enabled` tinyint(1) NOT NULL,
                                `open_cash_drawer` tinyint(1) NOT NULL,
                                `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                `order_no` smallint(5) unsigned DEFAULT NULL,
                                `prepayment_allowed` tinyint(1) NOT NULL DEFAULT 1,
                                `refund_allowed` tinyint(1) NOT NULL DEFAULT 1,
                                `change_allowed` tinyint(1) NOT NULL DEFAULT 1,
                                `cancel_allowed` tinyint(1) NOT NULL DEFAULT 1,
                                `printer_allowed` tinyint(1) NOT NULL DEFAULT 1,
                                `pay_entire_transaction` tinyint(1) NOT NULL DEFAULT 1,
                                `receipt_copies` smallint(5) unsigned DEFAULT NULL,
                                `auto_receipt` tinyint(1) NOT NULL DEFAULT 1,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `payment_uq` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



CREATE TABLE `document_mop_detail` (
                                       `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                       `document_id` bigint(20) unsigned DEFAULT NULL,
                                       `mop_id` smallint(5) unsigned NOT NULL,
                                       `paid_amount` decimal(13,4) NOT NULL,
                                       `code` varchar(255) DEFAULT NULL,
                                       `document_mop_movement_type_id` smallint(5) unsigned NOT NULL DEFAULT 1,
                                       PRIMARY KEY (`id`),
                                       KEY `idx_document_mop_detail_document` (`document_id`),
                                       KEY `idx_document_mop_detail_document_payment_mode` (`mop_id`),
                                       KEY `document_mop_detail_FK_document_mop` (`document_mop_movement_type_id`),
                                       CONSTRAINT `document_mop_detail_FK_document_mop` FOREIGN KEY (`document_mop_movement_type_id`) REFERENCES `document_mop_movement_type` (`id`),
                                       CONSTRAINT `fk_document_mop_detail_document` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                       CONSTRAINT `fk_document_mop_detail_document_payment_mode` FOREIGN KEY (`mop_id`) REFERENCES `payment_mode` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=676 DEFAULT CHARSET=latin1;



CREATE TABLE `document_series` (
                                   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                   `code` varchar(255) NOT NULL,
                                   `is_enabled` tinyint(1) NOT NULL DEFAULT 1,
                                   `document_series_type` varchar(50) NOT NULL,
                                   `last_sale_number` bigint(20) NOT NULL,
                                   `last_document_signature` varchar(1024) DEFAULT NULL,
                                   `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`data`)),
                                   `created_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                   `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=latin1;


CREATE TABLE `document_series_audit` (
                                         `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                         `document_serie_id` bigint(20) unsigned NOT NULL,
                                         `document_id` bigint(20) unsigned NOT NULL,
                                         `document_signature` varchar(1024) DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `fk_document_series_audit_document_series` (`document_serie_id`),
                                         KEY `fk_document_series_audit_document` (`document_id`),
                                         CONSTRAINT `fk_document_series_audit_document` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                         CONSTRAINT `fk_document_series_audit_document_series` FOREIGN KEY (`document_serie_id`) REFERENCES `document_series` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=latin1;



CREATE TABLE `shift_status` (
                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                `code` varchar(50) NOT NULL,
                                `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `code` (`code`),
                                UNIQUE KEY `id_UNIQUE_shit_status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;



CREATE TABLE `user` (
                        `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                        `username` varchar(50) NOT NULL,
                        `name` varchar(150) NOT NULL,
                        `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`description`)),
                        `profile_id` tinyint(3) unsigned NOT NULL,
                        `entity_id` smallint(5) unsigned NOT NULL,
                        `locale_id` smallint(5) unsigned NOT NULL DEFAULT 1,
                        `password` varchar(255) NOT NULL,
                        `is_enabled` tinyint(1) NOT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `email2` varchar(255) DEFAULT NULL,
                        `phone` varchar(45) DEFAULT NULL,
                        `phone2` varchar(45) DEFAULT NULL,
                        `observations` varchar(255) DEFAULT NULL,
                        `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                        `first_login_on` datetime DEFAULT NULL,
                        `last_login_on` datetime DEFAULT NULL,
                        `password_expired` tinyint(1) NOT NULL DEFAULT 0,
                        `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username` (`username`),
                        UNIQUE KEY `id_UNIQUE_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;



CREATE TABLE `shift_type` (
                              `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                              `code` varchar(50) NOT NULL,
                              `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                              `parent_id` int(10) unsigned DEFAULT NULL,
                              `open_time` time DEFAULT NULL,
                              `close_time` time DEFAULT NULL,
                              `close_related_shifts` tinyint(1) NOT NULL DEFAULT 0,
                              `open_with_related_shifts` tinyint(1) NOT NULL DEFAULT 0,
                              `allow_suspension` tinyint(1) NOT NULL DEFAULT 0,
                              `break_by_user` tinyint(1) NOT NULL DEFAULT 0,
                              `break_by_device` tinyint(1) NOT NULL DEFAULT 0,
                              `is_enabled` tinyint(1) NOT NULL,
                              `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                              `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `code` (`code`),
                              UNIQUE KEY `id_UNIQUE_shit_status` (`id`),
                              KEY `fk_parent_idx` (`parent_id`),
                              CONSTRAINT `fk_parent_idx` FOREIGN KEY (`parent_id`) REFERENCES `shift_type` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;



CREATE TABLE `shift` (
                         `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                         `code` varchar(50) NOT NULL,
                         `shift_type_id` int(10) unsigned NOT NULL,
                         `shift_status_id` int(10) unsigned NOT NULL,
                         `parent_id` int(10) unsigned DEFAULT NULL,
                         `open_date` datetime NOT NULL DEFAULT current_timestamp(),
                         `open_user_id` int(10) unsigned NOT NULL,
                         `open_device_id` smallint(5) unsigned NOT NULL,
                         `close_date` datetime DEFAULT NULL,
                         `close_user_id` int(10) unsigned DEFAULT NULL,
                         `close_device_id` smallint(5) unsigned DEFAULT NULL,
                         `is_enabled` tinyint(1) NOT NULL,
                         `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                         `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `code` (`code`),
                         UNIQUE KEY `id_UNIQUE_shit` (`id`),
                         KEY `shift_type_id` (`shift_type_id`),
                         KEY `shift_status_id` (`shift_status_id`),
                         KEY `shift_parent_id` (`parent_id`),
                         KEY `open_user_id` (`open_user_id`),
                         KEY `fk_open_device_id` (`open_device_id`),
                         KEY `close_user_id` (`close_user_id`),
                         KEY `close_device_id` (`close_device_id`),
                         CONSTRAINT `fk_close_device_id` FOREIGN KEY (`close_device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                         CONSTRAINT `fk_close_user_id` FOREIGN KEY (`close_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                         CONSTRAINT `fk_open_device_id` FOREIGN KEY (`open_device_id`) REFERENCES `device` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                         CONSTRAINT `fk_open_user_id` FOREIGN KEY (`open_user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                         CONSTRAINT `fk_shift_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `shift` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                         CONSTRAINT `fk_shift_status_id` FOREIGN KEY (`shift_status_id`) REFERENCES `shift_status` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
                         CONSTRAINT `fk_shift_type_id` FOREIGN KEY (`shift_type_id`) REFERENCES `shift_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=latin1;


CREATE TABLE `shift_document` (
                                  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
                                  `shift_id` int(10) unsigned NOT NULL,
                                  `document_id` bigint(20) unsigned DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `idx_shift_document_shift` (`shift_id`),
                                  KEY `idx_shift_document_document` (`document_id`),
                                  CONSTRAINT `fk_shift_document_document` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                  CONSTRAINT `fk_shift_document_shift` FOREIGN KEY (`shift_id`) REFERENCES `shift` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1089 DEFAULT CHARSET=latin1;