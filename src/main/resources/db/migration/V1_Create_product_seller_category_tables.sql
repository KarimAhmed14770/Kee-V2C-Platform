Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;

--
-- Table structure for table `products`
--



DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,  
    `image_url` varchar(1000) default Null, 
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_category_name` UNIQUE (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



CREATE TABLE `seller` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`shop_name` varchar(45) NOT NULL,
    `user_id` int NOT NULL,
    `rating` DECIMAL(2,1) Not Null,
	PRIMARY KEY (`id`),
    constraint `unique_shop_name` UNIQUE (`shop_name`),
	constraint `Fk_roles_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


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
  Key `category_idx` (`category_id`),
  Key `seller_idx`	(`seller_id`),
  Constraint `product_category_fk` Foreign key (`category_id`) REFERENCES category(`id`),
  Constraint `product_seller_fk` Foreign key (`seller_id`) REFERENCES seller(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;






