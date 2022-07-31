ALTER TABLE site_device_warehouse ADD COLUMN IF NOT EXISTS total_capacity DECIMAL(10,2) NULL DEFAULT 0;
ALTER TABLE site_device_warehouse ADD COLUMN IF NOT EXISTS current_volume DECIMAL(10,2) NULL DEFAULT 0;
ALTER TABLE site_device_warehouse ADD COLUMN IF NOT EXISTS default_warehouse BIT NULL  DEFAULT 0;

ALTER TABLE service_mode ADD COLUMN IF NOT EXISTS created_on datetime NOT NULL DEFAULT current_timestamp();
ALTER TABLE service_mode ADD COLUMN IF NOT EXISTS updated_on datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp();