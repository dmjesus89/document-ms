INSERT INTO site_device_type (id, code, description, is_enabled)
VALUES (7, 'PRICE_SIGN', '{"en-en": "Price Sign", "pt-pt": "Tabela de Preços (Pórtico)"}', 1);

-- FCC
INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (1, 'FCC_DOMS', '{"en-en": "DOMS"}', 1);

-- NEW PUMPS
INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (2, 'PUMP_KA', '{"en-en": "Petrotec KA / Koppens KA"}', 1);
INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (2, 'PUMP_HDX', '{"en-en": "Petrotec HDX / Gilbarco"}', 1);
INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (2, 'PUMP_HIM', '{"en-en": "Petrotec HIM "}', 1);

-- POS


INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (7, 'PS_PETRO_APP', '{"en-en": "Petrotec Demo APP"}', 1);
INSERT IGNORE INTO site_device_subtype (device_type_id, code, description, is_enabled)
VALUES (3, 'TLG_DEMO', '{"en-en": "Demo TLG"}', 1);





