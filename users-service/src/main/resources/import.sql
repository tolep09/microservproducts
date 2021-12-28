INSERT INTO users (username, name, lastname, email, password, enable) VALUES ('karysvs', 'Karina', 'Vergara', 'karys@email.com', '$2a$10$m50qhAQ79aP.Eauk3u6aeui90aV16wYgMFXCP3JKUre.ptwMBx/6W', 1);
INSERT INTO users (username, name, lastname, email, password, enable) VALUES ('porfis', 'Porfirio', 'Torres', 'porfis@email.com', '$2a$10$5B8Jef9Nay3kYZgL6hwamuvtlrINMuqJUbHRZMSe8NCDP4PpzJh7K', 1);

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);