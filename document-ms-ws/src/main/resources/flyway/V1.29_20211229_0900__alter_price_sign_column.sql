ALTER TABLE grade_site_device_price_sign ADD `grade_order` smallint(3) unsigned NULL;
UPDATE grade_site_device_price_sign SET grade_order = 0;