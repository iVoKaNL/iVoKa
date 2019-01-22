-- MySQL Script generated by MySQL Workbench
-- Tue Jan 22 14:31:04 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ivoka
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ivoka
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ivoka` DEFAULT CHARACTER SET utf8 ;
USE `ivoka` ;

-- -----------------------------------------------------
-- Table `ivoka`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ivoka`.`User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_email` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(128) NOT NULL,
  `user_firstName` VARCHAR(45) NOT NULL,
  `user_lastName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;