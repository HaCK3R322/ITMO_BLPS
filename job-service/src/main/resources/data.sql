INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_WORKER');
INSERT INTO roles (id, name) VALUES (3, 'ROLE_HR');


INSERT INTO privileges (id, name) VALUES (1, 'USER_MANIPULATION');
INSERT INTO privileges (id, name) VALUES (2, 'RESUME_CREATE');
INSERT INTO privileges (id, name) VALUES (3, 'VACANCY_CREATE');
INSERT INTO privileges (id, name) VALUES (4, 'RESUME_VIEW');
INSERT INTO privileges (id, name) VALUES (5, 'VACANCY_VIEW');
INSERT INTO privileges (id, name) VALUES (6, 'RESUME_MANIPULATION');


INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 1);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 3);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 4);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (1, 5);

INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 2);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 4);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 5);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (2, 6);

INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 3);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 4);
INSERT INTO roles_privileges (role_id, privilege_id) VALUES (3, 5);
