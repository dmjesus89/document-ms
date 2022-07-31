drop table if exists tank_level_gauges;

create table site_device_tank_level_gauge
(
    `id`                      smallint(5) unsigned NOT NULL,
    site_device_warehouse_id  smallint(5) unsigned not null,
    communication_method_data longtext             null,
    tlg_code                  smallint,
    communication_method_id   tinyint(3) unsigned  null,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `site_device_id_UNIQUE` (`id`) USING BTREE,
    KEY `fk_site_device_tlg` (`id`) USING BTREE,
    CONSTRAINT `site_device_tlg_fk` FOREIGN KEY (`id`) REFERENCES `site_device` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT site_device_tlg_warehouse_FK FOREIGN KEY (site_device_warehouse_id) REFERENCES site_device_warehouse (id),
    CONSTRAINT communication_method_tlg_FK FOREIGN KEY (communication_method_id) REFERENCES communication_method (id)
) engine = InnoDB;

