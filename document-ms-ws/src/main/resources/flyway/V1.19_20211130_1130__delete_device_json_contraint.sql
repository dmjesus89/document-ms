ALTER TABLE site_device DROP CONSTRAINT IF EXISTS description;
ALTER TABLE site_device MODIFY COLUMN description varchar(100) NOT NULL;
