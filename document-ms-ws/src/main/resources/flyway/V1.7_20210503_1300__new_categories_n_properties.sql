ALTER TABLE site DROP IF EXISTS updated_on;
ALTER TABLE site ADD `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp();


DROP TABLE IF EXISTS property_site;
DROP TABLE IF EXISTS property_usage_property;
DROP TABLE IF EXISTS property_usage;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS property_data_type;


DROP TABLE IF EXISTS category_usage_category;
DROP TABLE IF EXISTS category_usage;
DROP TABLE IF EXISTS category_element_site;
DROP TABLE IF EXISTS category_element;
DROP TABLE IF EXISTS category;

CREATE TABLE `property_data_type` (
                                      `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                                      `code` varchar(20) NOT NULL,
                                      `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                      `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                      `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                      `is_enabled` tinyint(1) NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property` (
                            `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                            `code` varchar(30) NOT NULL,
                            `property_data_type_id` tinyint(3) unsigned NOT NULL,
                            `additional_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`additional_data`)),
                            `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                            `is_mandatory` tinyint(1) NOT NULL,
                            `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                            `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            `is_enabled` tinyint(1) NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `category_uq` (`code`),
                            KEY `fk_property_property_data_type1_idx` (`property_data_type_id`),
                            CONSTRAINT `property_ibfk_1` FOREIGN KEY (`property_data_type_id`) REFERENCES `property_data_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property_usage` (
                                  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                                  `code` varchar(20) NOT NULL,
                                  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                  `is_enabled` tinyint(1) NOT NULL,
                                  `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                  `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `category_type_uq` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property_usage_property` (
                                           `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                           `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                           `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                           `property_usage_id` tinyint(3) unsigned NOT NULL,
                                           `property_id` smallint(5) unsigned NOT NULL,
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `property_usage_property_uq` (`property_usage_id`,`property_id`),
                                           KEY `fk_property_usage_property_property_usage1_idx` (`property_usage_id`),
                                           KEY `fk_property_usage_property_property1_idx` (`property_id`),
                                           CONSTRAINT `fk_property_usage_property_property1` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                           CONSTRAINT `fk_property_usage_property_property_usage1` FOREIGN KEY (`property_usage_id`) REFERENCES `property_usage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `property_site` (
                                 `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                 `property_id` smallint(5) unsigned NOT NULL,
                                 `site_id` smallint(5) unsigned NOT NULL,
                                 `value` varchar(45) NOT NULL,
                                 `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                 `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `property_site_uq` (`property_id`,`site_id`),
                                 KEY `fk_property_site_property1_idx` (`property_id`),
                                 KEY `fk_property_site1_idx` (`site_id`),
                                 CONSTRAINT `fk_property_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                 CONSTRAINT `fk_property_site_property1` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;

CREATE TABLE `category` (
                            `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                            `code` varchar(20) NOT NULL,
                            `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                            `is_mandatory` tinyint(1) NOT NULL,
                            `is_enabled` tinyint(1) NOT NULL,
                            `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                            `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `category_uq` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `category_element` (
                                    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                    `parent_id` smallint(5) unsigned DEFAULT NULL,
                                    `category_id` smallint(5) unsigned NOT NULL,
                                    `code` varchar(20) NOT NULL,
                                    `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                    `is_item_allowed` tinyint(1) NOT NULL,
                                    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `category_element_uq` (`category_id`,`code`),
                                    KEY `fk_category_element_category1_idx` (`category_id`),
                                    CONSTRAINT `fk_category_element_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `category_usage` (
                                  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                                  `code` varchar(20) NOT NULL,
                                  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`description`)),
                                  `is_enabled` tinyint(1) NOT NULL,
                                  `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                  `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `category_type_uq` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category_usage_category` (
                                           `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                           `category_usage_id` tinyint(3) unsigned NOT NULL,
                                           `category_id` smallint(5) unsigned NOT NULL,
                                           `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                           `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `category_usage_category_uq` (`category_usage_id`,`category_id`),
                                           KEY `fk_category_usage_category_category_usage1_idx` (`category_usage_id`),
                                           KEY `fk_category_usage_category_category1_idx` (`category_id`),
                                           CONSTRAINT `fk_category_usage_category_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                           CONSTRAINT `fk_category_usage_category_category_usage1` FOREIGN KEY (`category_usage_id`) REFERENCES `category_usage` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `category_element_site` (
                                         `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                         `category_id` smallint(5) unsigned NOT NULL,
                                         `category_element_id` smallint(5) unsigned NOT NULL,
                                         `site_id` smallint(5) unsigned NOT NULL,
                                         `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                         `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `category_element_site_uq` (`category_id`,`category_element_id`,`site_id`),
                                         KEY `fk_category_element_site_category_element1_idx` (`category_element_id`),
                                         KEY `fk_category_element_site_category1_idx` (`category_id`),
                                         KEY `fk_category_element_site_site1_idx` (`site_id`),
                                         CONSTRAINT `fk_category_element_site_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                         CONSTRAINT `fk_category_element_site_category_element1` FOREIGN KEY (`category_element_id`) REFERENCES `category_element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                         CONSTRAINT `fk_category_element_site_site1` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO category_usage (code,description,is_enabled) VALUES
('VEHICLE','{"en-en": "Vehicle", "pt-pt": "Viatura"}',1)
                                                              ,('CARD','{"en-en": "Card", "pt-pt": "Cartão"}',1)
                                                              ,('CUSTOMER','{"en-en": "Customer", "pt-pt": "Cliente"}',1)
                                                              ,('EMPLOYEE','{"en-en": "Employee", "pt-pt": "Funcionário"}',1)
                                                              ,('SUPPLIER','{"en-en": "Supplier", "pt-pt": "Fornecedor"}',1)
                                                              ,('VEHICLE_MODEL','{"en-en": "Vehicle Model", "pt-pt": "Modelo Viatura"}',1)
                                                              ,('SITE','{"en-en": "Site", "pt-pt": "Posto"}',1)
;

INSERT INTO property_usage (code,description,is_enabled) VALUES
('VEHICLE','{"en-en": "Vehicle", "pt-pt": "Viatura"}',1)
                                                              ,('CARD','{"en-en": "Card", "pt-pt": "Cartão"}',1)
                                                              ,('CUSTOMER','{"en-en": "Customer", "pt-pt": "Cliente"}',1)
                                                              ,('EMPLOYEE','{"en-en": "Employee", "pt-pt": "Funcionário"}',1)
                                                              ,('SUPPLIER','{"en-en": "Supplier", "pt-pt": "Fornecedor"}',1)
                                                              ,('VEHICLE_MODEL','{"en-en": "Vehicle Model", "pt-pt": "Modelo Viatura"}',1)
                                                              ,('SITE','{"en-en": "Site", "pt-pt": "Posto"}',1)
;

INSERT INTO property_data_type (code,description,is_enabled) VALUES
('NUMERIC','{"en-en": "Numeric", "pt-pt": "Numérico"}',1)
                                                                  ,('TEXT','{"en-en": "String", "pt-pt": "Texto"}',1)
                                                                  ,('DATE','{"en-en": "Date", "pt-pt": "Data"}',1)
                                                                  ,('BOOLEAN','{"en-en": "Boolean", "pt-pt": "Booleano"}',1)
;

