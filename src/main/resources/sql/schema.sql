CREATE SCHEMA IF NOT EXISTS `task_manager`;

USE `task_manager`;

CREATE TABLE IF NOT EXISTS `tasks` (
	`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `status` VARCHAR(255) NOT NULL,
    `priority` VARCHAR(255) NOT NULL,
    `due_date` DATETIME NOT NULL,
);