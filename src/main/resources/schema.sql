CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(128) NOT NULL,
  `title` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_roles_title` (`title`)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `role_id` int NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_users_email` (`email`),
  UNIQUE KEY `UK_users_username` (`username`),
  KEY `FK_users_role_id` (`role_id`),
  CONSTRAINT `FK_users_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `clients` (
    `id` varchar(255) NOT NULL,
    `client_id` varchar(255) NOT NULL,
    `client_id_issued_at` timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `client_secret` varchar(255) DEFAULT NULL,
    `client_secret_expires_at` timestamp DEFAULT NULL,
    `client_name` varchar(255) NOT NULL,
    `client_authentication_methods` varchar(1000) NOT NULL,
    `authorization_grant_types` varchar(1000) NOT NULL,
    `redirect_uris` varchar(1000) DEFAULT NULL,
    `post_logout_redirect_uris` varchar(1000) DEFAULT NULL,
    `scopes` varchar(1000) NOT NULL,
    `client_settings` varchar(2000) NOT NULL,
    `token_settings` varchar(2000) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `authorizations` (
    `id` varchar(255) NOT NULL,
    `registered_client_id` varchar(255) NOT NULL,
    `principal_name` varchar(255) NOT NULL,
    `authorization_grant_type` varchar(255) NOT NULL,
    `authorized_scopes` varchar(1000) DEFAULT NULL,
    `attributes` text DEFAULT NULL,
    `state` varchar(500) DEFAULT NULL,
    `authorization_code_value` text DEFAULT NULL,
    `authorization_code_issued_at` timestamp DEFAULT NULL,
    `authorization_code_expires_at` timestamp DEFAULT NULL,
    `authorization_code_metadata` text DEFAULT NULL,
    `access_token_value` text DEFAULT NULL,
    `access_token_issued_at` timestamp DEFAULT NULL,
    `access_token_expires_at` timestamp DEFAULT NULL,
    `access_token_metadata` text DEFAULT NULL,
    `access_token_type` varchar(255) DEFAULT NULL,
    `access_token_scopes` varchar(1000) DEFAULT NULL,
    `refresh_token_value` text DEFAULT NULL,
    `refresh_token_issued_at` timestamp DEFAULT NULL,
    `refresh_token_expires_at` timestamp DEFAULT NULL,
    `refresh_token_metadata` text DEFAULT NULL,
    `oidc_id_token_value` text DEFAULT NULL,
    `oidc_id_token_issued_at` timestamp DEFAULT NULL,
    `oidc_id_token_expires_at` timestamp DEFAULT NULL,
    `oidc_id_token_metadata` text DEFAULT NULL,
    `oidc_id_token_claims` text DEFAULT NULL,
    `user_code_value` text DEFAULT NULL,
    `user_code_issued_at` timestamp DEFAULT NULL,
    `user_code_expires_at` timestamp DEFAULT NULL,
    `user_code_metadata` text DEFAULT NULL,
    `device_code_value` text DEFAULT NULL,
    `device_code_issued_at` timestamp DEFAULT NULL,
    `device_code_expires_at` timestamp DEFAULT NULL,
    `device_code_metadata` text DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `authorization_consents` (
    `registered_client_id` varchar(255) NOT NULL,
    `principal_name` varchar(255) NOT NULL,
    `authorities` varchar(1000) NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`)
) ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
