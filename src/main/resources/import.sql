INSERT INTO `roles` (`id`,`description`,`title`) VALUES (1,'Role for admins users','Admin');

INSERT INTO `roles` (`id`,`description`,`title`) VALUES (2,'Role for normal users','User');

INSERT INTO `users` (`id`,`name`,`email`,`username`,`password`,`is_active`,`role_id`,`created_at`,`updated_at`)
VALUES (1,'Carlos Alberto Arroyo Martínez','carroyom@mail.com','carroyom','$2y$10$Tejps1b9A8oOCTGT8wB01ebx626klMvzaaBA.X7IBYAnAjqT0RFRe',b'1',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);

INSERT INTO `users` (`id`,`name`,`email`,`username`,`password`,`is_active`,`role_id`,`created_at`,`updated_at`)
VALUES (2,'Cathy Stefania Guido Rojas','cguidor@mail.com','cguidor','$2b$2y$10$Tejps1b9A8oOCTGT8wB01ebx626klMvzaaBA.X7IBYAnAjqT0RFRe',b'0',2,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP);
