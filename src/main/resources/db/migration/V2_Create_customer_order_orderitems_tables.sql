USE `Kee_V2C_Platform`;


Drop table if exists `orders`;


CREATE TABLE `orders` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
	`customer_id` int NOT NULL,
    `shipping_address` varchar(128) NOT NULL,-- could be the same as the users address or different
    `status` varchar(20) default 'Pending',  -- CANCELED,PENDING,SHIPPED,DELIVERED
    `total_price` DECIMAL(10,2) NOT NULL,
    `ordered_at` datetime  not null default current_timestamp, 
    `delivered_at` datetime default null,
    
	PRIMARY KEY (`id`),
    Constraint `customer_order_fk` Foreign key (`customer_id`) REFERENCES customers(`id`),
	INDEX idx_order_price (total_price)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


Drop table if exists `order_items`;


CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
  `order_id` int  NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int not null,
  `price` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  Constraint `orderitems_orderid_fk` Foreign key (`order_id`) REFERENCES orders(`id`),
  Constraint `orderitems_product_fk` Foreign key (`product_id`) REFERENCES products(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `sub_orders` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `order_id` int not null,
	`vendor_id` int NOT NULL,
    `status` varchar(20) default 'Pending',  -- CANCELED,PENDING,SHIPPED,DELIVERED
    `total_price` DECIMAL(10,2) NOT NULL,
    `ordered_at` datetime  not null default current_timestamp, 
    `delivered_at` datetime default null,
	PRIMARY KEY (`id`),
	Constraint `sub_order_master_order_fk` Foreign key (`order_id`) REFERENCES orders(`id`),
    Constraint `sub_order_vendor_fk` Foreign key (`vendor_id`) REFERENCES vendors(`id`),
	INDEX idx_order_price (total_price)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



