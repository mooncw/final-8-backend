ALTER TABLE user
    MODIFY COLUMN password VARCHAR(60);

ALTER TABLE user_management
    MODIFY COLUMN password VARCHAR(60);