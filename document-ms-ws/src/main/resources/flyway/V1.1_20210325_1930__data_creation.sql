
-- Dumping data for table data1.card_system: ~6 rows (approximately)
/*!40000 ALTER TABLE `card_system` DISABLE KEYS */;
INSERT IGNORE INTO `card_system` (`id`, `code`, `description`, `is_enabled`, `created_on`, `updated_on`) VALUES
	(1, 'VEHICLE_OF_2_SYSTEM', '{"pt-pt":"(A) Cartão de Viatura","null":"(A) Vehicle Card","en-en":"(A) Vehicle Card"}', 1, '2020-05-28 10:43:17', '2020-06-12 19:29:47'),
	(2, 'DRIVER_OF_2_SYSTEM', '{"pt-pt":"(A) Cartão de Condutor","null":"(B) Driver Card","en-en":"(B) Driver Card"}', 1, '2020-05-28 10:43:17', '2020-07-13 18:14:48'),
	(3, 'VEHICLE_CARD', '{"pt-pt":"(C) Cartão de Viatura","null":"(C) Vehicle Card","en-en":"(C) Vehicle Card"}', 1, '2020-05-28 10:43:17', '2020-06-09 14:56:28'),
	(4, 'DRIVER_CARD', '{"pt-pt":"(C) Cartão de Condutor","null":"(C) Driver Card","en-en":"(C) Driver Card"}', 1, '2020-05-28 10:43:17', '2020-10-20 11:42:48'),
	(5, 'FLEET', '{"en-en": "Fleet", "pt-pt": "Fleet"}', 1, '2020-05-28 10:43:17', '2020-05-28 10:43:17'),
	(6, 'BANK_CARD', '{"en-en": "Bank Card", "pt-pt": "Cartão Bancário"}', 1, '2020-05-28 10:43:17', '2020-05-28 10:43:17');
/*!40000 ALTER TABLE `card_system` ENABLE KEYS */;

-- -- Dumping data for table data1.document_serie: ~1 rows (approximately)
-- /*!40000 ALTER TABLE `document_serie` DISABLE KEYS */;
-- INSERT IGNORE INTO `document_serie` (`id`, `code`, `document_type`, `site_id`, `site_device_pos_id`, `last_document_number`, `is_enabled`, `created_on`, `updated_on`) VALUES
-- 	(8, 'A', 1, 500, 501, 95, 1, '2020-11-17 12:43:47', '2021-03-02 19:34:41');
-- /*!40000 ALTER TABLE `document_serie` ENABLE KEYS */;

-- Dumping data for table data1.document_type: ~1 rows (approximately)
/*!40000 ALTER TABLE `document_type` DISABLE KEYS */;
INSERT IGNORE INTO `document_type` (`id`, `code`, `prefix`, `description`, `is_enabled`, `created_on`, `updated_on`) VALUES
	(1, 1, 'GT', '{"pt-pt":"Guia de transporte","null":"Transport Document","en-en":"Transport Document"}', 1, '2020-05-28 10:43:17', '2020-06-01 14:56:03');
/*!40000 ALTER TABLE `document_type` ENABLE KEYS */;

-- Dumping data for table data1.payment_mode: ~2 rows (approximately)
/*!40000 ALTER TABLE `payment_mode` DISABLE KEYS */;
INSERT IGNORE INTO `payment_mode` (`id`, `code`, `description`, `is_enabled`, `created_on`, `updated_on`, `document_type_id`) VALUES
	(1, 'FLEET_CREDIT', '{"pt-pt":"Crédito Frota","null":"Fleet Credit","en-en":"Fleet Credit"}', 1, '2020-05-28 10:43:17', '2020-06-01 14:56:03', 1),
	(2, 'LOCAL_CREDIT', '{"en-en": "Local Credit", "pt-pt": "Crédito Local"}', 1, '2020-05-28 10:43:17', '2020-05-28 10:43:17', NULL),
	(3, 'MONEY', '{"pt-pt":"Dinheiro","null":"Money","en-en":"Money"}', 1, '2020-05-28 10:43:17', '2020-06-01 14:56:03', 1);
/*!40000 ALTER TABLE `payment_mode` ENABLE KEYS */;

