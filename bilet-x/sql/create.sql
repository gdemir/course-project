SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `bilet_x` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `bilet_x` ;

-- -----------------------------------------------------
-- Table `bilet_x`.`KATEGORI`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`KATEGORI` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`KATEGORI` (
  `kategori_id` INT NOT NULL AUTO_INCREMENT ,
  `ad` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NULL ,
  PRIMARY KEY (`kategori_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`ETKINLIK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`ETKINLIK` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`ETKINLIK` (
  `etkinlik_id` INT NOT NULL AUTO_INCREMENT ,
  `ad` VARCHAR(128) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `durum` INT NOT NULL ,
  `kategori_id` INT NOT NULL ,
  PRIMARY KEY (`etkinlik_id`) ,
  INDEX `kategori_id` (`kategori_id` ASC) ,
  CONSTRAINT `kategori_id`
    FOREIGN KEY (`kategori_id` )
    REFERENCES `bilet_x`.`KATEGORI` (`kategori_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`IL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`IL` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`IL` (
  `il_id` INT NOT NULL ,
  `ad` VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  PRIMARY KEY (`il_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`YER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`YER` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`YER` (
  `yer_id` INT NOT NULL AUTO_INCREMENT ,
  `etkinlik_id` INT NOT NULL ,
  `il_id` INT NOT NULL ,
  PRIMARY KEY (`yer_id`) ,
  INDEX `ililce_id` (`il_id` ASC) ,
  INDEX `etkinlik_id` (`etkinlik_id` ASC) ,
  CONSTRAINT `ililce_id`
    FOREIGN KEY (`il_id` )
    REFERENCES `bilet_x`.`IL` (`il_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `etkinlik_id`
    FOREIGN KEY (`etkinlik_id` )
    REFERENCES `bilet_x`.`ETKINLIK` (`etkinlik_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`SALON`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`SALON` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`SALON` (
  `salon_id` INT NOT NULL AUTO_INCREMENT ,
  `ad` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `kapasite` INT NOT NULL ,
  `il_id` INT NOT NULL ,
  PRIMARY KEY (`salon_id`) ,
  INDEX `ililce_id` (`il_id` ASC) ,
  CONSTRAINT `ililce_id`
    FOREIGN KEY (`il_id` )
    REFERENCES `bilet_x`.`IL` (`il_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`KOLTUK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`KOLTUK` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`KOLTUK` (
  `koltuk_id` INT NOT NULL AUTO_INCREMENT ,
  `ad` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `salon_id` INT NOT NULL ,
  PRIMARY KEY (`koltuk_id`) ,
  INDEX `salon_id` (`salon_id` ASC) ,
  CONSTRAINT `salon_id`
    FOREIGN KEY (`salon_id` )
    REFERENCES `bilet_x`.`SALON` (`salon_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`UCRET`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`UCRET` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`UCRET` (
  `ucret_id` INT NOT NULL AUTO_INCREMENT ,
  `ad` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `fiyat` INT NOT NULL ,
  PRIMARY KEY (`ucret_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`MEMBER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`MEMBER` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`MEMBER` (
  `member_id` INT NOT NULL AUTO_INCREMENT ,
  `il_id` INT NOT NULL ,
  `ad` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `soyad` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `email` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `username` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `password` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `telefon` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `kredikart` INT NOT NULL ,
  `tarih` DATE NOT NULL ,
  PRIMARY KEY (`member_id`) ,
  INDEX `ililce_id` (`il_id` ASC) ,
  CONSTRAINT `ililce_id`
    FOREIGN KEY (`il_id` )
    REFERENCES `bilet_x`.`IL` (`il_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`REZERVE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`REZERVE` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`REZERVE` (
  `rezerve_id` INT NOT NULL AUTO_INCREMENT ,
  `tarih` DATE NOT NULL ,
  `etkinlik_id` INT NOT NULL ,
  `yer_id` INT NOT NULL ,
  `salon_id` INT NOT NULL ,
  `koltuk_id` INT NOT NULL ,
  `ucret_id` INT NOT NULL ,
  `member_id` INT NOT NULL ,
  PRIMARY KEY (`rezerve_id`) ,
  INDEX `etkinlik_id` (`etkinlik_id` ASC) ,
  INDEX `yer_id` (`yer_id` ASC) ,
  INDEX `salon_id` (`salon_id` ASC) ,
  INDEX `koltuk_id` (`koltuk_id` ASC) ,
  INDEX `ucret_id` (`ucret_id` ASC) ,
  INDEX `member_id` (`member_id` ASC) ,
  CONSTRAINT `etkinlik_id`
    FOREIGN KEY (`etkinlik_id` )
    REFERENCES `bilet_x`.`ETKINLIK` (`etkinlik_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `yer_id`
    FOREIGN KEY (`yer_id` )
    REFERENCES `bilet_x`.`YER` (`yer_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `salon_id`
    FOREIGN KEY (`salon_id` )
    REFERENCES `bilet_x`.`SALON` (`salon_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `koltuk_id`
    FOREIGN KEY (`koltuk_id` )
    REFERENCES `bilet_x`.`KOLTUK` (`koltuk_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ucret_id`
    FOREIGN KEY (`ucret_id` )
    REFERENCES `bilet_x`.`UCRET` (`ucret_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `member_id`
    FOREIGN KEY (`member_id` )
    REFERENCES `bilet_x`.`MEMBER` (`member_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`ADMIN`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`ADMIN` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`ADMIN` (
  `admin_id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `password` VARCHAR(60) CHARACTER SET 'utf8' COLLATE 'utf8_general_ci' NOT NULL ,
  `tarih` DATE NOT NULL ,
  `super` INT NOT NULL ,
  PRIMARY KEY (`admin_id`) )
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `bilet_x`.`TARIH`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `bilet_x`.`TARIH` ;

CREATE  TABLE IF NOT EXISTS `bilet_x`.`TARIH` (
  `tarih_id` INT NOT NULL AUTO_INCREMENT ,
  `etkinlik_id` INT NOT NULL ,
  `tarih` DATE NOT NULL ,
  `saat` TIME NOT NULL ,
  PRIMARY KEY (`tarih_id`) ,
  INDEX `etkinlik_id` (`etkinlik_id` ASC) ,
  CONSTRAINT `etkinlik_id`
    FOREIGN KEY (`etkinlik_id` )
    REFERENCES `bilet_x`.`ETKINLIK` (`etkinlik_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = MyISAM
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
