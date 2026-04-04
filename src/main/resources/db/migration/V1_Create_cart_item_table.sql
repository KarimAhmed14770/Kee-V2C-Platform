Create DATABASE  IF NOT EXISTS `Apex_Cart_Ecommerce`;
USE `Apex_Cart_Ecommerce`;

DROP TABLE IF EXISTS `cart_item`;


CREATE TABLE `cart_item` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `customer_id` int NOT NULL,
    `product_id` int NOT NULL, 
    `quantity` int NOT NULL, 
    `added_at` datetime Not null default current_timestamp On update current_timestamp,
	PRIMARY KEY (`id`),
	constraint `Fk_customer_cart` FOREIGN KEY (`customer_id`) REFERENCES `customer`(`customer_id`),
	constraint `Fk_product_cart` FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
