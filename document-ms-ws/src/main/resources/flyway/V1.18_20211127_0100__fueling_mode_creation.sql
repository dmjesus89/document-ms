CREATE TABLE IF NOT EXISTS `fuelling_mode` (
    `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
    `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_enabled` tinyint(1) NOT NULL,
    `max_time_to_reach_min_limit` smallint(4) unsigned not null,
     `max_time_without_progress` smallint(2) unsigned not null,
     `max_fuelling_time` smallint(4) unsigned not null,
     `max_transaction_volume` smallint(7) unsigned not null,
     `max_transaction_amount` smallint(7) unsigned not null,
     `max_volume_overrun` smallint(2) unsigned not null,
     `clear_display_time` smallint(4) unsigned not null,
     `clear_display_when_transaction_is_cleared` tinyint(1) not null,
    `created_on` datetime NOT NULL DEFAULT current_timestamp(),
    `updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`code`),
    UNIQUE KEY `card_system_uq` (`code`)
    ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
