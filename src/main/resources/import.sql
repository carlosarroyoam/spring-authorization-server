INSERT INTO roles (id, name, description) VALUES
(1,'ADMIN','Role for admins users'),
(2,'CUSTOMER','Role for customer users');

INSERT INTO users (id, first_name, last_name, email, password_hash, status, role_id, created_at, updated_at) VALUES
(1, 'Carlos Alberto', 'Arroyo Martínez', 'carroyom@mail.com', '$2b$10$vNVtCVv7IxX1Q9Whwb//ie6SZROFY4IYcDOSn146SWph8UBEzSYte', 'ACTIVE', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), 
(2, 'Cathy Stefania', 'Guido Rojas', 'cguidor@mail.com', '$2b$2b$10$vNVtCVv7IxX1Q9Whwb//ie6SZROFY4IYcDOSn146SWph8UBEzSYte', 'ACTIVE', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
