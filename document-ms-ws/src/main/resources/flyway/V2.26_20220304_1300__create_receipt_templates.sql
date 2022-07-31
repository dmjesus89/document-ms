-- receipt_template_type definition

CREATE TABLE IF NOT EXISTS `receipt_template_type`
(
    `id`         smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `created_on` datetime     DEFAULT NULL,
    `updated_on` datetime     DEFAULT NULL,
    `code`       varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = latin1;

-- receipt_layout definition

CREATE TABLE IF NOT EXISTS `receipt_layout`
(
    `id`          smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `created_on`  datetime     DEFAULT NULL,
    `updated_on`  datetime     DEFAULT NULL,
    `code`        varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = latin1;

-- receipt_template definition

CREATE TABLE IF NOT EXISTS `receipt_template`
(
    `id`                       smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `created_on`               datetime             DEFAULT NULL,
    `updated_on`               datetime             DEFAULT NULL,
    `code`                     varchar(255)         DEFAULT NULL,
    `description`              varchar(255)         DEFAULT NULL,
    `receipt_template_type_id` smallint(5) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_receipt_template_type_id` (`receipt_template_type_id`),
    CONSTRAINT `FK_receipt_template_type_id` FOREIGN KEY (`receipt_template_type_id`) REFERENCES `receipt_template_type` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  DEFAULT CHARSET = latin1;

-- receipt_block_type definition

CREATE TABLE IF NOT EXISTS `receipt_block_type`
(
    `id`                       smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `created_on`               datetime             DEFAULT NULL,
    `updated_on`               datetime             DEFAULT NULL,
    `code`                     varchar(255)         DEFAULT NULL,
    `description`              varchar(255)         DEFAULT NULL,
    `receipt_template_type_id` smallint(5) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_receipt_template__type_id_block` (`receipt_template_type_id`),
    CONSTRAINT `FK_receipt_template__type_id_block` FOREIGN KEY (`receipt_template_type_id`) REFERENCES `receipt_template_type` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = latin1;

-- receipt_line definition

CREATE TABLE IF NOT EXISTS `receipt_line`
(
    `id`                    smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `created_on`            datetime             DEFAULT NULL,
    `updated_on`            datetime             DEFAULT NULL,
    `line_data`             varchar(255)         DEFAULT NULL,
    `line_no`               bigint(20)           DEFAULT NULL,
    `receipt_block_type_id` smallint(5) unsigned DEFAULT NULL,
    `receipt_template_id`   smallint(5) unsigned DEFAULT NULL,
    `receipt_layout_id`     smallint(5) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_receipt_block_type_id` (`receipt_block_type_id`),
    KEY `FK_receipt_template_id` (`receipt_template_id`),
    KEY `FK_receipt_layout_id` (`receipt_layout_id`),
    CONSTRAINT `FK_receipt_block_type_id` FOREIGN KEY (`receipt_template_id`) REFERENCES `receipt_template` (`id`),
    CONSTRAINT `FK_receipt_layout_id` FOREIGN KEY (`receipt_layout_id`) REFERENCES `receipt_layout` (`id`),
    CONSTRAINT `FK_receipt_template_id` FOREIGN KEY (`receipt_block_type_id`) REFERENCES `receipt_block_type` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = latin1;

-- site_receipt_template definition

CREATE TABLE IF NOT EXISTS `site_receipt_template`
(
    `id`                  smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `site_id`             smallint(5) unsigned DEFAULT NULL,
    `receipt_template_id` smallint(5) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `site_device_fk` (`site_id`),
    KEY `receipt_template_fk` (`receipt_template_id`),
    CONSTRAINT `receipt_template_fk` FOREIGN KEY (`receipt_template_id`) REFERENCES `receipt_template` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `site_template_fk` FOREIGN KEY (`site_id`) REFERENCES `site` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

INSERT INTO receipt_template_type
    (id, created_on, updated_on, code)
VALUES (1, NOW(), '2020-05-28 10:43:17.000', 'SALE_RECEIPT');
INSERT INTO receipt_template_type
    (id, created_on, updated_on, code)
VALUES (2, NOW(), '2020-05-28 10:43:17.000', 'SALE_REFUND');

INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (1, NOW(), NOW(), 'HEADER', 'HEADER', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (2, NOW(), NOW(), 'SHIFT_DETAILS', 'SHIFT_DETAILS', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (3, NOW(), NOW(), 'SALE_HEADER', 'SALE_HEADER', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (4, NOW(), NOW(), 'SALE_LINE_HEADER', 'SALE_LINE_HEADER', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (5, NOW(), NOW(), 'SALE_LINE_DETAIL', 'SALE_LINE_DETAIL', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (6, NOW(), NOW(), 'MOP_DETAIL', 'MOP_DETAIL', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (7, NOW(), NOW(), 'SALE_TOTAL', 'SALE_TOTAL', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (8, NOW(), NOW(), 'CUSTOMER_DATA', 'CUSTOMER_DATA', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (9, NOW(), NOW(), 'SALE_FISCAL_INFO', 'SALE_FISCAL_INFO', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (10, NOW(), NOW(), 'SALE_FOOTER', 'SALE_FOOTER', 1);


INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (11, NOW(), NOW(), 'HEADER', 'HEADER', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (12, NOW(), NOW(), 'SHIFT_DETAILS', 'SHIFT_DETAILS', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (13, NOW(), NOW(), 'SALE_HEADER', 'SALE_HEADER', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (14, NOW(), NOW(), 'SALE_LINE_HEADER', 'SALE_LINE_HEADER', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (15, NOW(), NOW(), 'SALE_LINE_DETAIL', 'SALE_LINE_DETAIL', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (16, NOW(), NOW(), 'MOP_DETAIL', 'MOP_DETAIL', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (17, NOW(), NOW(), 'SALE_TOTAL', 'SALE_TOTAL', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (18, NOW(), NOW(), 'CUSTOMER_DATA', 'CUSTOMER_DATA', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (19, NOW(), NOW(), 'SALE_FISCAL_INFO', 'SALE_FISCAL_INFO', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (20, NOW(), NOW(), 'SALE_FOOTER', 'SALE_FOOTER', 2);

INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (21, NOW(), NOW(), 'SALE_FUEL_LINE_DETAIL', 'SALE_FUEL_LINE_DETAIL', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (22, NOW(), NOW(), 'TAX_DETAIL', 'TAX_DETAIL', 1);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (23, NOW(), NOW(), 'SALE_FUEL_LINE_DETAIL', 'SALE_FUEL_LINE_DETAIL', 2);
INSERT INTO receipt_block_type
(id, created_on, updated_on, code, description, receipt_template_type_id)
VALUES (24, NOW(), NOW(), 'TAX_DETAIL', 'TAX_DETAIL', 2);

INSERT INTO receipt_layout
    (id, created_on, updated_on, code, description)
VALUES (1, NOW(), NOW(), 'C', 'Central');
INSERT INTO receipt_layout
    (id, created_on, updated_on, code, description)
VALUES (2, NOW(), NOW(), 'E', 'Esquerda');
INSERT INTO receipt_layout
    (id, created_on, updated_on, code, description)
VALUES (3, NOW(), NOW(), 'D', 'Direita');
INSERT INTO receipt_layout
    (id, created_on, updated_on, code, description)
VALUES (4, NOW(), NOW(), 'BARCODE', 'BARCODE');
