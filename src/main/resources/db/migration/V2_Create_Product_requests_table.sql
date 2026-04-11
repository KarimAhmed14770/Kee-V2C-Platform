USE `Kee_V2C_Platform`;

Drop table if exists `add_product_model_requests`;

CREATE TABLE `add_product_model_requests` (
	`id` int NOT NULL AUTO_INCREMENT,-- this id is the address for the database search
    `vendor_id` int not null,
    `name` varchar(200)NOT NULL,
    `description` varchar(500) NOT NULL, 
    `image_url` varchar(1000) NOT NULL, 
    `is_global` Boolean default true not null,
    `status` varchar(20) default 'PENDING',  -- APPROVED,PENDING,REJECTED
    `added_at` datetime Not null default current_timestamp On update current_timestamp,
	PRIMARY KEY (`id`),
	constraint `Fk_request_vendor_id` FOREIGN KEY (`vendor_id`) REFERENCES vendors(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

