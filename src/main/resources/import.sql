INSERT INTO roles (id, name, description) VALUES
(1, 'ADMIN', 'Admin user role');

INSERT INTO users (id, first_name, last_name, email, password_hash, status, created_at, updated_at, deleted_at) VALUES
(1, 'Carlos Alberto', 'Arroyo Martínez', 'carlos.arroyo@example.com', '$2b$10$vNVtCVv7IxX1Q9Whwb//ie6SZROFY4IYcDOSn146SWph8UBEzSYte', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
(2, 'Cathy Stefania', 'Guido Rojas', 'cathy.guido@example.com', '$2b$10$vNVtCVv7IxX1Q9Whwb//ie6SZROFY4IYcDOSn146SWph8UBEzSYte', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1),
(2, 1);
