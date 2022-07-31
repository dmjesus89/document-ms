ALTER TABLE service_mode MODIFY id smallint(5) unsigned NOT NULL;

ALTER TABLE site_device_price_sign MODIFY COLUMN id smallint(5) unsigned auto_increment NOT NULL;

ALTER TABLE grade_site_device_price_sign DROP FOREIGN KEY fk_site_price_device_sign_idx3;

ALTER TABLE grade_site_device_price_sign
  ADD CONSTRAINT `fk_site_price_device_sign_idx3` FOREIGN KEY (
  `site_device_price_sign_id`) REFERENCES `site_device_price_sign` (`id`) ON
  DELETE no action ON UPDATE no action;

ALTER TABLE site_device_price_sign
  ADD COLUMN IF NOT EXISTS site_device_fcc_id SMALLINT(5) UNSIGNED NULL;

ALTER TABLE site_device_tank_level_gauge
  ADD COLUMN IF NOT EXISTS  site_device_fcc_id SMALLINT(5) UNSIGNED NULL;

ALTER TABLE site_device_fuel_point
  ADD COLUMN IF NOT EXISTS  site_device_fcc_id SMALLINT(5) UNSIGNED NULL;

ALTER TABLE fuelling_mode
  DROP PRIMARY KEY,
  ADD COLUMN IF NOT EXISTS  id SMALLINT(5) UNSIGNED AUTO_INCREMENT NOT NULL,
  ADD CONSTRAINT fuelling_mode_PK PRIMARY KEY (id);

CREATE TABLE IF NOT EXISTS site_device_fcc
  (
     id smallint(5) unsigned NOT NULL,
     fuelling_mode_id SMALLINT(5) UNSIGNED  NOT NULL,
     service_mode_id  SMALLINT(5) UNSIGNED  NOT NULL,
     communication_method_data longtext null,
     communication_method_id tinyint(3) unsigned  null,
     PRIMARY KEY (id) USING BTREE
  )
engine=innodb;

CREATE TABLE IF NOT EXISTS site_device_fcc_pos
  (
     site_device_fcc_id SMALLINT(5) UNSIGNED NOT NULL,
     site_device_pos_id SMALLINT(5) UNSIGNED NOT NULL,
     PRIMARY KEY (site_device_fcc_id, site_device_pos_id)
  )
engine=innodb;

ALTER TABLE site_device_fcc
  ADD CONSTRAINT site_device_fcc_service_mode_fk FOREIGN KEY (
  service_mode_id) REFERENCES service_mode (id);

ALTER TABLE site_device_fcc
  ADD CONSTRAINT communication_method_site_device_fcc_FK FOREIGN KEY (
  communication_method_id) REFERENCES communication_method(id);

ALTER TABLE site_device_fcc
  ADD CONSTRAINT site_device_fcc_fuelling_mode_fk FOREIGN KEY (fuelling_mode_id)
  REFERENCES fuelling_mode (id);

ALTER TABLE site_device_fcc
  ADD CONSTRAINT site_device_fcc_fk FOREIGN KEY (id) REFERENCES site_device (id);

ALTER TABLE site_device_price_sign
  ADD CONSTRAINT site_device_fcc_price_fk FOREIGN KEY (site_device_fcc_id) REFERENCES
  site_device_fcc (id);

ALTER TABLE site_device_fuel_point
  ADD CONSTRAINT site_device_fcc_fuel_point_fk FOREIGN KEY (site_device_fcc_id) REFERENCES
  site_device_fcc (id);

ALTER TABLE site_device_tank_level_gauge
  ADD CONSTRAINT site_device_fcc_tank_level_fk FOREIGN KEY (site_device_fcc_id) REFERENCES
  site_device_fcc (id);

