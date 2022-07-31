ALTER TABLE site_product
    MODIFY COLUMN fcc_product_code varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL;
ALTER TABLE document
    MODIFY COLUMN `number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL;
