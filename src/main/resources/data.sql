INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_WORKER');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_HR');


INSERT INTO privileges (id, name) VALUES (1, 'USER_MANIPULATION');
INSERT INTO privileges (id, name) VALUES (2, 'RESUME_MANIPULATION');
INSERT INTO privileges (id, name) VALUES (3, 'VACANCY_MANIPULATION');


INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 3);

INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 2);

INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 3);