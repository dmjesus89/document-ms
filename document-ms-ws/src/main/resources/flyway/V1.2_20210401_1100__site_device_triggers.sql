DELIMITER $$
$$
CREATE DEFINER=`root`@`localhost` TRIGGER tr_site_device_generate_code BEFORE INSERT ON site_device FOR EACH row
BEGIN
	IF NEW.code IS NULL
	THEN
		SET NEW.code = (SELECT UUID());
    END IF;
END
$$
DELIMITER ;
