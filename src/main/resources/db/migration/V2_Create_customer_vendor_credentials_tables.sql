USE `Kee_V2C_Platform`;

Drop table if exists `customers`;

CREATE TABLE `customers` (
  `id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `phone_number` varchar(20) default Null,  
  `shipping_Address` VARCHAR(500) DEFAULT NULL,
  `image_url` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  Constraint fk_customer_user_id foreign key (`id`) references users_credentials(`id`),
  INDEX idx_user_phone_number (phone_number),
  INDEX idx_user_full_name (first_name, last_name),-- index for first name only or firstname+lastname
  INDEX idx_user_last_name ( last_name),
  FULLTEXT idx_customer_address (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


Drop table if exists `users_credentials`;


CREATE TABLE `users_credentials` (
  `id` int NOT NULL auto_increment,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,-- bcrypt just needs 68 char, but we make it 255 to give ourself a space for more complex algo in future
  `email`    varchar(255) NOT NULL,
  `created_at` datetime NOT NULL default current_timestamp,
  `updated_at` datetime default current_timestamp On update current_timestamp,
  `status` varchar(45) NOT NULL default 'INACTIVE', 
  PRIMARY KEY (`id`),
  constraint `unique_user_name` UNIQUE (`user_name`),
  constraint `unique_email` UNIQUE (`email`),
  index user_status_index(`status`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



Drop table if exists `vendor`;


CREATE TABLE `vendors` (
	`id` int NOT NULL,
	`vendor_name` varchar(45) Default NULL,
    `description` varchar(500) NOT NULL, -- description what type of products, what categories ...etc
    `business_address` VARCHAR(500) NOT NULL,-- the address of the main branch for the vendor 
    `rating` DECIMAL(2,1) default 2.5, -- a vendor should start with a default rating of 2.5 , it increases or decreases based on customer review
    `image_url` VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (`id`),
    constraint unique_vendor_name UNIQUE (`vendor_name`),
    FULLTEXT idx_vendor_description (`description`), -- for searching for specific shops based on description
    FULLTEXT idx_vendor_address (`vendor_address`), -- for searching for specific substrings inside the description
	constraint `fk_vendor_user_id` FOREIGN KEY (`id`) REFERENCES `users_credentials`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

Drop table if exists `users_roles`;

CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  constraint `Fk_roles_userid` FOREIGN KEY (`user_id`) REFERENCES `users_credentials`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
