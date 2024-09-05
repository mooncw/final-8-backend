CREATE TABLE IF NOT EXISTS `ad_media` (
            `id` int NOT NULL AUTO_INCREMENT,
            `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
            PRIMARY KEY (`id`),
            UNIQUE KEY UKadmeidaname (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE advertisement
    CHANGE COLUMN media media_id int;