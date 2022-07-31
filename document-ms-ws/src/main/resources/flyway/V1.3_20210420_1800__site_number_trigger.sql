ALTER TABLE site ADD site_number varchar(45) NULL;

DROP TRIGGER IF EXISTS tr_site_generate_code;

DELIMITER $$
$$
CREATE DEFINER=`root`@`localhost` TRIGGER tr_site_generate_code BEFORE INSERT ON site FOR EACH row
BEGIN
	IF NEW.code IS NULL
	THEN
		SET NEW.code = (SELECT UUID());
END IF;
END
$$
DELIMITER ;
