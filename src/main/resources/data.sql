INSERT INTO user (id, username, password, email, firtname, lastname, address, phone, info, dob, status, created_date, last_modified_date) VALUES
(1, 'admin', '$2a$08$TnCz6JYlWmHHM/4gw3/XauAqxAsLlMZOyGKX1ueyKMnN0uRfVHyT.', 'admin@email.com', NULL, NULL, NULL, NULL, NULL, NULL, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);
INSERT INTO user (id, username, password, email, firtname, lastname, address, phone, info, dob, status, created_date, last_modified_date) VALUES
(2, 'user', '$2a$08$TnCz6JYlWmHHM/4gw3/XauAqxAsLlMZOyGKX1ueyKMnN0uRfVHyT.', 'user@email.com', NULL, NULL, NULL, NULL, NULL, NULL, 0,  CURRENT_TIMESTAMP,  CURRENT_TIMESTAMP);
INSERT INTO user (id, username, password, email, firtname, lastname, address, phone, info, dob, status, created_date, last_modified_date) VALUES
(3, 'very-old-user', '$2a$08$TnCz6JYlWmHHM/4gw3/XauAqxAsLlMZOyGKX1ueyKMnN0uRfVHyT.', 'very-old-user@email.com', NULL, NULL, NULL, NULL, NULL, NULL, 1,  CURRENT_TIMESTAMP - INTERVAL 10 DAY, NOW());

INSERT INTO role (id, authority, description, created_date, last_modified_date) VALUES
(1, 'ROLE_ADMIN', 'Administrator', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO role (id, authority, description, created_date, last_modified_date) VALUES
(2, 'ROLE_USER', 'Simple user', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO user_role (user_id, role_id) VALUES
(1, 1);
INSERT INTO user_role (user_id, role_id) VALUES
(2, 2);
INSERT INTO user_role (user_id, role_id) VALUES
(3, 2);

INSERT INTO email_hash (id, email, hash, type, active, expire, created_date, last_modified_date) VALUES
(1, 'very-old-user@email.com', 'the hash', 1, 1, NOW() - INTERVAL 5 HOUR, NOW(), NOW());
