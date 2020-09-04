-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema AutoPartsShop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema AutoPartsShop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `AutoPartsShop` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `AutoPartsShop` ;

-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Country` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Abbreviation` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`City`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`City` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Postcode` VARCHAR(45) NOT NULL,
  `Country_ID` INT NOT NULL,
  `IsActive` TINYINT NULL DEFAULT 1,
  INDEX `fk_City_Country1_idx` (`Country_ID` ASC) VISIBLE,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `Postcode_UNIQUE` (`Postcode` ASC) VISIBLE,
  CONSTRAINT `fk_City_Country1`
    FOREIGN KEY (`Country_ID`)
    REFERENCES `AutoPartsShop`.`Country` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Manufacturer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Manufacturer` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Description` TEXT NULL,
  `Headquarters` VARCHAR(100) NULL,
  `City_ID` INT NOT NULL,
  `IsActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  INDEX `fk_Manufacturer_City1_idx` (`City_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Manufacturer_City1`
    FOREIGN KEY (`City_ID`)
    REFERENCES `AutoPartsShop`.`City` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Category` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Product` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Quantity` DECIMAL(10,2) UNSIGNED NOT NULL,
  `Barcode` VARCHAR(45) NOT NULL,
  `Price` DECIMAL(10,2) NOT NULL,
  `Description` TEXT NULL,
  `Manufacturer_ID` INT NOT NULL,
  `Category_ID` INT NOT NULL,
  `IsActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  INDEX `fk_Item_Manufacturer_idx` (`Manufacturer_ID` ASC) VISIBLE,
  INDEX `fk_Item_Category1_idx` (`Category_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Item_Manufacturer`
    FOREIGN KEY (`Manufacturer_ID`)
    REFERENCES `AutoPartsShop`.`Manufacturer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Item_Category1`
    FOREIGN KEY (`Category_ID`)
    REFERENCES `AutoPartsShop`.`Category` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Vehicle` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Model` VARCHAR(45) NOT NULL,
  `ProductionStart` DATETIME NOT NULL,
  `ProductionEnd` DATETIME NULL,
  `Manufacturer_ID` INT NOT NULL,
  `Engine` VARCHAR(45) NULL,
  `IsActive` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  INDEX `fk_Vehicle_Manufacturer1_idx` (`Manufacturer_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Vehicle_Manufacturer1`
    FOREIGN KEY (`Manufacturer_ID`)
    REFERENCES `AutoPartsShop`.`Manufacturer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Vehicle_Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Vehicle_Product` (
  `Vehicle_ID` INT NOT NULL,
  `Product_ID` INT NOT NULL,
  `IsActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`Product_ID`, `Vehicle_ID`),
  INDEX `fk_Vehicle_has_Item_Item1_idx` (`Product_ID` ASC) VISIBLE,
  INDEX `fk_Vehicle_has_Item_Vehicle1_idx` (`Vehicle_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Vehicle_has_Item_Vehicle1`
    FOREIGN KEY (`Vehicle_ID`)
    REFERENCES `AutoPartsShop`.`Vehicle` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Vehicle_has_Item_Item1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `AutoPartsShop`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Tax`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Tax` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Percentage` DECIMAL(4,2) NOT NULL,
  `From` DATETIME NOT NULL,
  `To` DATETIME NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`User` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `City_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_User_City1_idx` (`City_ID` ASC) VISIBLE,
  CONSTRAINT `fk_User_City1`
    FOREIGN KEY (`City_ID`)
    REFERENCES `AutoPartsShop`.`City` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Bank` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`User_Bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`User_Bank` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `AccountNumber` VARCHAR(45) NOT NULL,
  `User_ID` INT NOT NULL,
  `Bank_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_User_Bank_User1_idx` (`User_ID` ASC) VISIBLE,
  INDEX `fk_User_Bank_Bank1_idx` (`Bank_ID` ASC) VISIBLE,
  CONSTRAINT `fk_User_Bank_User1`
    FOREIGN KEY (`User_ID`)
    REFERENCES `AutoPartsShop`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Bank_Bank1`
    FOREIGN KEY (`Bank_ID`)
    REFERENCES `AutoPartsShop`.`Bank` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Bill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Bill` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Tax_ID` INT NOT NULL,
  `Time` DATETIME NOT NULL,
  `Discount` DECIMAL(4,2) NULL,
  `User_Bank_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Bill_Tax1_idx` (`Tax_ID` ASC) VISIBLE,
  INDEX `fk_Bill_User_Bank1_idx` (`User_Bank_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Bill_Tax1`
    FOREIGN KEY (`Tax_ID`)
    REFERENCES `AutoPartsShop`.`Tax` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bill_User_Bank1`
    FOREIGN KEY (`User_Bank_ID`)
    REFERENCES `AutoPartsShop`.`User_Bank` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Bill_Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Bill_Product` (
  `Quantity` DECIMAL(10,2) NOT NULL,
  `Bill_ID` INT NOT NULL,
  `Product_ID` INT NOT NULL,
  PRIMARY KEY (`Bill_ID`, `Product_ID`),
  INDEX `fk_Bill_PriceProduct_Bill1_idx` (`Bill_ID` ASC) VISIBLE,
  INDEX `fk_Bill_Product_Product1_idx` (`Product_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Bill_PriceProduct_Bill1`
    FOREIGN KEY (`Bill_ID`)
    REFERENCES `AutoPartsShop`.`Bill` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bill_Product_Product1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `AutoPartsShop`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Customer` (
  `ID` INT NOT NULL,
  `Discount` DECIMAL(4,2) NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Customer_Account1_idx` (`ID` ASC) VISIBLE,
  CONSTRAINT `fk_Customer_Account1`
    FOREIGN KEY (`ID`)
    REFERENCES `AutoPartsShop`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Review` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Description` TEXT NOT NULL,
  `Product_ID` INT NOT NULL,
  `Customer_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Review_Product1_idx` (`Product_ID` ASC) VISIBLE,
  INDEX `fk_Review_Customer1_idx` (`Customer_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Review_Product1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `AutoPartsShop`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Review_Customer1`
    FOREIGN KEY (`Customer_ID`)
    REFERENCES `AutoPartsShop`.`Customer` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Employee` (
  `ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Employee_Account1_idx` (`ID` ASC) VISIBLE,
  CONSTRAINT `fk_Employee_Account1`
    FOREIGN KEY (`ID`)
    REFERENCES `AutoPartsShop`.`User` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Role` (
  `ID` INT NOT NULL,
  `Name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Contract`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Contract` (
  `From` DATETIME NOT NULL,
  `To` DATETIME NULL,
  `Description` TEXT NULL,
  `Salary` DECIMAL(10,2) NOT NULL,
  `Role_ID` INT NOT NULL,
  `Employee_ID` INT NOT NULL,
  `User_Bank_ID` INT NOT NULL,
  PRIMARY KEY (`Role_ID`, `Employee_ID`, `From`),
  INDEX `fk_Contract_Role1_idx` (`Role_ID` ASC) VISIBLE,
  INDEX `fk_Contract_Employee1_idx` (`Employee_ID` ASC) VISIBLE,
  INDEX `fk_Contract_User_Bank1_idx` (`User_Bank_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Contract_Role1`
    FOREIGN KEY (`Role_ID`)
    REFERENCES `AutoPartsShop`.`Role` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contract_Employee1`
    FOREIGN KEY (`Employee_ID`)
    REFERENCES `AutoPartsShop`.`Employee` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Contract_User_Bank1`
    FOREIGN KEY (`User_Bank_ID`)
    REFERENCES `AutoPartsShop`.`User_Bank` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Supplier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Supplier` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NOT NULL,
  `isActive` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Supplier_Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Supplier_Product` (
  `Supplier_ID` INT NOT NULL,
  `Product_ID` INT NOT NULL,
  PRIMARY KEY (`Supplier_ID`, `Product_ID`),
  INDEX `fk_Supplier_has_Product_Product1_idx` (`Product_ID` ASC) VISIBLE,
  INDEX `fk_Supplier_has_Product_Supplier1_idx` (`Supplier_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Supplier_has_Product_Supplier1`
    FOREIGN KEY (`Supplier_ID`)
    REFERENCES `AutoPartsShop`.`Supplier` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Supplier_has_Product_Product1`
    FOREIGN KEY (`Product_ID`)
    REFERENCES `AutoPartsShop`.`Product` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Supplier_City`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Supplier_City` (
  `Supplier_ID` INT NOT NULL,
  `City_ID` INT NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `IsActive` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`Supplier_ID`, `City_ID`),
  INDEX `fk_Supplier_has_City_Supplier1_idx` (`Supplier_ID` ASC) VISIBLE,
  INDEX `fk_Supplier_City_City1_idx` (`City_ID` ASC) VISIBLE,
  CONSTRAINT `fk_Supplier_has_City_Supplier1`
    FOREIGN KEY (`Supplier_ID`)
    REFERENCES `AutoPartsShop`.`Supplier` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Supplier_City_City1`
    FOREIGN KEY (`City_ID`)
    REFERENCES `AutoPartsShop`.`City` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `AutoPartsShop`.`Bank_City`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `AutoPartsShop`.`Bank_City` (
  `Bank_ID` INT NOT NULL,
  `City_ID` INT NOT NULL,
  PRIMARY KEY (`Bank_ID`, `City_ID`),
  INDEX `fk_City_has_Bank_Bank1_idx` (`Bank_ID` ASC) VISIBLE,
  INDEX `fk_Bank_City_City1_idx` (`City_ID` ASC) VISIBLE,
  CONSTRAINT `fk_City_has_Bank_Bank1`
    FOREIGN KEY (`Bank_ID`)
    REFERENCES `AutoPartsShop`.`Bank` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Bank_City_City1`
    FOREIGN KEY (`City_ID`)
    REFERENCES `AutoPartsShop`.`City` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
