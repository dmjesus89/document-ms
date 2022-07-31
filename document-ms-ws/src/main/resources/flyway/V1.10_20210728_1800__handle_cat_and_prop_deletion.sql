ALTER TABLE category_element DROP FOREIGN KEY fk_category_element_category1;
ALTER TABLE category_element ADD CONSTRAINT fk_category_element_category1 FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE;


ALTER TABLE category_element_site DROP FOREIGN KEY fk_category_element_site_category1;
ALTER TABLE category_element_site ADD CONSTRAINT fk_category_element_site_category1 FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE CASCADE;
ALTER TABLE category_element_site DROP FOREIGN KEY fk_category_element_site_category_element1;
ALTER TABLE category_element_site ADD CONSTRAINT fk_category_element_site_category_element1 FOREIGN KEY (category_element_id) REFERENCES category_element(id) ON DELETE CASCADE;

ALTER TABLE property_site DROP FOREIGN KEY fk_property_site_property1;
ALTER TABLE property_site ADD CONSTRAINT fk_property_site_property1 FOREIGN KEY (property_id) REFERENCES property(id) ON DELETE CASCADE;

