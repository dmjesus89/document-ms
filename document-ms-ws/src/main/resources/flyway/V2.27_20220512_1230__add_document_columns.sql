ALTER TABLE document
    ADD pos_number SMALLINT(4) NULL;
ALTER TABLE document
    ADD site_id SMALLINT(5) UNSIGNED NULL;
ALTER TABLE document
    ADD CONSTRAINT document_FK FOREIGN KEY (site_id) REFERENCES site.site (id);

ALTER TABLE document
    ADD is_stock_movement tinyint(1) NULL DEFAULT 1;
