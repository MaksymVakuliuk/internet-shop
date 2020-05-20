CREATE SCHEMA `internet-shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet-shop`.`products` (
  `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `price` DOUBLE NOT NULL,
  PRIMARY KEY (`products_id`));

CREATE TABLE `internet-shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(256) NOT NULL,
  `login` VARCHAR(256) NOT NULL,
  `password` VARCHAR(256) NOT NULL,
  `salt` VARBINARY(16) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE);


CREATE TABLE `internet-shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE);
INSERT INTO `internet-shop`.`roles` (`role_name`) VALUES ('ADMIN');
INSERT INTO `internet-shop`.`roles` (`role_name`) VALUES ('USER');

CREATE TABLE `internet-shop`.`shopping_carts` (
  `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`cart_id`),
  UNIQUE INDEX `cart_id_UNIQUE` (`cart_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_to_shopping_carts`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet-shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_to_orders`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet-shop`.`users_roles` (
  `user_id` BIGINT(11) NOT NULL,
  `role_id` BIGINT(11) NOT NULL,
  INDEX `fk_user_to_roles_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_role_to_user_idx` (`role_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_to_role`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet-shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_to_user`
    FOREIGN KEY (`role_id`)
    REFERENCES `internet-shop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet-shop`.`orders_products` (
  `order_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `fk_order_to_product_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_product_to_order_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_to_product`
    FOREIGN KEY (`order_id`)
    REFERENCES `internet-shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_to_order`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet-shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `internet-shop`.`shopping_carts_products` (
  `cart_id` BIGINT(11) NOT NULL,
  `product_id` BIGINT(11) NOT NULL,
  INDEX `fk_shopping_carts_to_products_idx` (`cart_id` ASC) VISIBLE,
  INDEX `fk_product_to_cart_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_cart_to_product`
    FOREIGN KEY (`cart_id`)
    REFERENCES `internet-shop`.`shopping_carts` (`cart_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_product_to_cart`
    FOREIGN KEY (`product_id`)
    REFERENCES `internet-shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
