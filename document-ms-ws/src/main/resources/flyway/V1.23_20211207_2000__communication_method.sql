CREATE TABLE IF NOT EXISTS `communication_method` (
                                                     `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
                                                     `code` varchar(20) CHARACTER SET utf8 NOT NULL,
                                                     `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                                     `is_enabled` tinyint(1) NOT NULL,
                                                     PRIMARY KEY (`id`) USING BTREE,
                                                     UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB;


INSERT INTO `communication_method` (`code`, `description`, `is_enabled`) VALUES ('IP_PROTOCOL', '{"en-en": "IP Protocol", "pt-pt": "Protocolo IP"}', 1);
INSERT INTO `communication_method` (`code`, `description`, `is_enabled`) VALUES ('SERIAL_CONNECTION', '{"en-en": "Serial Connection", "pt-pt": "Série"}', 1);

ALTER TABLE site_device_fuel_point ADD communication_method_data LONGTEXT NULL;
ALTER TABLE site_device_fuel_point ADD communication_method_id tinyint(3) unsigned NULL;
ALTER TABLE site_device_fuel_point ADD CONSTRAINT site_device_fuel_point_FK FOREIGN KEY (communication_method_id) REFERENCES communication_method(id);
ALTER TABLE site_device_fuel_point MODIFY COLUMN site_device_dispenser_id smallint(5) unsigned NULL;
