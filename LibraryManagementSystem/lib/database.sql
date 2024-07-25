CREATE DATABASE library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS `Roles` (
    `role_id` ENUM('admin', 'librarian', 'user') NOT NULL,
    PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50) UNIQUE NOT NULL,
    `email` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role_id` ENUM('admin', 'librarian', 'user') NOT NULL,
    CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `Roles`(`role_id`)
);

CREATE TABLE IF NOT EXISTS `Books` (
    `book_id` INT AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(100) NOT NULL,
    `author` VARCHAR(100) NOT NULL,
    `genre` VARCHAR(100) NOT NULL,
    `status` BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS `Borrowed_books` (
    `book_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `issue_date` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `return_date` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT `fk_book_id` FOREIGN KEY (`book_id`) REFERENCES `Books`(`book_id`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
    PRIMARY KEY (`book_id`, `user_id`)
);

CREATE TABLE IF NOT EXISTS `Fines` (
    `fine_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `fine_amount` DECIMAL(10, 2) NOT NULL,
    `paid` BOOLEAN NOT NULL,
    CONSTRAINT `fk_user_id_fines` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);
