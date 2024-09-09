CREATE TABLE IF NOT EXISTS `user_summary` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;