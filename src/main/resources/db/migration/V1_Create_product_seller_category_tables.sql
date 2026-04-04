Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;



DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,  
    `image_url` varchar(1000) default Null, 
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_category_name` UNIQUE (`name`),
    FULLTEXT idx_category_description (`description`) -- for searching for specific substrings inside the description
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `seller`;

CREATE TABLE `seller` (
	`seller_id` int NOT NULL,
	`shop_name` varchar(45) Default NULL,
    `shop_address` VARCHAR(500) DEFAULT NULL,
    `rating` DECIMAL(2,1) default Null,
    `image_url` VARCHAR(255) DEFAULT NULL,
    `created_at` datetime NOT NULL default current_timestamp,
    `updated_at` datetime Not null default current_timestamp On update current_timestamp,
    `active` boolean DEFAULT true,
	PRIMARY KEY (`seller_id`),
    constraint `unique_shop_name` UNIQUE (`shop_name`),
    FULLTEXT idx_shop_address (`shop_address`), -- for searching for specific substrings inside the description
	constraint `fk_seller_user_id` FOREIGN KEY (`seller_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `name` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `description` varchar(300) default Null,  
  `price`  DECIMAL(10,2) NOT NULL,
  `stock`  int default NULL,
  `image_url` varchar(1000) default Null, 
  `seller_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp,
  `updated_at` datetime not null default current_timestamp on update current_timestamp,
  `active` boolean NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`id`),
  Constraint `unique_name` UNIQUE (`name`),
  Constraint `product_category_fk` Foreign key (`category_id`) REFERENCES category(`id`),
  Constraint `product_seller_fk` Foreign key (`seller_id`) REFERENCES seller(`seller_id`),
  INDEX idx_product_price (price),
  INDEX idx_product_stock (stock),
  FULLTEXT idx_product_description (`description`) -- The FTS index definition
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;




