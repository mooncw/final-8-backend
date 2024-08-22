CREATE VIEW user_union_view AS
SELECT id, phone_number, emp_number, email
FROM user
UNION ALL
SELECT id, phone_number, emp_number, email
FROM user_management;
