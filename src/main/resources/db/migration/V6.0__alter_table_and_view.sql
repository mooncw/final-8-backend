ALTER TABLE `user_management`
DROP PRIMARY KEY;

ALTER TABLE `user_management`
    CHANGE COLUMN `id` `user_id` VARCHAR(15) COLLATE utf8mb4_unicode_ci NOT NULL;

ALTER TABLE `user_management`
    ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE `user_management`
    ADD UNIQUE KEY `UK_user_id` (`user_id`);

CREATE OR REPLACE VIEW user_union_view AS
SELECT id, phone_number, emp_number, email
FROM user
UNION ALL
SELECT user_id AS id, phone_number, emp_number, email
FROM user_management;