SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE service_mode MODIFY id smallint(5) unsigned NOT NULL auto_increment;
SET FOREIGN_KEY_CHECKS=1;

ALTER TABLE site_device_tank_level_gauge DROP CONSTRAINT site_device_fcc_tank_level_fk;