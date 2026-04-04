Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email`  varchar(45) 	NOT NULL,
  `phone_number` varchar(20) default Null,  
  PRIMARY KEY (`id`),
  Constraint `unique_email` UNIQUE (`email`),
  INDEX idx_user_phone_number (phone_number),
  INDEX idx_user_full_name (first_name, last_name),-- index for first name only or firstname+lastname
  INDEX idx_user_last_name ( last_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



DROP TABLE IF EXISTS `users_credentials`;

CREATE TABLE `users_credentials` (
  `user_id` int NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,-- bcrypt just needs 68 char, but we make it 255 to give ourself a space for more complex algo in future
  `created_at` datetime NOT NULL default current_timestamp,
  `updated_at` datetime default current_timestamp On update current_timestamp,
  `enabled` boolean NOT NULL default 1,
  PRIMARY KEY (`user_id`),
  constraint `Fk_credentials_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
  constraint `unique_user_name` UNIQUE (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;




Show Index from users;

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `role` varchar(45) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  constraint `Fk_roles_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
  CONSTRAINT unique_user_role UNIQUE (user_id, role) -- prevents the bug where a user might be assigned the same role twice 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;









