CREATE TABLE IF NOT EXISTS `queue_in_message` (
    `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
    `queue_topic_id` smallint(5) unsigned NOT NULL,
    `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    `last_process_error` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `processed` tinyint(1) NOT NULL,
    `message_updated_on` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    KEY `fk_queue_topic_idx2` (`queue_topic_id`),
    CONSTRAINT `fk_queue_topic2` FOREIGN KEY (`queue_topic_id`) REFERENCES `queue_topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
    ) ENGINE=InnoDB;

