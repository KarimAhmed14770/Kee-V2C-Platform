USE `Kee_V2C_Platform`;

Drop table if exists `cart_item`;

CREATE TABLE `cart_item` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `customer_id` int NOT NULL,
    `product_id` int NOT NULL, 
    `quantity` int NOT NULL, 
    `added_at` datetime Not null default current_timestamp On update current_timestamp,
	PRIMARY KEY (`id`),
	constraint `Fk_custome_cart_item` FOREIGN KEY (`customer_id`) REFERENCES customers(`id`),
	constraint `Fk_product_cart` FOREIGN KEY (`product_id`) REFERENCES products(`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
