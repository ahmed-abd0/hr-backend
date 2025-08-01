




INSERT INTO authorities (id, name) VALUES (1,'ROLE_USER'), (2,'ROLE_ADMIN');

INSERT INTO users
(id,createdAt, createdBy, updatedAt, updatedBy, accountNonExpired, accountNonLocked, credentialsNonExpired, email, employee_id, enabled, name, password)
VALUES (1,CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system', TRUE, TRUE, TRUE, 'admin@email.com', NULL, TRUE, 'Admin', ':password');

INSERT INTO user_roles (user_id, authority_id) VALUES (1, 1), (1, 2);