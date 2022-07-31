ALTER TABLE site_device_pos ADD communication_method_id tinyint(3) unsigned NULL;
ALTER TABLE site_device_pos ADD communication_method_data LONGTEXT NULL;

CREATE TABLE IF NOT EXISTS `site_device_pos_site_device_fuel_point` (
        `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
        site_device_pos_id smallint(5) unsigned not null,
        fuel_points_id smallint(5) unsigned not null,
        UNIQUE KEY `site_device_pos_fuel_id` (`id`),
        PRIMARY KEY (`id`),
        UNIQUE KEY `site_device_pos_fuel_UNIQUE` (`site_device_pos_id`,`fuel_points_id`) USING BTREE,
        CONSTRAINT site_device_pos_id_FK FOREIGN KEY (site_device_pos_id) REFERENCES site_device_pos(id),
        CONSTRAINT fuel_points_id_FK FOREIGN KEY (fuel_points_id) REFERENCES site_device_fuel_point(id)
    ) engine=InnoDB;


CREATE TABLE IF NOT EXISTS `virtual_pos` (
        `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
        `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
         `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
         `has_printer` tinyint(1) NOT NULL,
        site_device_pos_id smallint(5) unsigned not null,
        UNIQUE KEY `virtual_pos_code` (`code`),
        PRIMARY KEY (`id`),
        CONSTRAINT virtual_pos_id_FK FOREIGN KEY (site_device_pos_id) REFERENCES site_device_pos(id)

    ) engine=InnoDB;


CREATE TABLE IF NOT EXISTS `virtual_pos_site_device_fuel_point` (
        `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
        `virtual_pos_id` smallint(5) unsigned not null,
        fuel_points_id smallint(5) unsigned not null,
        UNIQUE KEY `virtual_pos_fuel_point2_id` (`id`),
        PRIMARY KEY (`id`),
        UNIQUE KEY `virtual_pos_fuel_point2_UNIQUE` (`virtual_pos_id`,`fuel_points_id`) USING BTREE,
        CONSTRAINT virtual_pos_id2_FK FOREIGN KEY (virtual_pos_id) REFERENCES site.virtual_pos(id) ON DELETE CASCADE ON UPDATE RESTRICT,
        CONSTRAINT fuel_points_2_id_FK FOREIGN KEY (fuel_points_id) REFERENCES site_device_fuel_point(id)
    ) engine=InnoDB;