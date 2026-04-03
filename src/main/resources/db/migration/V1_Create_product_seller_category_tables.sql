Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;



DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,  
    `image_url` varchar(1000) default Null, 
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_category_name` UNIQUE (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `seller`;

CREATE TABLE `seller` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`shop_name` varchar(45) Default NULL,
    `user_id` int NOT NULL,
    `rating` DECIMAL(2,1) default Null,
	PRIMARY KEY (`id`),
    constraint `unique_shop_name` UNIQUE (`shop_name`),
	constraint `fk_seller_userid` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `products`;

CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `name` varchar(45) NOT NULL,
  `category_id` int NOT NULL,
  `description` varchar(1000) default Null,  
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
  Constraint `product_seller_fk` Foreign key (`seller_id`) REFERENCES seller(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE INDEX idx_seller_id ON products(seller_id);
CREATE INDEX idx_product_category ON products(category_id);



