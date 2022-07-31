UPDATE receipt_layout SET description = 'Cod. Barras' WHERE id = 4;

UPDATE receipt_template_type SET updated_on =  NOW() WHERE id = 1;
UPDATE receipt_template_type SET updated_on =  NOW() WHERE id = 2;

INSERT INTO receipt_layout
(id, created_on, updated_on, code, description)
VALUES (5, NOW(), NOW(), 'QRCODE', 'Qr. Code');
INSERT INTO receipt_layout
(id, created_on, updated_on, code, description)
VALUES (6, NOW(), NOW(), 'LOGO', 'Logo');

INSERT INTO receipt_template (id, created_on, updated_on, code, description, receipt_template_type_id) VALUES(1, NOW(), NOW(),'1704b395-2cf9-45ff-a204-6447e6e8d0da','Recept of Sale', 1);

INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '0', 1, 1, 1, 6);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Petrotec Inovaçao & Industria SA', 2, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Parque Industrial da Ponte, S. Joao de Ponte', 3, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Telephone :  (+351) 253 479 300', 4, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Vatin : 502381201', 5, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Portugal', 6, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '', 7, 1, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Date             Time         POS         Shift', 1, 3, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_DATE@ @14s#SALE_TIME@  @4s#POS_CODE@ @14s#SHIFT_OPERATOR_SHIFT_NUMBER@', 2, 3, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Operator: @SHIFT_OPERATOR_NAME@', 3, 3, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_TYPE_DESCRIPTION@', 4, 3, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_INVOICE_NO@', 5, 3, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '------------------------------------------------', 1, 4, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Qtd.         Unit Price         Product', 2, 4, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '                     IVA              Sub-Total', 3, 4, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '------------------------------------------------', 4, 4, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Fuel Point: @SALE_FUEL_LINE_PUMP_NUMBER@', 1, 5, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_LINE_QUANTITY@ @1s#SALE_LINE_UOM@ @10s#SALE_LINE_PRODUCT_UNIT_PRICE@ @1s#SALE_LINE_PRODUCT_UNIT_PRICE_UOM@ @19s#SALE_LINE_PRODUCT_DESCRIPTION@  ', 2, 5, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@25s#SALE_LINE_TAX_VALUE@@18s#SALE_LINE_TOTAL@ EUR  ', 3, 5, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Name: @SALE_CUSTOMER_NAME@', 1, 8, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Vatin: @SALE_CUSTOMER_VATIN@', 2, 8, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Address: @SALE_CUSTOMER_ADDRESS@', 3, 8, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@-20s#SALE_MOP_DESCRIPTION@@23s#SALE_MOP_TOTAL@ EUR', 1, 6, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Change: @35s#CHANGE_VALUE@ EUR', 2, 6, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@-20s#SALE_TAX_DESCRIPTION@@23s#SALE_TAX_TOTAL@ EUR', 1, 22, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Total Sale:@32s#SALE_TOTAL@ EUR', 1, 7, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_DOCUMENT_NO@', 2, 7, 1, 4);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '@SALE_DOCUMENT_NO@', 3, 7, 1, 5);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '------------------------------------------------', 1, 10, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), 'Thank you, have a nice journey', 2, 10, 1, 1);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES(NOW(), NOW(), '------------------------------------------------', 3, 10, 1, 2);
INSERT INTO site.receipt_line
(created_on, updated_on, line_data, line_no, receipt_block_type_id, receipt_template_id, receipt_layout_id)
VALUES( NOW(), NOW(), 'Pulse Pos, All solutions 4 you', 4, 10, 1, 1);
