CREATE TABLE `property_device_warehouse` (
                                 `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                 `property_id` smallint(5) unsigned NOT NULL,
                                 `site_device_warehouse_id` smallint(5) unsigned NOT NULL,
                                 `value` varchar(45) NOT NULL,
                                 `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                 `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `property_site_device_warehouse_uq` (`property_id`,`site_device_warehouse_id`),
                                 KEY `fk_property_device_warehouse_property1_idx` (`property_id`),
                                 KEY `fk_property_device_warehouse_site_device_warehouse1_idx` (`site_device_warehouse_id`),
                                 CONSTRAINT `fk_property_device_warehouse_site_device_warehouse1` FOREIGN KEY (`site_device_warehouse_id`) REFERENCES `site_device_warehouse` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                 CONSTRAINT `fk_property_device_warehouse_property1` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;


CREATE TABLE `category_element_device_warehouse` (
                                         `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
                                         `category_id` smallint(5) unsigned NOT NULL,
                                         `category_element_id` smallint(5) unsigned NOT NULL,
                                         `site_device_warehouse_id` smallint(5) unsigned NOT NULL,
                                         `created_on` datetime NOT NULL DEFAULT current_timestamp(),
                                         `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                         PRIMARY KEY (`id`),
                                         UNIQUE KEY `category_element_device_warehouse_uq` (`category_id`,`category_element_id`,`site_device_warehouse_id`),
                                         KEY `fk_category_element_device_warehouse_category_element1_idx` (`category_element_id`),
                                         KEY `fk_category_element_device_warehouse_category1_idx` (`category_id`),
                                         KEY `fk_category_element_device_warehouse_site_device_warehouse1_idx` (`site_device_warehouse_id`),
                                         CONSTRAINT `fk_category_element_device_warehouse_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                         CONSTRAINT `fk_category_element_device_warehouse_category_element1` FOREIGN KEY (`category_element_id`) REFERENCES `category_element` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
                                         CONSTRAINT `fk_category_element_device_warehouse_site_device_warehouse1` FOREIGN KEY (`site_device_warehouse_id`) REFERENCES `site_device_warehouse` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB;

INSERT INTO property_usage (code,description,is_enabled) VALUES('DEVICE_WAREHOUSE','{"en-en": "Warehouse", "pt-pt": "Armazem"}',1);


