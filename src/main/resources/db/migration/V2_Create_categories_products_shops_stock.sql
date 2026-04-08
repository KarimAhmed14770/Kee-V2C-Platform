USE `Kee_V2C_Platform`;


Drop table if exists `categories`;

CREATE TABLE `categories` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,  
    `image_url` varchar(1000) default Null, 
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_category_name` UNIQUE (`name`),
    FULLTEXT idx_category_description (`description`) -- for searching for specific substrings inside the description
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



CREATE TABLE `sub_categories` (
	`id` int NOT NULL AUTO_INCREMENT,
    `parent_category_id` int not null,
	`name` varchar(45) NOT NULL,
    `description` varchar(1000) default Null,  
    `image_url` varchar(1000) default Null, 
    `active` boolean Not Null DEFAULT TRUE,
	PRIMARY KEY (`id`),
    constraint `unique_sub_category_name` UNIQUE (`name`),
    FULLTEXT idx_sub_category_description (`description`), -- for searching for specific substrings inside the description
    constraint `fk_subcategory_parent_id` FOREIGN KEY (`parent_category_id`) REFERENCES `categories`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


Drop table if exists `brands`;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(300) default Null,
  `image_url` varchar(1000) default Null, 
  PRIMARY KEY (`id`),
  constraint `unique_brand_name` UNIQUE (`name`),
  FULLTEXT idx_brand_description (`description`) -- The FTS index definition
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

Drop table if exists `product_models`;
CREATE TABLE `product_models` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Brand_id` int not null,
  `category_id` int NOT NULL,-- will point to a subcategory
  `name` varchar(45) NOT NULL,
  `description` varchar(300) default Null,-- for official factory specs
  `image_url` varchar(1000) default Null, -- the clean image that will be shown to the user
  `is_global` BOOLEAN DEFAULT TRUE,
  `owner_vendor_id` INT DEFAULT NULL,
   `status` varchar(30) default 'PENDING_APPROVAL',
   PRIMARY KEY (`id`),
   Constraint `model_category_id_fk` Foreign key (`category_id`) REFERENCES sub_categories(`id`),
   Constraint `model_brand_id_fk` Foreign key (`Brand_id`) REFERENCES brands(`id`),
   CONSTRAINT fk_model_owner FOREIGN KEY (owner_vendor_id) REFERENCES vendors(id),
   FULLTEXT idx_model_description (`description`) -- The FTS index definition
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;




Drop table if exists `products`;


CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `model_id` int NOT NULL,
  `vendor_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(300) default Null,  -- for vendor specific specs ex:comes with a free screen protector
  `price`  DECIMAL(10,2) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp,
  `updated_at` datetime not null default current_timestamp on update current_timestamp,
  `active` boolean NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`id`),
  Constraint `product_vendor_fk` Foreign key (`vendor_id`) REFERENCES vendors(`id`),
  Constraint `product_model_fk` Foreign key (`model_id`) REFERENCES product_models(`id`),
  INDEX idx_product_price (price),
  FULLTEXT idx_product_description (`description`) -- The FTS index definition
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


Drop table if exists `shops`;


CREATE TABLE `shops` (
  `id` int NOT NULL AUTO_INCREMENT,
  `vendor_id` int not null,
  `name` varchar(100) NOT NULL,
  `address` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp,
  Primary key(`id`),
  Constraint `shop_vendor_id_fk` Foreign key (`vendor_id`) REFERENCES vendors(`id`),
  Constraint `unique_shop_vendor_fk` Unique (`vendor_id`) -- allowing one vendor to have only 1 shop for now 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

Drop table if exists `stock`;


CREATE TABLE `stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shop_id` int not null,
  `product_id` int NOT NULL,
  `quantity` int NOT NULL,
  `updated_at` datetime not null default current_timestamp on update current_timestamp,
  Primary key(`id`),
  CONSTRAINT `unique_product_stock_per_shop` UNIQUE (`product_id`, `shop_id`), -- only 1 stock allowed for a product in 1 shop
  Constraint `stock_product_id_fk` Foreign key (`product_id`) REFERENCES products(`id`),
  Constraint `stock_shop_id_fk` Foreign key (`shop_id`) REFERENCES shops(`id`),
  INDEX idx_product_stock (quantity)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
