Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;

--
-- Table structure for table `customer`
--


DROP TABLE IF EXISTS `customer`;


CREATE TABLE `customer` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `user_id` int NOT NULL,
    `address` VARCHAR(500) DEFAULT NULL,
    `image_url` VARCHAR(255) DEFAULT NULL,
    `created_at` datetime NOT NULL default current_timestamp,
    `updated_at` datetime Not null default current_timestamp On update current_timestamp,
    `active` boolean DEFAULT true,
	PRIMARY KEY (`id`),
	constraint `Fk_customer_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


ALTER TABLE `customer` 
    ADD COLUMN `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD COLUMN `active` boolean DEFAULT true;



DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`customer_id` int NOT NULL,
    `shipping_address` varchar(128) NOT NULL,
    `status` varchar(20) default 'Pending',  -- CANCELED,PENDING,SHIPPED,DELIVERED
    `total_price` DECIMAL(10,2) NOT NULL,
    `ordered_at` datetime  not null default current_timestamp, 
    `delivered_at` datetime default null,
    
	PRIMARY KEY (`id`),
    Key `customer_idx` (`customer_id`),
    Constraint `customer_order_fk` Foreign key (`customer_id`) REFERENCES customer(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



DROP TABLE IF EXISTS `order_items`;

CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `order_id` int  NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int not null,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  Constraint `items_orderid_fk` Foreign key (`order_id`) REFERENCES orders(`id`),
  Constraint `items_product_fk` Foreign key (`product_id`) REFERENCES products(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;