-- Dumping data for table data1.payment_movement: ~2 rows (approximately)
/*!40000 ALTER TABLE `payment_movement` DISABLE KEYS */;
INSERT IGNORE INTO `payment_movement` (`id`, `code`, `description`, `created_on`, `updated_on`) VALUES
	(1, 'IN', '{"pt-pt":"Entrada","null":"In","en-en":"In"}', '2020-05-28 10:43:17', '2020-06-01 14:56:03'),
	(2, 'OUT', '{"en-en": "Out", "pt-pt": "Saída"}', '2020-05-28 10:43:17', '2020-05-28 10:43:17');
/*!40000 ALTER TABLE `payment_movement` ENABLE KEYS */;

-- Dumping data for table data1.region: ~3 rows (approximately)
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT IGNORE INTO `region` (`id`, `code`, `description`, `is_enabled`) VALUES
	(1, 'CONTINENTE', '{"en-en": "Mainland", "pt-pt": "Continente"}', 1),
	(2, 'AÇORES', '{"en-en": "Azores", "pt-pt": "Açores"}', 0),
	(3, 'MADEIRA', '{"en-en": "Madeira", "pt-pt": "Madeira"}', 0);
/*!40000 ALTER TABLE `region` ENABLE KEYS */;

-- Dumping data for table pos.device_type: ~5 rows (approximately)
/*!40000 ALTER TABLE `site_device_type` DISABLE KEYS */;
INSERT IGNORE INTO `site_device_type` (`id`, `code`, `description`, `is_enabled`) VALUES
	(1, 'FCC', '{"en-en": "FCC", "pt-pt": "FCC"}', 1),
	(2, 'FUEL_POINT', '{"en-en": "FuelPoint", "pt-pt": "Bomba"}', 1),
	(3, 'TLG', '{"en-en": "TLG", "pt-pt": "Sondas"}', 1),
	(4, 'WAREHOUSE', '{"en-en": "Warehouse", "pt-pt": "Armazem"}', 1),
	(5, 'POS', '{"en-en": "POS", "pt-pt": "POS"}', 1),
    (6, 'DISPENSER', '{"en-en": "Dispenser", "pt-pt": "Dispenser"}', 1);
/*!40000 ALTER TABLE `site_device_type` ENABLE KEYS */;

-- Dumping data for table pos.device_subtype: ~3 rows (approximately)
/*!40000 ALTER TABLE `site_device_subtype` DISABLE KEYS */;
INSERT IGNORE INTO `site_device_subtype` (`id`, `code`, `device_type_id`, `description`, `is_enabled`) VALUES
	(1, 'IPT', 5, '{"en-en": "IPT", "pt-pt": "IPT"}', 1),
	(2, 'OPT', 5, '{"en-en": "OPT", "pt-pt": "OPT"}', 1),
	(3, 'OPT_FLEET', 5, '{"en-en": "OPT Fleet", "pt-pt": "OPT Frota"}', 1);
/*!40000 ALTER TABLE `site_device_subtype` ENABLE KEYS */;

-- Dumping data for table data1.site_profile: ~2 rows (approximately)
/*!40000 ALTER TABLE `site_profile` DISABLE KEYS */;
INSERT IGNORE INTO `site_profile` (`id`, `code`, `description`, `is_enabled`) VALUES
	(1, 'Attended', '{"en-en": "Attended", "es-es": "Atendido", "pt-pt": "Atendido"}', 1),
	(2, 'Unattended', '{"en-en": "Unattended", "es-es": "Continente", "pt-pt": "Desatendido"}', 1);
/*!40000 ALTER TABLE `site_profile` ENABLE KEYS */;

-- Dumping data for table data1.warehouse_type: ~2 rows (approximately)
/*!40000 ALTER TABLE `warehouse_type` DISABLE KEYS */;
INSERT IGNORE INTO `warehouse_type` (`id`, `description`, `code`) VALUES
	(1, '{"en-en":"Wet Warehouse","pt-pt":"Armazém de Liquidos"}', 'WET'),
	(2, '{"en-en":"Dry Warehouse","pt-pt":"Armazém de Sólidos"}', 'DRY');
/*!40000 ALTER TABLE `warehouse_type` ENABLE KEYS */;