-- --------------------------------------------------------
-- Anfitrião:                    127.0.0.1
-- Versão do servidor:           10.4.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Versão:              11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping structure for table site.card_system
CREATE TABLE IF NOT EXISTS `card_system` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `card_system_uq` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document
CREATE TABLE IF NOT EXISTS `document` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    `entity_code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `entity_rank_order` tinyint(4) NOT NULL,
    `document_serie_id` smallint(5) unsigned NOT NULL,
    `number` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
    `document_date` datetime NOT NULL,
    `number_of_lines` smallint(5) unsigned NOT NULL,
    `net_amount` decimal(13,4) NOT NULL,
    `tax_amount` decimal(13,4) NOT NULL,
    `gross_amount` decimal(13,4) NOT NULL,
    `discount_amount` decimal(13,4) NOT NULL,
    `total_amount` decimal(13,4) NOT NULL,
    `receipt_data` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `document_uq` (`code`),
    UNIQUE KEY `document_uq2` (`document_serie_id`,`number`,`document_date`),
    KEY `fk_document_document_serie1_idx` (`document_serie_id`),
    CONSTRAINT `fk_document_document_serie1` FOREIGN KEY (`document_serie_id`) REFERENCES `document_serie` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=708 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_legal_authority
CREATE TABLE IF NOT EXISTS `document_legal_authority` (
    `document_id` int(10) unsigned NOT NULL,
    `hash` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `hash_print` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `hash_control` tinyint(4) DEFAULT NULL,
    `certificate_number` smallint(5) unsigned DEFAULT NULL,
    `invoice_number` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`document_id`),
    CONSTRAINT `fk_document_legal_authority_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_line
CREATE TABLE IF NOT EXISTS `document_line` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
    `document_id` int(10) unsigned NOT NULL,
    `product_id` mediumint(8) unsigned NOT NULL,
    `line` smallint(5) unsigned NOT NULL,
    `product_description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`product_description`)),
    `product_description_long` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`product_description_long`)),
    `quantity` decimal(13,4) NOT NULL DEFAULT 0.0000,
    `unit_price_net` decimal(8,4) NOT NULL DEFAULT 0.0000,
    `unit_price_gross` decimal(8,4) NOT NULL DEFAULT 0.0000,
    `net_amount` decimal(13,4) NOT NULL DEFAULT 0.0000,
    `tax_amount` decimal(13,4) NOT NULL,
    `gross_amount` decimal(13,4) NOT NULL,
    `discount_amount` decimal(13,4) NOT NULL,
    `total_amount` decimal(13,4) NOT NULL DEFAULT 0.0000,
    `cost_price_net` decimal(8,4) DEFAULT 0.0000,
    `cost_price_gross` decimal(8,4) DEFAULT 0.0000,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `fk_document_detail_product1_idx` (`product_id`),
    KEY `fk_document_line_document1_idx` (`document_id`),
    CONSTRAINT `fk_document_detail_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_line_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_line_fuel
CREATE TABLE IF NOT EXISTS `document_line_fuel` (
    `document_line_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `pump` tinyint(3) unsigned NOT NULL,
    `nozzle` tinyint(4) NOT NULL,
    `start_totalizer` decimal(13,4) DEFAULT NULL,
    `end_totalizer` decimal(13,4) DEFAULT NULL,
    `start_stock` decimal(13,4) DEFAULT NULL,
    `end_stock` decimal(13,4) DEFAULT NULL,
    `transaction_id` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`document_line_id`),
    CONSTRAINT `fk_document_detail_fuel_document_detail1` FOREIGN KEY (`document_line_id`) REFERENCES `document_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_line_ref
CREATE TABLE IF NOT EXISTS `document_line_ref` (
    `document_line_id` int(10) unsigned NOT NULL,
    `ref_document_line_id` int(10) unsigned NOT NULL,
    PRIMARY KEY (`document_line_id`),
    KEY `fk_document_line_ref_document_line2_idx` (`ref_document_line_id`),
    CONSTRAINT `fk_document_detail_ref_document_detail1` FOREIGN KEY (`document_line_id`) REFERENCES `document_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_detail_ref_document_detail2` FOREIGN KEY (`ref_document_line_id`) REFERENCES `document_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_line_supplied_diff
CREATE TABLE IF NOT EXISTS `document_line_supplied_diff` (
    `document_line_id` int(10) unsigned NOT NULL,
    `quantity` decimal(13,4) NOT NULL,
    `unit_price_net` decimal(8,4) DEFAULT NULL,
    `unit_price_gross` decimal(8,4) DEFAULT NULL,
    `total_net_amount` decimal(13,4) DEFAULT NULL,
    `total_gross_amount` decimal(13,4) NOT NULL,
    PRIMARY KEY (`document_line_id`),
    KEY `fk_document_detail_supplied_diff_document_detail1_idx` (`document_line_id`),
    CONSTRAINT `fk_document_detail_supplied_diff_document_detail1` FOREIGN KEY (`document_line_id`) REFERENCES `document_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_party
CREATE TABLE IF NOT EXISTS `document_party` (
    `document_id` int(10) unsigned NOT NULL,
    `party_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `vatin` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `salutation` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `street` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `postal_zip_code` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `city` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `iso_country_code` char(3) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`document_id`),
    KEY `fk_document_partner_partner1_idx` (`party_code`),
    CONSTRAINT `fk_document_partner_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_payment
CREATE TABLE IF NOT EXISTS `document_payment` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `document_id` int(10) unsigned NOT NULL,
    `payment_mode_id` smallint(5) unsigned NOT NULL,
    `payment_movement_id` tinyint(3) unsigned NOT NULL,
    `amount` decimal(13,4) NOT NULL,
    `receipt_data` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_document_payment_payment1_idx` (`payment_mode_id`),
    KEY `fk_document_payment_document1_idx` (`document_id`),
    KEY `fk_document_payment_payment_movement1_idx` (`payment_movement_id`),
    CONSTRAINT `fk_document_payment_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_payment_payment1` FOREIGN KEY (`payment_mode_id`) REFERENCES `payment_mode` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_payment_payment_movement1` FOREIGN KEY (`payment_movement_id`) REFERENCES `payment_movement` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=691 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_payment_card
CREATE TABLE IF NOT EXISTS `document_payment_card` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `document_payment_id` int(10) unsigned NOT NULL,
    `card_system_id` tinyint(3) unsigned NOT NULL,
    `pan` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
    `track2` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `customer_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `card_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `label` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_document_payment_card_card_system1_idx` (`card_system_id`),
    KEY `fk_document_payment_card_document_payment1` (`document_payment_id`),
    CONSTRAINT `fk_document_payment_card_card_system1` FOREIGN KEY (`card_system_id`) REFERENCES `card_system` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_payment_card_document_payment1` FOREIGN KEY (`document_payment_id`) REFERENCES `document_payment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_prompt
CREATE TABLE IF NOT EXISTS `document_prompt` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `document_id` int(10) unsigned NOT NULL,
    `prompt_id` tinyint(3) unsigned NOT NULL,
    `input_data` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_document_prompt_prompt1_idx` (`prompt_id`),
    KEY `fk_document_prompt_document1_idx` (`document_id`),
    CONSTRAINT `fk_document_prompt_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_prompt_prompt1` FOREIGN KEY (`prompt_id`) REFERENCES `prompt` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_receipt
CREATE TABLE IF NOT EXISTS `document_receipt` (
    `document_id` int(10) unsigned NOT NULL,
    `reprint_number` tinyint(4) NOT NULL,
    `receipt` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`document_id`),
    CONSTRAINT `fk_document_receipt_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_serie
CREATE TABLE IF NOT EXISTS `document_serie` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL,
    `document_type` tinyint(3) unsigned NOT NULL,
    `site_id` smallint(5) unsigned NOT NULL,
    `site_device_pos_id` smallint(5) unsigned NOT NULL,
    `last_document_number` mediumint(8) unsigned NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `document_serie_uq` (`code`),
    KEY `fk_document_serie_site1_idx` (`site_id`),
    KEY `fk_document_serie_site_device_pos1_idx` (`site_device_pos_id`),
    KEY `fk_document_serie_document_type1_idx` (`document_type`),
    CONSTRAINT `fk_document_serie_document_type1` FOREIGN KEY (`document_type`) REFERENCES `document_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_serie_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_serie_site_device_pos` FOREIGN KEY (`site_device_pos_id`) REFERENCES `site_device_pos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_tax
CREATE TABLE IF NOT EXISTS `document_tax` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `document_id` int(10) unsigned NOT NULL,
    `document_line_id` int(10) unsigned NOT NULL,
    `line` smallint(5) unsigned NOT NULL,
    `tax_item_id` smallint(5) unsigned NOT NULL,
    `is_fixed_rate` tinyint(1) NOT NULL,
    `rate` decimal(8,4) NOT NULL,
    `amount` decimal(13,4) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_document_tax_document1_idx` (`document_id`),
    KEY `fk_document_tax_tax_item1` (`tax_item_id`),
    KEY `fk_document_tax_document_detail1` (`document_line_id`),
    CONSTRAINT `fk_document_tax_document1` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_tax_document_detail1` FOREIGN KEY (`document_line_id`) REFERENCES `document_line` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_document_tax_tax_item1` FOREIGN KEY (`tax_item_id`) REFERENCES `tax_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.document_type
CREATE TABLE IF NOT EXISTS `document_type` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` smallint(5) unsigned NOT NULL,
    `prefix` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(4) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `document_type_uq` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.grade
CREATE TABLE IF NOT EXISTS `grade` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(15) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `color` varchar(45) DEFAULT NULL,
    `unit_price_net` decimal(8,4) NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.grade_product
CREATE TABLE IF NOT EXISTS `grade_product` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `grade_id` smallint(5) unsigned NOT NULL,
    `product_id` mediumint(8) unsigned NOT NULL,
    `product_percentage` decimal(5,2) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_grade_product_grade1_idx` (`grade_id`),
    KEY `fk_grade_product_product1_idx` (`product_id`),
    CONSTRAINT `fk_grade_product_grade1` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_grade_product_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable
CREATE TABLE IF NOT EXISTS `notifiable` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(45) NOT NULL,
    `entity_code` varchar(45) NOT NULL,
    `entity_rank_order` tinyint(4) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_element
CREATE TABLE IF NOT EXISTS `notifiable_element` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `notifiable_id` smallint(5) unsigned NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `name` varchar(45) NOT NULL,
    `locale_code` varchar(45) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_IDX` (`name`,`notifiable_id`),
    KEY `fk_notifiable_element_notifiable1_idx` (`notifiable_id`),
    CONSTRAINT `fk_notifiable_element_notifiable1` FOREIGN KEY (`notifiable_id`) REFERENCES `notifiable` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_element_contact
CREATE TABLE IF NOT EXISTS `notifiable_element_contact` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `notifiable_element_id` smallint(5) unsigned NOT NULL,
    `notifiable_method_id` tinyint(3) unsigned NOT NULL,
    `alarm_severity_code` varchar(45) DEFAULT NULL,
    `contact` varchar(45) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNIQUE_IDX` (`contact`,`notifiable_element_id`),
    KEY `fk_notifiable_element_data_notifiable_method1_idx` (`notifiable_method_id`),
    KEY `fk_notifiable_element_data_notifiable_element1_idx` (`notifiable_element_id`),
    CONSTRAINT `fk_notifiable_element_data_notifiable_element1` FOREIGN KEY (`notifiable_element_id`) REFERENCES `notifiable_element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_notifiable_element_data_notifiable_method1` FOREIGN KEY (`notifiable_method_id`) REFERENCES `notifiable_method` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_entity
CREATE TABLE IF NOT EXISTS `notifiable_entity` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `notifiable_code` varchar(45) NOT NULL,
    `entity_code` varchar(45) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `notifiable_code_UNIQUE` (`notifiable_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_method
CREATE TABLE IF NOT EXISTS `notifiable_method` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_site
CREATE TABLE IF NOT EXISTS `notifiable_site` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `notifiable_code` varchar(45) NOT NULL,
    `site_code` varchar(45) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `notifiable_site_UNIQUE` (`notifiable_code`,`site_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.notifiable_site_group_item
CREATE TABLE IF NOT EXISTS `notifiable_site_group_item` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `notifiable_code` varchar(45) NOT NULL,
    `site_group_code` varchar(45) NOT NULL,
    `site_group_item_code` varchar(45) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `notifiable_site_group_item_UNIQUE` (`notifiable_code`,`site_group_code`,`site_group_item_code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.payment
CREATE TABLE IF NOT EXISTS `payment` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` smallint(5) unsigned NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.payment_mode
CREATE TABLE IF NOT EXISTS `payment_mode` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `document_type_id` tinyint(3) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `payment_uq` (`code`),
    KEY `fk_payment_mode_document_type1_idx` (`document_type_id`),
    CONSTRAINT `fk_payment_mode_document_type1` FOREIGN KEY (`document_type_id`) REFERENCES `document_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.payment_movement
CREATE TABLE IF NOT EXISTS `payment_movement` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `payment_movement_uq` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.product
CREATE TABLE IF NOT EXISTS `product` (
    `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `product_group_id` smallint(5) unsigned DEFAULT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `short_description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `is_fuel` tinyint(1) NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `created_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `updated_by` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `reference_unit_price` decimal(10,4) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`),
    KEY `fk_product_group` (`product_group_id`),
    CONSTRAINT `fk_product_group` FOREIGN KEY (`product_group_id`) REFERENCES `product_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.product_group
CREATE TABLE IF NOT EXISTS `product_group` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.prompt
CREATE TABLE IF NOT EXISTS `prompt` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.queue_message
CREATE TABLE IF NOT EXISTS `queue_message` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `queue_topic_id` smallint(5) unsigned NOT NULL,
    `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `processed` tinyint(1) NOT NULL,
    `message_updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `fk_queue_topic_idx` (`queue_topic_id`),
    CONSTRAINT `fk_queue_topic` FOREIGN KEY (`queue_topic_id`) REFERENCES `queue_topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table site.queue_topic
CREATE TABLE IF NOT EXISTS `queue_topic` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `topic` varchar(255) NOT NULL,
    `message_updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table site.region
CREATE TABLE IF NOT EXISTS `region` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site
CREATE TABLE IF NOT EXISTS `site` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `region_id` smallint(5) unsigned NOT NULL,
    `code` varchar(45) NOT NULL,
    `entity_code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    `latitude` varchar(45) DEFAULT NULL,
    `longitude` varchar(45) DEFAULT NULL,
    `site_profile_id` tinyint(3) unsigned NOT NULL DEFAULT 1,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_UNIQUE` (`code`),
    KEY `fk_site_region1_idx` (`region_id`),
    KEY `fk_site_site_profile1_idx` (`site_profile_id`),
    CONSTRAINT `fk_site_region1` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `site_ibfk_1` FOREIGN KEY (`site_profile_id`) REFERENCES `site_profile` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device
CREATE TABLE IF NOT EXISTS `site_device` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_id` smallint(5) unsigned NOT NULL,
    `site_device_type_id` tinyint(3) unsigned NOT NULL,
    `site_device_subtype_id` tinyint(3) unsigned DEFAULT NULL,
    `code` varchar(36) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `additional_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
    `is_enabled` tinyint(1) NOT NULL DEFAULT 1,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_code` (`code`),
    KEY `fk_site_device_site1_idx` (`site_id`),
    KEY `fk_site_device_site_device_type1_idx` (`site_device_type_id`),
    KEY `fk_site_device_site_device_subtype1` (`site_device_subtype_id`),
    CONSTRAINT `fk_site_device_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_device_site_device_subtype1` FOREIGN KEY (`site_device_subtype_id`) REFERENCES `site_device_subtype` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_device_site_device_type1` FOREIGN KEY (`site_device_type_id`) REFERENCES `site_device_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_dispenser
CREATE TABLE IF NOT EXISTS `site_device_dispenser` (
    `id` smallint(5) unsigned NOT NULL,
    `dispenser_number` tinyint(3) unsigned NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `site_device_id_UNIQUE` (`id`) USING BTREE,
    KEY `fk_site_device_dispenser` (`id`) USING BTREE,
    CONSTRAINT `site_device_dispenser_fk` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='conjunto de fuelpoints';

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_fuel_point
CREATE TABLE IF NOT EXISTS `site_device_fuel_point` (
    `id` smallint(5) unsigned NOT NULL,
    `site_device_dispenser_id` smallint(5) unsigned NOT NULL,
    `pump_number` tinyint(3) unsigned NOT NULL,
    `total_nozzles` tinyint(3) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `id` (`id`),
    KEY `fk_site_device_fuel_point_id` (`id`) USING BTREE,
    KEY `fk_site_device_dispenser` (`site_device_dispenser_id`),
    CONSTRAINT `fk_site_device_dispenser` FOREIGN KEY (`site_device_dispenser_id`) REFERENCES `site_device_dispenser` (`id`),
    CONSTRAINT `fk_site_device_fuel_point` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_pos
CREATE TABLE IF NOT EXISTS `site_device_pos` (
    `id` smallint(5) unsigned NOT NULL,
    `number` smallint(6) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `site_device_id` (`id`),
    KEY `fk_site_device_pos_site_device1_idx` (`id`),
    CONSTRAINT `fk_site_device_pos_site_device1` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_subtype
CREATE TABLE IF NOT EXISTS `site_device_subtype` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `device_type_id` tinyint(3) unsigned NOT NULL,
    `code` varchar(20) CHARACTER SET utf8 NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `code` (`code`),
    KEY `device_type_id` (`device_type_id`),
    CONSTRAINT `fk_site_device` FOREIGN KEY (`device_type_id`) REFERENCES `site_device_type` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_type
CREATE TABLE IF NOT EXISTS `site_device_type` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(20) CHARACTER SET utf8 NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_device_warehouse
CREATE TABLE IF NOT EXISTS `site_device_warehouse` (
    `id` smallint(5) unsigned NOT NULL,
    `product_id` mediumint(8) unsigned DEFAULT NULL,
    `fcc_warehouse_code` varchar(36) NOT NULL DEFAULT '1',
    `warehouse_type_id` tinyint(3) unsigned NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    UNIQUE KEY `site_device_id_UNIQUE` (`id`),
    KEY `fk_site_device_tank_site_device1_idx` (`id`),
    KEY `fk_site_device_tank_product1_idx` (`product_id`),
    KEY `site_device_warehouse_FK` (`warehouse_type_id`),
    CONSTRAINT `fk_site_device_tank_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_device_tank_site_device1` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `site_device_warehouse_FK` FOREIGN KEY (`warehouse_type_id`) REFERENCES `warehouse_type` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_grade
CREATE TABLE IF NOT EXISTS `site_grade` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `site_id` smallint(5) unsigned NOT NULL,
    `grade_id` smallint(5) unsigned NOT NULL,
    `fcc_grade_code` smallint(5) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_site_grade` (`site_id`,`fcc_grade_code`),
    KEY `fk_site_grade_grade1_idx` (`grade_id`),
    KEY `fk_site_grade_site1_idx` (`site_id`),
    CONSTRAINT `fk_site_grade_grade1` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_grade_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.


-- Dumping structure for table site.site_device_fuel_point_nozzle
CREATE TABLE IF NOT EXISTS `site_device_fuel_point_nozzle` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_device_id` smallint(5) unsigned NOT NULL,
    `nozzle_number` tinyint(3) unsigned NOT NULL,
    `site_grade_id` int(10) unsigned NOT NULL,
    `last_totalizer` decimal(11,2) DEFAULT NULL,
    `last_totalizer_on` datetime DEFAULT NULL,
    `site_device_warehouse_id` smallint(5) unsigned NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_site_device_pump_nozzle_grade1_idx` (`site_grade_id`),
    KEY `fk_site_device_pump_nozzle_site_device_pump1_idx` (`site_device_id`),
    KEY `fk_site_device_fuel_point_nozzle_site_device_warehouse1` (`site_device_warehouse_id`),
    CONSTRAINT `fk_site_device_fuel_point_nozzle_site_grade1` FOREIGN KEY (`site_grade_id`) REFERENCES `site_grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_device_fuel_point_nozzle_site_device_fuel_point1` FOREIGN KEY (`site_device_id`) REFERENCES `site_device_fuel_point` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_device_fuel_point_nozzle_site_device_warehouse1` FOREIGN KEY (`site_device_warehouse_id`) REFERENCES `site_device_warehouse` (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_group
CREATE TABLE IF NOT EXISTS `site_group` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(45) NOT NULL,
    `entity_code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `entity_rank_order` tinyint(4) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `description_UNIQUE` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_group_item
CREATE TABLE IF NOT EXISTS `site_group_item` (
    `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    `parent_id` mediumint(8) unsigned DEFAULT NULL,
    `site_group_id` smallint(5) unsigned NOT NULL,
    `code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_leaf` tinyint(1) NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_UNIQUE` (`site_group_id`,`code`),
    KEY `fk_group_item_group1_idx` (`site_group_id`),
    KEY `fk_site_group_item_site_group_item1` (`parent_id`),
    CONSTRAINT `fk_site_group_item_site_group_item1` FOREIGN KEY (`parent_id`) REFERENCES `site_group_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `site_group_item_ibfk_1` FOREIGN KEY (`site_group_id`) REFERENCES `site_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_group_item_site
CREATE TABLE IF NOT EXISTS `site_group_item_site` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_id` smallint(5) unsigned NOT NULL,
    `site_group_item_id` mediumint(8) unsigned NOT NULL,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_UNIQUE` (`site_id`,`site_group_item_id`),
    KEY `fk_site_group_item_group_item1` (`site_group_item_id`),
    CONSTRAINT `fk_site_group_item_group_item1` FOREIGN KEY (`site_group_item_id`) REFERENCES `site_group_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_group_item_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.site_product
CREATE TABLE IF NOT EXISTS `site_product` (
    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    `site_id` smallint(5) unsigned NOT NULL,
    `product_id` mediumint(5) unsigned NOT NULL,
    `fcc_product_code` varchar(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_site_product_number` (`site_id`,`product_id`),
    KEY `fk_site_pos_product_product_pos1_idx` (`product_id`),
    KEY `fk_site_pos_product_site1_idx` (`site_id`),
    CONSTRAINT `fk_site_pos_product_product_pos1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_site_pos_product_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Data exporting was unselected.

-- Dumping structure for table site.site_profile
CREATE TABLE IF NOT EXISTS `site_profile` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(45) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_item
CREATE TABLE IF NOT EXISTS `tax_item` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `tax_type_id` smallint(5) unsigned NOT NULL,
    `code` varchar(10) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_fixed_rate` tinyint(1) NOT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_tax_item_tax_group1_idx` (`tax_type_id`),
    CONSTRAINT `fk_tax_item_tax_group1` FOREIGN KEY (`tax_type_id`) REFERENCES `tax_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_item_region
CREATE TABLE IF NOT EXISTS `tax_item_region` (
    `tax_item_id` smallint(5) unsigned NOT NULL,
    `region_id` smallint(5) unsigned NOT NULL,
    `rate` decimal(8,4) NOT NULL,
    PRIMARY KEY (`tax_item_id`,`region_id`),
    KEY `fk_tax_tax_item1_idx` (`tax_item_id`),
    CONSTRAINT `fk_tax_tax_item1` FOREIGN KEY (`tax_item_id`) REFERENCES `tax_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_rule
CREATE TABLE IF NOT EXISTS `tax_rule` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_rule_calculation
CREATE TABLE IF NOT EXISTS `tax_rule_calculation` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_rule_item
CREATE TABLE IF NOT EXISTS `tax_rule_item` (
    `tax_rule_id` smallint(5) unsigned NOT NULL,
    `tax_item_id` smallint(5) unsigned NOT NULL,
    `apply_order` tinyint(3) unsigned NOT NULL,
    `tax_rule_calculation_id` tinyint(3) unsigned NOT NULL,
    PRIMARY KEY (`tax_rule_id`,`tax_item_id`),
    KEY `fk_tax_rule_item_tax_item1_idx` (`tax_item_id`),
    KEY `fk_tax_rule_item_tax_rule_calculation1_idx` (`tax_rule_calculation_id`),
    CONSTRAINT `fk_tax_rule_item_tax_item1` FOREIGN KEY (`tax_item_id`) REFERENCES `tax_item` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_tax_rule_item_tax_rule1` FOREIGN KEY (`tax_rule_id`) REFERENCES `tax_rule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `fk_tax_rule_item_tax_rule_calculation1` FOREIGN KEY (`tax_rule_calculation_id`) REFERENCES `tax_rule_calculation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site.tax_type
CREATE TABLE IF NOT EXISTS `tax_type` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(10) NOT NULL,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
    `is_enabled` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for function site.translate
DELIMITER //
CREATE FUNCTION `translate`(field JSON, lang CHAR(10))
    RETURNS char(255)
BEGIN
	DECLARE extract_lang CHAR(15);
	SET extract_lang = CONCAT('$."',lang,'"');
    #Returning en-en as default language
	RETURN COALESCE (JSON_UNQUOTE(JSON_EXTRACT(field,extract_lang)), JSON_UNQUOTE(JSON_EXTRACT(field,'$."en-en"')),'NA');
END//
DELIMITER ;

-- Dumping structure for table site.warehouse_type
CREATE TABLE IF NOT EXISTS `warehouse_type` (
    `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `code` varchar(25) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `warehouse_type_UN` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table site._flyway_site_history
CREATE TABLE IF NOT EXISTS `_flyway_site_history` (
    `installed_rank` int(11) NOT NULL,
    `version` varchar(50) DEFAULT NULL,
    `description` varchar(200) NOT NULL,
    `type` varchar(20) NOT NULL,
    `script` varchar(1000) NOT NULL,
    `checksum` int(11) DEFAULT NULL,
    `installed_by` varchar(100) NOT NULL,
    `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
    `execution_time` int(11) NOT NULL,
    `success` tinyint(1) NOT NULL,
    PRIMARY KEY (`installed_rank`),
    KEY `_flyway_site_history_s_idx` (`success`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for trigger site.tr_document_code
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tr_document_code` BEFORE INSERT ON `document` FOR EACH ROW
BEGIN
    IF NEW.code IS NULL
    THEN
        SET NEW.code = (SELECT UUID());
END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger site.tr_document_line_code
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tr_document_line_code` BEFORE INSERT ON `document_line` FOR EACH ROW
BEGIN
    IF NEW.code IS NULL
    THEN
        SET NEW.code = (SELECT UUID());
END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger site.tr_document_serie_code
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tr_document_serie_code` BEFORE INSERT ON `document_serie` FOR EACH ROW
BEGIN
    IF NEW.code IS NULL
    THEN
        SET NEW.code = (SELECT UUID());
END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
