CREATE TABLE IF NOT EXISTS `ad_similarity` (
            `inspection_ad_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
            `comparison_ad_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
            `similarity` decimal(5, 4) NOT NULL,
            `same_sentence` text COLLATE utf8mb4_unicode_ci NOT NULL,
            `same_sentence_count` smallint NOT NULL,
            PRIMARY KEY (`inspection_ad_id`, `comparison_ad_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;