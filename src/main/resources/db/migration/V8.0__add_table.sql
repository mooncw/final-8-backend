CREATE TABLE IF NOT EXISTS `ad_content` (
                            `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                            `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;