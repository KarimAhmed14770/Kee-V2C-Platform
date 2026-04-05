Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;

--
-- Table structure for table `customer`
--


DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`user_id` int NOT NULL,
    `shipping_address` varchar(128) NOT NULL,-- could be the same as the users address or different
    `status` varchar(20) default 'Pending',  -- CANCELED,PENDING,SHIPPED,DELIVERED
    `total_price` DECIMAL(10,2) NOT NULL,
    `ordered_at` datetime  not null default current_timestamp, 
    `delivered_at` datetime default null,
    
	PRIMARY KEY (`id`),
    Constraint `user_order_fk` Foreign key (`user_id`) REFERENCES users(`id`),
	INDEX idx_order_price (total_price)
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






