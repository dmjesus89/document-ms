ALTER TABLE document_type
    MODIFY COLUMN code varchar(10) NOT NULL;


INSERT INTO document_type (code, prefix, description, is_enabled)
VALUES ('FT', 'FT', '{"pt-pt":"Fatura","en-en":"VAT Invoice"}', 1);
INSERT INTO document_type (code, prefix, description, is_enabled)
VALUES ('FS', 'FS', '{"pt-pt":"Fatura Simplificada","en-en":"VAT Invoice"}', 1);
INSERT INTO document_type (code, prefix, description, is_enabled)
VALUES ('NC', 'NC', '{"pt-pt":"Nota de Crédito","en-en":"Invoice refund"}', 1);
UPDATE document_type
SET code='GT'
WHERE id = 1;
