ALTER TABLE `user`
DROP PRIMARY KEY;

ALTER TABLE `user`
    CHANGE COLUMN `id` `user_id` VARCHAR(15) COLLATE utf8mb4_unicode_ci NOT NULL;

ALTER TABLE `user`
    ADD COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST;

ALTER TABLE `user`
    ADD UNIQUE KEY `UKm6kk0lDtGa2NQdScuKFV67YUm` (`user_id`);

CREATE OR REPLACE VIEW user_union_view AS
SELECT user_id, phone_number, emp_number, email
FROM user
UNION ALL
SELECT user_id, phone_number, emp_number, email
FROM user_management;