CREATE TABLE IF NOT EXISTS `ad_category` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `category` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UKt6ma1mhmodsorewvh8riy19ea` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `ad_decision` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `decision` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `UK5h86ftv9wdn02qpf7aqgcx04l` (`decision`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `ad_provision` (
                                `article` smallint NOT NULL,
                                `id` int NOT NULL AUTO_INCREMENT,
                                `content` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `UKgpheii1raavn63nstqoxs170i` (`article`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `ad_review` (
                             `ad_provision_id` bigint DEFAULT NULL,
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `opinion` text COLLATE utf8mb4_unicode_ci NOT NULL,
                             `sentence` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `advertisement` (
                                 `ad_category_id` bigint DEFAULT NULL,
                                 `issue` tinyint NOT NULL,
                                 `same` tinyint NOT NULL,
                                 `state` tinyint NOT NULL,
                                 `ad_decision_id` bigint DEFAULT NULL,
                                 `post_datetime` datetime NOT NULL,
                                 `advertiser` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `assignee_id` bigint DEFAULT NULL,
                                 `id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `media` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `modifier_id` bigint DEFAULT NULL,
                                 `product` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `UK5mkt2c7gb20imt68y3vbh82m6` (`ad_decision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user` (
                        `final_login_datetime` datetime NOT NULL,
                        `signup_datetime` datetime NOT NULL,
                        `email` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `emp_number` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `id` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `phone_number` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
                        `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
                        UNIQUE KEY `UKc1u1muochy6pulk9j61hbtsi1` (`emp_number`),
                        UNIQUE KEY `UK4bgmpi98dylab6qdvf9xyaxu4` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_management` (
                                   `signup_datetime` datetime NOT NULL,
                                   `email` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `emp_number` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `id` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   `phone_number` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL,
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `UKh074liblpwa914lnppxy8qisc` (`email`),
                                   UNIQUE KEY `UKoplmbryqsjl0gi0ij1eisqjch` (`emp_number`),
                                   UNIQUE KEY `UKpefcmoj1kji5xd458kn4m6g4i` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

