-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema matching
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema matching
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `matching` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `matching` ;

-- -----------------------------------------------------
-- Table `matching`.`files`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`files` (
  `id` VARCHAR(255) NOT NULL,
  `data` LONGBLOB NULL DEFAULT NULL,
  `file_name` VARCHAR(255) NULL DEFAULT NULL,
  `file_type` VARCHAR(255) NULL DEFAULT NULL,
  `download_uri` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `authority` VARCHAR(20) NOT NULL,
  `banned` BIT(1) NULL DEFAULT NULL,
  `bio` VARCHAR(1000) NULL DEFAULT NULL,
  `city` VARCHAR(10) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `is_active` BIT(1) NOT NULL,
  `name` VARCHAR(8) NOT NULL,
  `nickname` VARCHAR(10) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `position` VARCHAR(20) NULL DEFAULT NULL,
  `tel` VARCHAR(255) NULL DEFAULT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `portfolio_uri` VARCHAR(1000) NULL DEFAULT NULL,
  `portfolio_uuid` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_member_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_member_files2_idx` (`portfolio_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_member_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_member_files2`
    FOREIGN KEY (`portfolio_uuid`)
    REFERENCES `matching`.`files` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 46
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`club`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `bio` VARCHAR(1000) NULL DEFAULT NULL,
  `city` VARCHAR(10) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `is_active` BIT(1) NOT NULL,
  `is_public` BIT(1) NOT NULL,
  `max_count` INT NOT NULL,
  `member_count` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `topic` VARCHAR(45) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `host_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_club_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_club_member1_idx` (`host_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_club_member1`
    FOREIGN KEY (`host_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`club_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `club_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_club_board_club1_idx` (`club_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_board_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `matching`.`club` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`career`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`career` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `company` VARCHAR(255) NULL DEFAULT NULL,
  `department` VARCHAR(255) NULL DEFAULT NULL,
  `description` VARCHAR(255) NULL DEFAULT NULL,
  `end_date` DATETIME(6) NULL DEFAULT NULL,
  `start_date` DATETIME(6) NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKj3sr8mqtm4j9hh3cdk9514iuy` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FKj3sr8mqtm4j9hh3cdk9514iuy`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`certification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`certification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(255) NULL DEFAULT NULL,
  `expired_date` DATETIME(6) NULL DEFAULT NULL,
  `grade` VARCHAR(255) NULL DEFAULT NULL,
  `issued_date` DATETIME(6) NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `organization` VARCHAR(255) NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKean8y31fu1keq8505jsdy4ts7` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FKean8y31fu1keq8505jsdy4ts7`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`club_application_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_application_form` (
  `nickname` VARCHAR(10) NOT NULL,
  `city` VARCHAR(10) NOT NULL,
  `git` VARCHAR(145) NULL DEFAULT NULL,
  `twitter` VARCHAR(145) NULL DEFAULT NULL,
  `facebook` VARCHAR(145) NULL DEFAULT NULL,
  `backjoon` VARCHAR(145) NULL DEFAULT NULL,
  `bio` VARCHAR(145) NULL DEFAULT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `club_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`club_id`, `member_id`),
  INDEX `fk_club_application_form_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_club_application_form_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_application_form_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `matching`.`club` (`id`),
  CONSTRAINT `fk_club_application_form_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_club_application_form_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`education`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`education` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `degree` VARCHAR(255) NULL DEFAULT NULL,
  `end_date` DATETIME(6) NULL DEFAULT NULL,
  `full_credit` FLOAT NOT NULL,
  `institution` VARCHAR(255) NULL DEFAULT NULL,
  `major` VARCHAR(255) NULL DEFAULT NULL,
  `my_credit` FLOAT NOT NULL,
  `start_date` DATETIME(6) NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKcwpq7xrd9cidl8hxgepm1cju8` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FKcwpq7xrd9cidl8hxgepm1cju8`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`techstack` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_beginner_techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_beginner_techstack` (
  `member_id` BIGINT NOT NULL,
  `techstack_id` INT NOT NULL,
  PRIMARY KEY (`member_id`, `techstack_id`),
  INDEX `FKhiwqgp87b3o133ipcrwvmtred` (`techstack_id` ASC) VISIBLE,
  CONSTRAINT `FKhiwqgp87b3o133ipcrwvmtred`
    FOREIGN KEY (`techstack_id`)
    REFERENCES `matching`.`techstack` (`id`),
  CONSTRAINT `FKqkqcrqsbs4ir3m652e6ee7rpq`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_club`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_club` (
  `is_active` BIT(1) NOT NULL,
  `register_date` DATETIME(6) NULL DEFAULT NULL,
  `member_id` BIGINT NOT NULL,
  `club_id` BIGINT NOT NULL,
  PRIMARY KEY (`member_id`, `club_id`),
  INDEX `fk_member_club_club1_idx` (`club_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_club_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `matching`.`club` (`id`),
  CONSTRAINT `fk_member_club_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_experienced_techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_experienced_techstack` (
  `techstack_id` INT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`techstack_id`, `member_id`),
  INDEX `fk_member_experienced_techstack_techstack1_idx` (`techstack_id` ASC) VISIBLE,
  INDEX `fk_member_experienced_techstack_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_experienced_techstack_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_member_experienced_techstack_techstack1`
    FOREIGN KEY (`techstack_id`)
    REFERENCES `matching`.`techstack` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `bio` VARCHAR(1000) NULL DEFAULT NULL,
  `city` VARCHAR(10) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `designer_count` INT NOT NULL,
  `designer_max_count` INT NOT NULL,
  `developer_count` INT NOT NULL,
  `developer_max_count` INT NOT NULL,
  `host_role` VARCHAR(20) NULL DEFAULT NULL,
  `is_active` BIT(1) NOT NULL,
  `is_participate` BIT(1) NOT NULL,
  `is_public` BIT(1) NOT NULL,
  `modify_date` DATETIME(6) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `period` INT NULL DEFAULT NULL,
  `planner_count` INT NOT NULL,
  `planner_max_count` INT NOT NULL,
  `schedule` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(10) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `club_id` BIGINT NULL DEFAULT NULL,
  `host_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_project_club1_idx` (`club_id` ASC) VISIBLE,
  INDEX `fk_project_member1_idx` (`host_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `matching`.`club` (`id`),
  CONSTRAINT `fk_project_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_project_member1`
    FOREIGN KEY (`host_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_project` (
  `is_active` BIT(1) NOT NULL,
  `register_date` DATETIME(6) NOT NULL,
  `role` VARCHAR(20) NULL DEFAULT NULL,
  `member_id` BIGINT NOT NULL,
  `project_id` BIGINT NOT NULL,
  PRIMARY KEY (`member_id`, `project_id`),
  INDEX `fk_member_project_project1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_project_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_member_project_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `matching`.`project` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_sns`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_sns` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `sns_account` VARCHAR(255) NOT NULL,
  `sns_name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_member_sns_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_sns_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`study`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `bio` VARCHAR(1000) NULL DEFAULT NULL,
  `city` VARCHAR(10) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `is_active` BIT(1) NOT NULL,
  `is_participate` BIT(1) NOT NULL,
  `is_public` BIT(1) NOT NULL,
  `max_count` INT NOT NULL,
  `member_count` INT NOT NULL,
  `modify_date` DATETIME(6) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `period` INT NULL DEFAULT NULL,
  `schedule` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(15) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `host_id` BIGINT NULL DEFAULT NULL,
  `club_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_study_member1_idx` (`host_id` ASC) VISIBLE,
  INDEX `fk_study_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_study_club1_idx` (`club_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `matching`.`club` (`id`),
  CONSTRAINT `fk_study_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_study_member1`
    FOREIGN KEY (`host_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_study`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_study` (
  `is_active` BIT(1) NOT NULL,
  `register_date` DATETIME(6) NOT NULL,
  `study_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`study_id`, `member_id`),
  INDEX `fk_member_study_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_member_study_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_member_study_study1`
    FOREIGN KEY (`study_id`)
    REFERENCES `matching`.`study` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`member_techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_techstack` (
  `member_id` BIGINT NOT NULL,
  `techstack_id` INT NOT NULL,
  PRIMARY KEY (`member_id`, `techstack_id`),
  INDEX `FKt9pc24oxcu8o9129k92jt7qg7` (`techstack_id` ASC) VISIBLE,
  CONSTRAINT `FK4e6643xhjlp6k401w5aac3itk`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `FKt9pc24oxcu8o9129k92jt7qg7`
    FOREIGN KEY (`techstack_id`)
    REFERENCES `matching`.`techstack` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` BIGINT NOT NULL,
  `receiver_id` BIGINT NOT NULL,
  `sent_time` TIMESTAMP NULL DEFAULT NULL,
  `read_time` TIMESTAMP NULL DEFAULT NULL,
  `content` TEXT NULL DEFAULT NULL,
  `type` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_member1_idx` (`sender_id` ASC) VISIBLE,
  INDEX `fk_message_member2_idx` (`receiver_id` ASC) VISIBLE,
  CONSTRAINT `fk_message_member1`
    FOREIGN KEY (`sender_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_message_member2`
    FOREIGN KEY (`receiver_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`position` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `member_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKjcl2rsr8itxg4548wlcvxr5yo` (`member_id` ASC) VISIBLE,
  CONSTRAINT `FKjcl2rsr8itxg4548wlcvxr5yo`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`project_application_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_application_form` (
  `project_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `nickname` VARCHAR(10) NOT NULL,
  `city` VARCHAR(10) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  `position` VARCHAR(20) NOT NULL,
  `git` VARCHAR(145) NULL DEFAULT NULL,
  `twitter` VARCHAR(145) NULL DEFAULT NULL,
  `facebook` VARCHAR(145) NULL DEFAULT NULL,
  `backjoon` VARCHAR(145) NULL DEFAULT NULL,
  `bio` VARCHAR(145) NULL DEFAULT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`project_id`, `member_id`),
  INDEX `fk_project_application_form_project1_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_project_application_form_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_project_application_form_files1_idx` (`cover_pic` ASC) VISIBLE,
  CONSTRAINT `fk_project_application_form_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_project_application_form_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_project_application_form_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `matching`.`project` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`project_techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_techstack` (
  `project_id` BIGINT NOT NULL,
  `techstack_id` INT NOT NULL,
  PRIMARY KEY (`project_id`, `techstack_id`),
  INDEX `fk_project_techstack_project1_idx` (`project_id` ASC) VISIBLE,
  INDEX `fk_project_techstack_techstack1_idx` (`techstack_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_techstack_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `matching`.`project` (`id`),
  CONSTRAINT `fk_project_techstack_techstack1`
    FOREIGN KEY (`techstack_id`)
    REFERENCES `matching`.`techstack` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`refresh_token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`refresh_token` (
  `member_id` VARCHAR(255) NOT NULL,
  `value` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`member_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`study_application_form`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_application_form` (
  `nickname` VARCHAR(10) NOT NULL,
  `city` VARCHAR(10) NOT NULL,
  `git` VARCHAR(145) NULL DEFAULT NULL,
  `twitter` VARCHAR(145) NULL DEFAULT NULL,
  `facebook` VARCHAR(145) NULL DEFAULT NULL,
  `backjoon` VARCHAR(145) NULL DEFAULT NULL,
  `bio` VARCHAR(145) NULL DEFAULT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `cover_pic` VARCHAR(255) NULL DEFAULT NULL,
  `study_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  PRIMARY KEY (`study_id`, `member_id`),
  INDEX `fk_study_application_form_files1_idx` (`cover_pic` ASC) VISIBLE,
  INDEX `fk_study_application_form_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_application_form_files1`
    FOREIGN KEY (`cover_pic`)
    REFERENCES `matching`.`files` (`id`),
  CONSTRAINT `fk_study_application_form_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`),
  CONSTRAINT `fk_study_application_form_study1`
    FOREIGN KEY (`study_id`)
    REFERENCES `matching`.`study` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`study_techstack`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_techstack` (
  `study_id` BIGINT NOT NULL,
  `techstack_id` INT NOT NULL,
  PRIMARY KEY (`study_id`, `techstack_id`),
  INDEX `fk_study_techstack_techstack1_idx` (`techstack_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_techstack_study1`
    FOREIGN KEY (`study_id`)
    REFERENCES `matching`.`study` (`id`),
  CONSTRAINT `fk_study_techstack_techstack1`
    FOREIGN KEY (`techstack_id`)
    REFERENCES `matching`.`techstack` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `matching`.`club_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `club_board_id` INT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `view_count` INT NULL,
  INDEX `fk_club_article_club_board1_idx` (`club_board_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  INDEX `fk_club_article_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_article_club_board1`
    FOREIGN KEY (`club_board_id`)
    REFERENCES `matching`.`club_board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_club_article_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`club_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `club_article_id` BIGINT NOT NULL,
  `content` MEDIUMTEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_club_content_club_article1_idx` (`club_article_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_content_club_article1`
    FOREIGN KEY (`club_article_id`)
    REFERENCES `matching`.`club_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`club_article_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_article_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `club_article_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_club_article_tag_club_article1_idx` (`club_article_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_article_tag_club_article1`
    FOREIGN KEY (`club_article_id`)
    REFERENCES `matching`.`club_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`club_article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`club_article_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `member_id` BIGINT NOT NULL,
  `club_article_id` BIGINT NOT NULL,
  `depth` INT NOT NULL,
  `parent_id` BIGINT NULL,
  `is_modified` BIT(1) NOT NULL,
  `is_deleted` BIT(1) NOT NULL,
  `reply_count` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_club_article_comment_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_club_article_comment_club_article1_idx` (`club_article_id` ASC) VISIBLE,
  CONSTRAINT `fk_club_article_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_club_article_comment_club_article1`
    FOREIGN KEY (`club_article_id`)
    REFERENCES `matching`.`club_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`study_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `study_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  INDEX `fk_study_board_study1_idx` (`study_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_study_board_study1`
    FOREIGN KEY (`study_id`)
    REFERENCES `matching`.`study` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`study_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `study_board_id` INT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `view_count` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_study_article_study_board1_idx` (`study_board_id` ASC) VISIBLE,
  INDEX `fk_study_article_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_article_study_board1`
    FOREIGN KEY (`study_board_id`)
    REFERENCES `matching`.`study_board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_study_article_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`study_article_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_article_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `study_article_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_study_article_tag_study_article1_idx` (`study_article_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_article_tag_study_article1`
    FOREIGN KEY (`study_article_id`)
    REFERENCES `matching`.`study_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`study_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `study_article_id` BIGINT NOT NULL,
  `content` MEDIUMTEXT NOT NULL,
  INDEX `fk_study_content_study_article1_idx` (`study_article_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_study_content_study_article1`
    FOREIGN KEY (`study_article_id`)
    REFERENCES `matching`.`study_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`study_article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`study_article_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `depth` INT NOT NULL,
  `parent_id` BIGINT NULL,
  `is_modified` BIT(1) NOT NULL,
  `is_deleted` BIT(1) NOT NULL,
  `study_article_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `reply_count` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_study_article_comment_study_article1_idx` (`study_article_id` ASC) VISIBLE,
  INDEX `fk_study_article_comment_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_study_article_comment_study_article1`
    FOREIGN KEY (`study_article_id`)
    REFERENCES `matching`.`study_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_study_article_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`project_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `project_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_board_project1_idx` (`project_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_board_project1`
    FOREIGN KEY (`project_id`)
    REFERENCES `matching`.`project` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`project_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `project_board_id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `view_count` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_article_member1_idx` (`member_id` ASC) VISIBLE,
  INDEX `fk_project_article_project_board1_idx` (`project_board_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_article_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_article_project_board1`
    FOREIGN KEY (`project_board_id`)
    REFERENCES `matching`.`project_board` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`project_content`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_content` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_article_id` BIGINT NOT NULL,
  `content` MEDIUMTEXT NOT NULL,
  INDEX `fk_project_content_project_article1_idx` (`project_article_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_project_content_project_article1`
    FOREIGN KEY (`project_article_id`)
    REFERENCES `matching`.`project_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`project_article_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_article_comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `create_date` DATETIME(6) NOT NULL,
  `modified_date` DATETIME(6) NULL,
  `depth` INT NOT NULL,
  `parent_id` BIGINT NULL,
  `is_modified` BIT(1) NOT NULL,
  `is_deleted` BIT(1) NOT NULL,
  `project_article_id` BIGINT NOT NULL,
  `member_id` BIGINT NOT NULL,
  `reply_count` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_project_article_comment_project_article1_idx` (`project_article_id` ASC) VISIBLE,
  INDEX `fk_project_article_comment_member1_idx` (`member_id` ASC) VISIBLE,
  CONSTRAINT `fk_project_article_comment_project_article1`
    FOREIGN KEY (`project_article_id`)
    REFERENCES `matching`.`project_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_article_comment_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`member_portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`member_portfolio` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `member_id` BIGINT NOT NULL,
  `portfolio_path` VARCHAR(255) NOT NULL,
  INDEX `fk_member_portfolio_member1_idx` (`member_id` ASC) VISIBLE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_member_portfolio_member1`
    FOREIGN KEY (`member_id`)
    REFERENCES `matching`.`member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `matching`.`project_article_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `matching`.`project_article_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `project_article_id` BIGINT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tag_project_article1_idx` (`project_article_id` ASC) VISIBLE,
  CONSTRAINT `fk_tag_project_article1`
    FOREIGN KEY (`project_article_id`)
    REFERENCES `matching`.`project_article` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1, "ionicons");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2, "material-ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3, "ant design");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (4, "devextreme");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (5, "elementui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (6, "chakra ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (7, "feathers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (8, "md bootstrap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (9, "open iconic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (10, "react rainbow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (11, "material components...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (12, "glimmer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (13, "jqwidgets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (14, "xen orchestra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (15, "telerik ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (16, "mobiscroll");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (17, "blade ui kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (18, "oracle jet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (19, "lightning web compo...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (20, "headless ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (21, "bootstrap styled");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (22, "shoelace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (23, "mat-ui-icons");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (24, "react toolbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (25, "radix primitives");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (26, "supabase ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (27, "orbtk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (28, "react spectrum");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (29, "matestack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (30, "jsx lite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (31, "material tailwind");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (32, "powershell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (33, "gnu bash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (34, "zsh (z shell)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (35, "ipython");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (36, "fish shell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (37, "cicada");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (38, "nyagos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (39, "nu shell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (40, "runops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (41, "unity");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (42, "three.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (43, "unreal engine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (44, "godot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (45, "phaser");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (46, "cocos2d-x");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (47, "pygame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (48, "babylonjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (49, "libgdx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (50, "monogame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (51, "playcanvas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (52, "gamesparks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (53, "ninject");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (54, "gamemaker studio 2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (55, "corona sdk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (56, "buildbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (57, "steamworks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (58, "panda3d");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (59, "crown");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (60, "gnome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (61, "react-three-fiber");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (62, "blend4web");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (63, "codecks.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (64, "felgo game engine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (65, "amethyst");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (66, "gb studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (67, "appgamekit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (68, "ggez");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (69, "amazon lumberyard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (70, "akka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (71, "rxjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (72, "netty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (73, "finagle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (74, "orleans");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (75, "redux observable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (76, "tokio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (77, "protoactor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (78, "zio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (79, "gpars");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (80, "highland.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (81, "scramjet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (82, "bloomrpc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (83, "protobuf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (84, "apache thrift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (85, "avro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (86, "messagepack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (87, "serde");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (88, "react storybook");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (89, "primeng");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (90, "laravel nova");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (91, "linqpad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (92, "laravel voyager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (93, "reactotron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (94, "structor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (95, "divjoy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (96, "react monocle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (97, "vue-model");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (98, "react proto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (99, "shuffle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (100, "rekit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (101, "react cosmos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (102, "react sight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (103, "material-ui builder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (104, "react toolbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (105, "laravel kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (106, "laravel breeze");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (107, "bootstrap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (108, "animate.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (109, "material design for...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (110, "foundation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (111, "material-ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (112, "nuxt.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (113, "semantic ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (114, "vuetify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (115, "tailwind css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (116, "uikit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (117, "material design lite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (118, "materialize");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (119, "bulma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (120, "dojo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (121, "material design");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (122, "polymer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (123, "vue cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (124, "quasar framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (125, "angular universal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (126, "bootstrap vue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (127, "flat ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (128, "bem");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (129, "primefaces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (130, "material design for...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (131, "gentelella");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (132, "kube");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (133, "reactstrap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (134, "skeleton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (135, "web components");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (136, "ring ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (137, "mini.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (138, "foundation for apps");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (139, "pure");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (140, "tachyons");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (141, "milligram");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (142, "material kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (143, "clarity design system");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (144, "toolkit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (145, "avalonia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (146, "pattern lab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (147, "nimble");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (148, "suit css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (149, "tailwind starter kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (150, "iview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (151, "susy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (152, "flakes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (153, "primer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (154, "fluxxor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (155, "gumby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (156, "basscss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (157, "water.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (158, "flexible.gs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (159, "bootsfaces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (160, "lit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (161, "swiftwebui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (162, "new.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (163, "lightning web compo...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (164, "topcoat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (165, "mozilla brick");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (166, "miso");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (167, "sauron (framework)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (168, "simple grid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (169, "axentix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (170, "spectre.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (171, "bosonic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (172, "udash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (173, "bootstrap styled");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (174, "microsoft fast design");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (175, "cirrus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (176, "systemflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (177, "charts.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (178, "shmenu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (179, "ubuntu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (180, "debian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (181, "centos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (182, "linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (183, "ios");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (184, "windows");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (185, "macos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (186, "android os");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (187, "windows server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (188, "arch linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (189, "windows 10");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (190, "fedora");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (191, "alpine linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (192, "red hat enterprise ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (193, "coreos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (194, "freebsd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (195, "linux mint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (196, "mac os x");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (197, "amazon linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (198, "kali linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (199, "manjaro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (200, "opensuse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (201, "raspbian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (202, "rancheros");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (203, "oracle linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (204, "linux kernel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (205, "nixos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (206, "gentoo linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (207, "elementary os");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (208, "openbsd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (209, "freeipa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (210, "freenas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (211, "wine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (212, "kubuntu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (213, "cloudlinux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (214, "cygwin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (215, "void linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (216, "wsl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (217, "linuxconsole");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (218, "cnvrg.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (219, "amazon freertos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (220, "oracle solaris");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (221, "mqx rtos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (222, "open network linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (223, "lever os");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (224, "tailsos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (225, "talos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (226, "wind river linux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (227, "onos-");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (228, "stratumos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (229, "onos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (230, "copperheados");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (231, "devuan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (232, "react native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (233, "ionic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (234, "flutter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (235, "xamarin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (236, "apache cordova");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (237, "phonegap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (238, "nativescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (239, "expo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (240, "qt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (241, "xamarin forms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (242, "web starter kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (243, "framework7");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (244, "vue native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (245, "visual studio app c...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (246, "create react native...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (247, "sencha touch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (248, "nativebase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (249, "capacitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (250, "nativescript-vue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (251, "ionic react");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (252, "onsen ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (253, "pwa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (254, "pyqt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (255, "ribs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (256, "rubymotion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (257, "juce");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (258, "filemaker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (259, "crosswalk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (260, "apache flex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (261, "gamesparks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (262, "react native seed");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (263, "shoutem ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (264, "fuse open");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (265, "felgo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (266, "trigger.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (267, "openfl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (268, "pepperoni");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (269, "j2objc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (270, "matcha");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (271, "manifoldjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (272, "fabric.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (273, "mobiscroll");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (274, "codename one");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (275, "f-droid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (276, "donejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (277, "voltbuilder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (278, "apportable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (279, "juniversal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (280, "lambdanative");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (281, "viroreact");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (282, "dropsource");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (283, "magenative");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (284, "phonk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (285, "electron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (286, "javafx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (287, "qt5");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (288, "element");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (289, "pygame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (290, "sdl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (291, "nativefier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (292, "juce");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (293, "proton native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (294, "hazel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (295, "wxwidgets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (296, "react native desktop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (297, "electron.net");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (298, "react desktop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (299, "photon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (300, "macgap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (301, "nodegui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (302, "openfl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (303, "electrino");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (304, "sciter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (305, "electron toolkit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (306, "eel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (307, "vuido");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (308, "uno platform");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (309, "reactnativeeverywhere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (310, "qbrt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (311, "fbs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (312, "electron fiddle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (313, "ultralight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (314, "gallium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (315, "neutronium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (316, "expressjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (317, "flask");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (318, "django rest framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (319, "sinatra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (320, "koa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (321, "hapi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (322, "lumen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (323, "sails.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (324, "typeorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (325, "slim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (326, "loopback");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (327, "echo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (328, "fastify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (329, "feathersjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (330, "aiohttp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (331, "grape");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (332, "guzzle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (333, "rails api");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (334, "asyncio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (335, "jersey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (336, "falcon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (337, "restify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (338, "fastapi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (339, "ktor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (340, "iron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (341, "nancy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (342, "bottle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (343, "tastypie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (344, "moleculer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (345, "sapper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (346, "silex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (347, "spark framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (348, "akka http");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (349, "javalin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (350, "aqueduct");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (351, "actionhero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (352, "hug");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (353, "trails");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (354, "nameko");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (355, "total.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (356, "strongloop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (357, "kemal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (358, "apache sling");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (359, "colossus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (360, "propel orm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (361, "starlette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (362, "ent");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (363, "go-fiber");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (364, "balde");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (365, "pippo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (366, "pencil");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (367, "rapidsms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (368, "leaf php");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (369, "simplify.web");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (370, "leptus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (371, "typescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (372, "handlebars.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (373, "mustache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (374, "pug");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (375, "hogan.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (376, "smarty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (377, "jinja");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (378, "flow (js)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (379, "twig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (380, "ejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (381, "liquid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (382, "slim lang");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (383, "purescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (384, "nunjucks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (385, "haxe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (386, "jsonnet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (387, "ractivejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (388, "jade language");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (389, "dust");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (390, "hamlet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (391, "jtwig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (392, "dot.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (393, "emblem.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (394, "eta js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (395, "askama");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (396, "javascript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (397, "python");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (398, "php");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (399, "html5");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (400, "java");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (401, "es6");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (402, "c#");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (403, "css 3");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (404, "ruby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (405, "asp.net");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (406, "markdown");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (407, "swift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (408, "go");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (409, "c++");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (410, "objective-c");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (411, "scala");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (412, "kotlin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (413, "c");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (414, "elixir");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (415, "rust");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (416, "coffeescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (417, "dart");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (418, "r language");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (419, "perl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (420, "json");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (421, "clojure");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (422, "powershell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (423, "groovy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (424, "lua");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (425, "gnu bash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (426, "haskell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (427, "erlang");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (428, "elm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (429, "matlab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (430, "haml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (431, "visual basic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (432, "java ee");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (433, "f#");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (434, "julia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (435, "oh my zsh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (436, "xml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (437, "rstudio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (438, "clojurescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (439, "crystal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (440, "yaml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (441, "zsh (z shell)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (442, "ceph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (443, "actionscript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (444, "common lisp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (445, "ocaml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (446, "awk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (447, "webassembly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (448, "hack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (449, "d");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (450, "processing");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (451, "jruby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (452, "go ethereum");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (453, "asciidoctor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (454, "classic asp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (455, "reasonml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (456, "jsx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (457, "cobol");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (458, "nim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (459, "smalltalk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (460, "geojson");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (461, "octave");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (462, "racket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (463, "mjml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (464, "stan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (465, "arc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (466, "pharo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (467, "boost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (468, "red");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (469, "fabric.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (470, "bel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (471, "rapidminer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (472, "scala native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (473, "livescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (474, "vbscript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (475, "lucee");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (476, "om");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (477, "ada");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (478, "autoit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (479, "vala");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (480, "imba");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (481, "v programming language");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (482, "purebasic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (483, "pypy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (484, "rescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (485, "self");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (486, "cue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (487, "clio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (488, "moonscript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (489, "chicken scheme");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (490, "grain");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (491, "mruby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (492, "daml");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (493, "dhall language");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (494, "eve");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (495, "eta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (496, "jetbrains mps");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (497, "listmonk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (498, "jolie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (499, "pascal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (500, "fstar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (501, "emu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (502, "unison");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (503, "hyperscript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (504, "qore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (505, "bosque language");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (506, "topshell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (507, "metacode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (508, "wasp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (509, "microsoft power fx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (510, "object pascal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (511, "acucobol");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (512, "sass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (513, "animate.css");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (514, "less");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (515, "postcss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (516, "stylus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (517, "compass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (518, "bourbon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (519, "css modules");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (520, "node-sass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (521, "autoprefixer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (522, "purifycss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (523, "tailwindcss jit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (524, "cjss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (525, "css scan pro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (526, "precss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (527, "stitches");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (528, "compiled");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (529, "goober");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (530, "katana-parser");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (531, "angularjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (532, "vue.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (533, "backbone.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (534, "angular 2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (535, "ember.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (536, "aurelia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (537, "marionette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (538, "jsf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (539, "mithril");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (540, "gwt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (541, "durandal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (542, "ampersand.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (543, "espresso.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (544, "canjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (545, "breeze.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (546, "chaplin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (547, "donejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (548, "bootstrap studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (549, "bootswatch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (550, "pingendo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (551, "bootstrap.build");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (552, "gridbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (553, "solid design system");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (554, "shards ui kit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (555, "opencl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (556, "xtend");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (557, "postsharp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (558, "helix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (559, "cursive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (560, "ruru");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (561, "oh my zsh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (562, "tmux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (563, "tortoisesvn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (564, "scoop.sh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (565, "starship (shell pro...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (566, "navi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (567, "picocli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (568, "fzf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (569, "sampler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (570, "bash-my-aws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (571, "strans");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (572, "janetsh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (573, "ascii tree");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (574, "lip gloss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (575, "graphql-yoga");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (576, "altair graphql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (577, "graphql voyager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (578, "graphql editor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (579, "serverless appsync");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (580, "graphql nexus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (581, "spectaql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (582, "graphqurl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (583, "firecamp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (584, "graphql inspector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (585, "graphql faker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (586, "cruddl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (587, "async-graphql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (588, "graphql mesh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (589, "graphql query gener...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (590, "slash graphql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (591, "juniper graphql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (592, "vulcannext");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (593, "graphpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (594, "graffiti.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (595, "irisql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (596, "apirocket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (597, "baseql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (598, "grpc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (599, "json-rpc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (600, "rest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (601, "apache dubbo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (602, "mercury");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (603, "tars");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (604, "graphql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (605, "oracle pl/sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (606, "prisma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (607, "json api");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (608, "graphene");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (609, "jsonapi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (610, "postgraphile");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (611, "graphql ruby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (612, "graphql.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (613, "odata");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (614, "jsoniq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (615, "apache xerces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (616, "jsonata");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (617, "vulcain");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (618, "preql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (619, "java 8");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (620, "rxjava");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (621, "guava");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (622, "thymeleaf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (623, "quarkus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (624, "lombok");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (625, "jsf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (626, "mybatis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (627, "jackson");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (628, "apache freemarker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (629, "querydsl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (630, "project reactor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (631, "mapstruct");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (632, "cdi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (633, "eventbus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (634, "jstl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (635, "web3j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (636, "leakcanary");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (637, "ratpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (638, "proguard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (639, "assertj");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (640, "immutables");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (641, "jgrapht");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (642, "swt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (643, "ff4j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (644, "objectify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (645, "archunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (646, "javacc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (647, "xrebel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (648, "jdsp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (649, "picturesafe-search");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (650, "javamelody");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (651, "objectivesql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (652, "jsoup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (653, "teavm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (654, "activej");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (655, "wasmer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (656, "lunatic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (657, "node.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (658, "django");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (659, "asp.net");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (660, "laravel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (661, "android sdk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (662, "spring boot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (663, "rails");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (664, ".net");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (665, "symfony");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (666, "spring");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (667, "codeigniter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (668, ".net core");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (669, "next.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (670, "meteor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (671, "django rest framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (672, "asp.net core");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (673, "nestjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (674, "phoenix framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (675, "play");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (676, "yii");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (677, "cakephp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (678, "spring framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (679, "spring mvc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (680, "mean");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (681, "grails");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (682, "tornado");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (683, "dropwizard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (684, "zend framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (685, "phalcon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (686, "cocoa touch (ios)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (687, "vert.x");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (688, "jhipster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (689, "nette framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (690, "adonisjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (691, "vaadin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (692, "spring batch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (693, "twig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (694, "micronaut framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (695, "gin gonic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (696, "php-mvc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (697, "actix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (698, "django channels");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (699, "vapor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (700, "kivy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (701, "osgi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (702, "iris");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (703, "io.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (704, "rocket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (705, "pyramid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (706, "mojolicious");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (707, "apache wicket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (708, "kohana");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (709, "api platform");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (710, "twisted");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (711, "apache struts");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (712, "guice");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (713, "aura");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (714, "mono");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (715, "axon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (716, "thinkphp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (717, "beego");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (718, "hanami");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (719, "dancer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (720, "revel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (721, "cocoa (os x)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (722, "servicestack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (723, "finatra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (724, "web2py");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (725, "fat-free");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (726, "swoole");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (727, "padrino");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (728, "yesod");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (729, "nette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (730, "angulardart");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (731, "asp.net zero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (732, "react on rails");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (733, "zk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (734, "fuelphp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (735, "apache dubbo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (736, "draftjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (737, "trailblazer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (738, "ihp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (739, "webapp2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (740, "blade");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (741, "martini");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (742, "ratpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (743, "volt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (744, "total.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (745, "abp commercial");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (746, "redwood");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (747, "hoodie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (748, "buffalo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (749, "tapestry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (750, "masonite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (751, "egg.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (752, "lift framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (753, "apache sling");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (754, "meatier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (755, "grizzly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (756, "yew framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (757, "atmosphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (758, "vraptor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (759, "turbogears");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (760, "apache calcite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (761, "jboss seam");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (762, "fullstack boilerplate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (763, "stimulusreflex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (764, "catalyst");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (765, "sane stack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (766, "jooby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (767, "hydra framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (768, "geddy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (769, "richfaces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (770, "amber framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (771, "scalatra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (772, "bolts");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (773, "jaws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (774, "apache cocoon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (775, "escher");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (776, "purplejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (777, "macaron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (778, "salesloft");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (779, "vibora");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (780, "cuba platform");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (781, "reactiveui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (782, "flow framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (783, "rapidoid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (784, "diamond");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (785, "saturn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (786, "websharper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (787, "vulcanjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (788, "bubbles");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (789, "jolteon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (790, "phpixie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (791, "fusion.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (792, "tower web");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (793, "lad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (794, "bear.sunday");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (795, "graphp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (796, "swifton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (797, "zen rails base appl...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (798, "bedrock.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (799, "react on rails pro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (800, "dframe framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (801, "qcobjects framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (802, "amazon s3");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (803, "google cloud storage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (804, "amazon ebs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (805, "azure storage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (806, "minio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (807, "rackspace cloud files");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (808, "icloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (809, "digitalocean spaces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (810, "rook");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (811, "backblaze b2 cloud ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (812, "openstack swift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (813, "openebs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (814, "storj");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (815, "portworx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (816, "digitalocean block ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (817, "scraper api");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (818, "object storage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (819, "runabove");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (820, "tardigrade");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (821, "rethink");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (822, "dclouds");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (823, "ambry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (824, "cloud local storage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (825, "hpe nimble storage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (826, "cloud storage manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (827, "mcrouter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (828, "twemproxy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (829, "mysql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (830, "postgresql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (831, "mongodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (832, "microsoft sql server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (833, "sqlite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (834, "mariadb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (835, "memcached");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (836, "cassandra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (837, "hadoop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (838, "oracle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (839, "influxdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (840, "oracle pl/sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (841, "couchdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (842, "couchbase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (843, "hbase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (844, "mssql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (845, "azure sql database");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (846, "rethinkdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (847, "arangodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (848, "clickhouse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (849, "ibm db2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (850, "timescaledb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (851, "cockroachdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (852, "pouchdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (853, "riak");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (854, "percona");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (855, "h2 database");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (856, "rocksdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (857, "scylla");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (858, "vertica");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (859, "firebird");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (860, "apache parquet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (861, "orientdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (862, "datomic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (863, "microsoft access");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (864, "faunadb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (865, "event store");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (866, "ravendb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (867, "tidb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (868, "rxdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (869, "galera cluster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (870, "citus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (871, "percona server for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (872, "leveldb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (873, "perconaxtradbcluster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (874, "vitess");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (875, "marklogic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (876, "google cloud spanner");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (877, "greenplum database");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (878, "yugabytedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (879, "litedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (880, "opentsdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (881, "alasql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (882, "foundationdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (883, "indexeddb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (884, "nedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (885, "sybase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (886, "bigchaindb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (887, "crateio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (888, "hsqldb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (889, "sqlyog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (890, "mentat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (891, "kairosdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (892, "percona server for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (893, "symas lmdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (894, "ibm informix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (895, "exasol");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (896, "apache derby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (897, "monetdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (898, "rqlite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (899, "watermelondb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (900, "orbitdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (901, "pipelinedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (902, "altibase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (903, "tokumx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (904, "dalmatinerdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (905, "questdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (906, "kyoto tycoon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (907, "nuodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (908, "exist-db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (909, "mapd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (910, "intersystems iris d...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (911, "heroic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (912, "informatica");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (913, "badger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (914, "harperdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (915, "unqlite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (916, "crux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (917, "clustrixdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (918, "zerodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (919, "chronix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (920, "tokudb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (921, "tibero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (922, "lovefield");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (923, "noms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (924, "jsonlite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (925, "objectbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (926, "dolt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (927, "ledisdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (928, "cakebase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (929, "amazon timestream");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (930, "olegdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (931, "iondb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (932, "velocitydb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (933, "traildb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (934, "kinetica");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (935, "actordb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (936, "irondb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (937, "edgedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (938, "openlink virtuoso");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (939, "lindb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (940, "umbra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (941, "leanxcale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (942, "duckdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (943, "apsaradb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (944, "node-json-db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (945, "databunker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (946, "torodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (947, "pumpkindb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (948, "euclidesdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (949, "amazon qldb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (950, "graphdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (951, "transbase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (952, "ts-sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (953, "centralized mtibu d...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (954, "mishmash io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (955, "bageldb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (956, "tiledb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (957, "apache kafka on heroku");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (958, "amazon managed stre...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (959, "dattell managed str...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (960, "cloudkarafka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (961, "kafka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (962, "rabbitmq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (963, "amazon sqs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (964, "celery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (965, "activemq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (966, "mqtt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (967, "apache nifi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (968, "zeromq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (969, "azure service bus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (970, "nsq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (971, "confluent");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (972, "wcf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (973, "mosquitto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (974, "ibm mq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (975, "apache pulsar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (976, "gearman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (977, "cloudamqp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (978, "xmpp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (979, "nservicebus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (980, "masstransit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (981, "ironmq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (982, "amazon mq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (983, "streamsets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (984, "apache rocketmq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (985, "ejabberd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (986, "kestrel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (987, "vernemq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (988, "msmq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (989, "distributedlog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (990, "emq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (991, "samza");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (992, "disque");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (993, "nanomsg");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (994, "mosca");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (995, "starling");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (996, "mediatr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (997, "sparrow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (998, "rsmq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (999, "hutch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1000, "scheduler api");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1001, "mqtt broker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1002, "simplehttp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1003, "sandglass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1004, "hosted mqtt server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1005, "pg-amqp-bridge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1006, "dramatiq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1007, "ralley");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1008, "mongodb atlas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1009, "mongolab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1010, "compose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1011, "scalegrid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1012, "objectrocket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1013, "amazon redshift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1014, "google bigquery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1015, "snowflake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1016, "amazon emr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1017, "stitch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1018, "cloudera enterprise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1019, "dremio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1020, "qubole");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1021, "azure synapse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1022, "azure hdinsight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1023, "alooma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1024, "matillion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1025, "treasure data");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1026, "fivetran");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1027, "xplenty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1028, "census");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1029, "altiscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1030, "etleap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1031, "panoply");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1032, "datos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1033, "hevo data");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1034, "airbyte");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1035, "myria");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1036, "acho");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1037, "jitsu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1038, "ataccama");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1039, "data warehouses by ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1040, "hotglue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1041, "pelikan cache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1042, "google cloud memory...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1043, "heroku redis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1044, "redis cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1045, "redis to go");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1046, "redisgreen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1047, "iris couch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1048, "redis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1049, "hazelcast");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1050, "aerospike");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1051, "sap hana");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1052, "apache ignite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1053, "memsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1054, "azure redis cache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1055, "tarantool");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1056, "keydb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1057, "lokijs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1058, "voltdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1059, "tile38");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1060, "beringei");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1061, "buntdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1062, "mapdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1063, "kdb+");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1064, "ncache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1065, "summitdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1066, "cqengine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1067, "griddb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1068, "datascript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1069, "xap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1070, "lambda store");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1071, "vineyard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1072, "amazon rds");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1073, "amazon aurora");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1074, "google cloud sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1075, "azure sql database");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1076, "digitalocean manage...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1077, "books");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1078, "azure database for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1079, "cleardb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1080, "aiven");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1081, "azure sql managed i...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1082, "rackspace cloud dat...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1083, "truevault");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1084, "tempodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1085, "cloud db for mysql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1086, "leanxcale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1087, "dimensigon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1088, "planetscaledb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1089, "geniedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1090, "jawsdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1091, "amazon elasticache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1092, "memcachier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1093, "memcached cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1094, "amazon rds for post...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1095, "heroku postgres");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1096, "google cloud sql fo...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1097, "azure database for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1098, "elephantsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1099, "database labs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1100, "crunchy bridge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1101, "paas db postgresql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1102, "amazon neptune");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1103, "graphenedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1104, "tigergraph db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1105, "graph story");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1106, "ehcache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1107, "graphql cache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1108, "apache spark");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1109, "splunk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1110, "apache flink");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1111, "amazon athena");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1112, "apache hive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1113, "presto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1114, "druid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1115, "aws glue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1116, "azure data factory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1117, "apache impala");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1118, "talend");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1119, "mule");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1120, "amazon redshift spe...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1121, "dremio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1122, "vertica");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1123, "apache parquet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1124, "pig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1125, "apache kudu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1126, "delta lake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1127, "apache kylin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1128, "hue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1129, "streamsets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1130, "azure synapse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1131, "azure hdinsight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1132, "openrefine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1133, "singer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1134, "pachyderm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1135, "cdap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1136, "atscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1137, "trifacta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1138, "kylo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1139, "mondrian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1140, "google cloud dataproc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1141, "aresdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1142, "vespa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1143, "zato");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1144, "google cloud data f...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1145, "amundsen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1146, "s3-lambda");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1147, "alation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1148, "eventql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1149, "cloudflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1150, "stratio datacentric");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1151, "datameer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1152, "mara");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1153, "amazon appflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1154, "pilosa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1155, "stumpy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1156, "bds");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1157, "corral");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1158, "blazingsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1159, "stroom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1160, "grooper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1161, "datajet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1162, "ibm db2 big sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1163, "sidekiq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1164, "resque");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1165, "beanstalkd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1166, "hangfire");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1167, "php-fpm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1168, "delayed_job");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1169, "bull");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1170, "kue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1171, "que");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1172, "faktory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1173, "runit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1174, "posthook");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1175, "subserver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1176, "graphile worker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1177, "workq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1178, "fireworq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1179, "taskbotjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1180, "mongoose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1181, "mongoid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1182, "realm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1183, "realm react native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1184, "redux persist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1185, "greendao");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1186, "objectbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1187, "kafka streams");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1188, "apache nifi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1189, "apache storm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1190, "confluent");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1191, "kapacitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1192, "ksql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1193, "heron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1194, "faust");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1195, "samza");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1196, "redpanda");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1197, "riko");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1198, "humanify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1199, "amazon workspaces s...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1200, "neo4j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1201, "dgraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1202, "titan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1203, "janusgraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1204, "cayley");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1205, "redisgraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1206, "grakn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1207, "blazegraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1208, "akutan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1209, "terminusdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1210, "graph engine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1211, "dse graph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1212, "memgraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1213, "nebula graph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1214, "recallgraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1215, "simple graph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1216, "hibernate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1217, "sequelize");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1218, "sqlalchemy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1219, "doctrine 2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1220, "entity framework core");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1221, "entity framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1222, "mybatis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1223, "gorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1224, "nhibernate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1225, "coredata");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1226, "dapper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1227, "peewee");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1228, "objection.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1229, "dbflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1230, "mikroorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1231, "bookshelf.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1232, "propel orm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1233, "ebean");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1234, "greendao");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1235, "jugglingdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1236, "sql+.net");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1237, "sugar orm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1238, "gino");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1239, "notorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1240, "objectivesql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1241, "airflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1242, "github actions");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1243, "apache beam");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1244, "camunda");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1245, "luigi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1246, "workflowy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1247, "apache oozie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1248, "microsoft power aut...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1249, "process street");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1250, "digdag");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1251, "otrs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1252, "unito");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1253, "workfront");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1254, "zenaton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1255, "kissflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1256, "otrs community edition");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1257, "amazon managed work...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1258, "tallyfy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1259, "freeter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1260, "sococo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1261, "imixs-workflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1262, "shipyard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1263, "wrangle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1264, "couler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1265, "amazon efs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1266, "cloudapp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1267, "google cloud filestore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1268, "filerobot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1269, "lucidlink filespaces");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1270, "vchasno");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1271, "chubaofs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1272, "amazon managed apac...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1273, "datastax enterprise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1274, "datastax astra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1275, "kafka manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1276, "rdkafka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1277, "kafkahq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1278, "doctorkafka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1279, "kafka ui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1280, "kafkacenter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1281, "kafdrop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1282, "kowl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1283, "lenses");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1284, "kattlo cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1285, "kafka sprout");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1286, "amazon dynamodb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1287, "cloud firestore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1288, "azure cosmos db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1289, "google cloud datastore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1290, "google cloud bigtable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1291, "firebase realtime d...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1292, "cloudant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1293, "amazon simpledb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1294, "amazon documentdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1295, "datomic cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1296, "restdb.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1297, "cloudboost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1298, "orchestrate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1299, "aiven");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1300, "velocitydb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1301, "slick");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1302, "spring data");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1303, "microsoft sql serve...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1304, "datagrip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1305, "sequel pro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1306, "postgis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1307, "dbeaver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1308, "mysql workbench");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1309, "db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1310, "liquibase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1311, "flyway");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1312, "phpmyadmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1313, "open postgresql mon...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1314, "android room");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1315, "dbt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1316, "knex.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1317, "tableplus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1318, "mongodb compass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1319, "graphiql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1320, "pgadmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1321, "heidisql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1322, "navicat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1323, "graphql playground");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1324, "robo 3t");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1325, "postico");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1326, "mongodb cloud manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1327, "active admin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1328, "apache drill");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1329, "jooq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1330, "azuredatastudio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1331, "postgrest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1332, "administrate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1333, "studio 3t");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1334, "galera cluster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1335, "querydsl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1336, "debezium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1337, "perconaxtradbcluster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1338, "sqoop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1339, "dat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1340, "railsadmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1341, "proxysql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1342, "maxscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1343, "airpal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1344, "reactivemongo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1345, "sequel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1346, "dbforge studio for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1347, "percona monitoring ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1348, "forest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1349, "popsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1350, "nosqlbooster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1351, "dbvisualizer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1352, "oracle enterprise m...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1353, "patroni");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1354, "strongdm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1355, "aws database migrat...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1356, "sqlyog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1357, "migra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1358, "sqlpad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1359, "percona xtrabackup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1360, "omnidb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1361, "dbforge studio for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1362, "psequel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1363, "mysql performance a...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1364, "mycli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1365, "mysql_utils");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1366, "pganalyze");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1367, "prest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1368, "postgresql modeler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1369, "orchestrator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1370, "redsmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1371, "pghero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1372, "postgresql for visu...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1373, "pipelinedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1374, "sqldep");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1375, "sql tabs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1376, "sqlgate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1377, "dbase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1378, "sqitch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1379, "pgrita");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1380, "mapdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1381, "dataform");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1382, "dbforge studio for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1383, "postgres.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1384, "basedash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1385, "humongous.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1386, "pgweb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1387, "objectrocket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1388, "atlas-db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1389, "vertabelo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1390, "pandasql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1391, "xmysql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1392, "graphqurl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1393, "pgrouting");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1394, "foxx-builder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1395, "stellar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1396, "squirrel sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1397, "skor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1398, "teamsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1399, "devops automation f...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1400, "sqlpro studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1401, "dbforge compare bun...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1402, "beekeeper studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1403, "pgcli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1404, "pome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1405, "valentina studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1406, "postage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1407, "dbschema");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1408, "pgwatch2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1409, "dbngin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1410, "objectify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1411, "database rider");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1412, "laravel prequel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1413, "restheart");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1414, "querypie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1415, "dbforge query build...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1416, "dbforge studio for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1417, "pgloader");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1418, "typegoose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1419, "dbforge documenter ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1420, "dbforge fusion for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1421, "dbdiagram");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1422, "dbdocs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1423, "fake2db");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1424, "bedquiltdb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1425, "redisql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1426, "pgdash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1427, "litecli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1428, "cq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1429, "pg_flame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1430, "dbforge data compar...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1431, "erbuilder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1432, "dbforge compare bun...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1433, "dynobase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1434, "iredis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1435, "pgsync");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1436, "pgtyped");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1437, "dbforge data compar...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1438, "dbforge data genera...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1439, "dbforge query build...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1440, "dbforge schema comp...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1441, "hackolade");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1442, "upmin admin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1443, "pghoard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1444, "slack sql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1445, "pgdoctor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1446, "odyssey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1447, "sqlflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1448, "pugsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1449, "octosql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1450, "trevor.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1451, "dbforge compare bun...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1452, "dbforge sql complete");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1453, "dbforge transaction...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1454, "pg_timetable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1455, "typearango");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1456, "master data services");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1457, "dbforge documenter ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1458, "dbforge schema comp...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1459, "fastonosql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1460, "plpgunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1461, "adminium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1462, "massive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1463, "phxsql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1464, "pgrebase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1465, "quilt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1466, "datasette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1467, "rdbtools");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1468, "scalablespace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1469, "sequeltools session...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1470, "idera sql secure");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1471, "sql compliance manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1472, "lettuce.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1473, "dropbase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1474, "octo cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1475, "dataspeeder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1476, "dbforge unit test f...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1477, "dbforge index manag...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1478, "dbforge monitor for...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1479, "dbforge search for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1480, "dbforge sql decrypt...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1481, "data compare for or...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1482, "data generator for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1483, "zheap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1484, "pg_stat_monitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1485, "percona backup for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1486, "xgboost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1487, "pyup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1488, "goconvey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1489, "testify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1490, "ansible");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1491, "chef");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1492, "capistrano");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1493, "puppet labs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1494, "fabric");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1495, "salt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1496, "aws opsworks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1497, "easyengine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1498, "rundeck");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1499, "awx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1500, "cpanel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1501, "mina");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1502, "webmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1503, "serverpilot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1504, "juju");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1505, "shipit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1506, "runcloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1507, "vmware vcenter server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1508, "serverspec");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1509, "visualops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1510, "rex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1511, "itamae");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1512, "cfengine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1513, "puppet bolt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1514, "slickstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1515, "centmin mod");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1516, "ansible semaphore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1517, "cleaver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1518, "cisco aci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1519, "pallet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1520, "cloudslang");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1521, "scriptrock");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1522, "aws amplify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1523, "aws cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1524, "aws shell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1525, "localstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1526, "troposphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1527, "awless");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1528, "blox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1529, "vantage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1530, "bash-my-aws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1531, "s3_disk_util");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1532, "aws cloudshell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1533, "aws audit manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1534, "λ gordon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1535, "bellerophon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1536, "lambdoku");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1537, "cronyo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1538, "cloudviz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1539, "instance watcher");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1540, "endgame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1541, "snyk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1542, "dependabot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1543, "autofac");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1544, "fossa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1545, "greenkeeper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1546, "tidelift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1547, "whitesource");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1548, "gemnasium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1549, "doppins");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1550, "guardrails");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1551, "selenium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1552, "browserstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1553, "karma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1554, "lambdatest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1555, "sauce labs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1556, "webdriverio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1557, "katalon studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1558, "nightwatchjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1559, "ghost inspector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1560, "playwright");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1561, "selenide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1562, "rainforest qa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1563, "testcomplete");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1564, "crossbrowsertesting");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1565, "watir");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1566, "geb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1567, "mabl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1568, "testim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1569, "wallaby.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1570, "browserling");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1571, "galen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1572, "kobiton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1573, "testingbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1574, "autify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1575, "qmetry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1576, "ranorex studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1577, "ghostlab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1578, "endtest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1579, "usetrace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1580, "nemo.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1581, "browserbite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1582, "test studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1583, "reflect");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1584, "preflight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1585, "axcept");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1586, "panther");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1587, "airtap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1588, "diffhub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1589, "courgette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1590, "visual studio live ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1591, "teletype for atom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1592, "gitduck");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1593, "atompair");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1594, "convergence");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1595, "jenkins");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1596, "travis ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1597, "circleci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1598, "gitlab ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1599, "codeship");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1600, "teamcity");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1601, "github actions");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1602, "bamboo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1603, "drone.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1604, "bitrise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1605, "aws codebuild");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1606, "bitbucket pipelines");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1607, "concourse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1608, "azure pipelines");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1609, "gocd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1610, "buildkite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1611, "wercker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1612, "semaphore");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1613, "jenkins x");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1614, "appveyor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1615, "heroku ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1616, "visual studio app c...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1617, "blue ocean");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1618, "shippable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1619, "buildbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1620, "test kitchen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1621, "snap ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1622, "solano ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1623, "strider");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1624, "hudson");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1625, "magnum ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1626, "screwdriver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1627, "finalbuilder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1628, "buildmaster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1629, "nevercode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1630, "phpci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1631, "lambci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1632, "quickbuild");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1633, "zuul ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1634, "cirrus ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1635, "dotci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1636, "continua ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1637, "vespene");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1638, "hercules ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1639, "kraken ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1640, "testling");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1641, "layerci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1642, "cumulusci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1643, "amazon sagemaker pi...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1644, "githost ci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1645, "mottainai");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1646, "cloud9 ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1647, "red hat codeready w...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1648, "codepen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1649, "codeanywhere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1650, "koding");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1651, "nitrous.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1652, "codesandbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1653, "eclipse che");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1654, "stackblitz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1655, "gitpod");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1656, "theia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1657, "jsfiddle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1658, "dartpad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1659, "codio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1660, "codebox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1661, "codiad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1662, "ibm swift sandbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1663, "paizacloud cloud ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1664, "kobra.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1665, "codetasty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1666, "codeonline");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1667, "buidl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1668, "judge0 ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1669, "dumber gist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1670, "components.studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1671, "1mb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1672, "cocoapods");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1673, "bundler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1674, "carthage");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1675, "poetry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1676, "ninject");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1677, "paket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1678, "dependaroo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1679, "asciidoctor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1680, "homer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1681, "dash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1682, "devdocs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1683, "devhints");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1684, "zest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1685, "bump.sh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1686, "anyapi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1687, "horizen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1688, "composer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1689, "homebrew");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1690, "nuget");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1691, "nix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1692, "fpm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1693, "chocolatey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1694, "jspm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1695, "hex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1696, "myget");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1697, "pnpm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1698, "proget");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1699, "sdkman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1700, "pub.dev");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1701, "phing");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1702, "wapm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1703, "rubygems");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1704, "baget");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1705, "cloudrepo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1706, "pydist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1707, "dephell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1708, "go packages");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1709, "violinist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1710, "xrepo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1711, "finalbuilder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1712, "cake (c# make)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1713, "psake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1714, "scons");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1715, "tulsi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1716, "runnable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1717, "coder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1718, "codepicnic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1719, "typescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1720, "coffeescript");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1721, "flow (js)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1722, "pyright");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1723, "sorbet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1724, "git");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1725, "svn (subversion)");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1726, "mercurial");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1727, "plastic scm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1728, "dvc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1729, "magit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1730, "pijul");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1731, "git reflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1732, "gitless");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1733, "bitkeeper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1734, "isomorphic-git");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1735, "replicate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1736, "navicat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1737, "galera cluster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1738, "dynomite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1739, "mysos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1740, "sourcetree");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1741, "gitkraken");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1742, "tower");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1743, "fork");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1744, "sublime merge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1745, "smartgit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1746, "gitup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1747, "vault");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1748, "aws secrets manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1749, "docker secrets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1750, "keywhiz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1751, "torus cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1752, "secrethub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1753, "confidant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1754, "doppler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1755, "knox-app");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1756, "envkey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1757, "akeyless");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1758, "ibm sklm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1759, "biscuit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1760, "vault on aws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1761, "strongbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1762, "kube-secret-syncer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1763, "1password secrets a...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1764, "sitespeed.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1765, "websitepulse test t...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1766, "siteanalyzer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1767, "comparium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1768, "mongodb cloud manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1769, "morpheus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1770, "opennebula");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1771, "scalr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1772, "rightscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1773, "leancloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1774, "copperegg");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1775, "commando.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1776, "mist.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1777, "informatica");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1778, "cloudbolt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1779, "cloudportam");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1780, "commandeer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1781, "onyx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1782, "cloudzero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1783, "cloudstick");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1784, "prosperops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1785, "azure app service plan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1786, "ngrok");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1787, "mamp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1788, "termius");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1789, "gotty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1790, "warp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1791, "pagekite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1792, "teleconsole");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1793, "mr.2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1794, "shutit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1795, "devutils");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1796, "kubestack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1797, "webhookrelay");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1798, "shellvault");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1799, "expose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1800, "webtty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1801, "packetriot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1802, "tunnelto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1803, "requestly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1804, "istio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1805, "kong");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1806, "zuul");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1807, "linkerd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1808, "azure service fabric");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1809, "jersey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1810, "netflix oss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1811, "express gateway");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1812, "conductor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1813, "dapr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1814, "ocelot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1815, "moleculer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1816, "fabric8");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1817, "lagom framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1818, "micro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1819, "seneca");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1820, "claudia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1821, "aws app mesh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1822, "arangodb foxx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1823, "goa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1824, "nameko");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1825, "expressive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1826, "pact");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1827, "go micro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1828, "kuma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1829, "hexagon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1830, "armeria");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1831, "foxx-builder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1832, "zeebe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1833, "helidon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1834, "tars");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1835, "gloo edge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1836, "gubernator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1837, "dactory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1838, "pactflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1839, "thundra sidekick");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1840, "backstage.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1841, "opslevel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1842, "cortex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1843, "effx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1844, "laravel homestead");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1845, "hhvm (hiphop virtua...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1846, "azure virtual machines");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1847, "graalvm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1848, "openvswitch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1849, "multipass");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1850, "bitnami");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1851, "amazon ec2 containe...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1852, "google kubernetes e...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1853, "amazon eks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1854, "aws fargate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1855, "azure kubernetes se...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1856, "docker for aws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1857, "azure container ser...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1858, "docker cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1859, "ibm containers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1860, "hyper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1861, "azure container ins...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1862, "joyent triton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1863, "docker datacenter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1864, "containership");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1865, "dchq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1866, "containerum");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1867, "tectonic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1868, "supergiant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1869, "orchard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1870, "instainer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1871, "platform9 managed k...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1872, "platform9 managed o...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1873, "coveralls");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1874, "codecov");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1875, "uberalls");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1876, "istanbul");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1877, "openstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1878, "apache cloudstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1879, "opennebula");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1880, "eucalyptus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1881, "maas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1882, "virtkick");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1883, "opsstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1884, "aws codepipeline");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1885, "buddy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1886, "spinnaker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1887, "google cloud build");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1888, "deploybot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1889, "deployer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1890, "cloud 66");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1891, "weaveworks flux");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1892, "harness.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1893, "deployhq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1894, "gaia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1895, "continuous delivery...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1896, "armory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1897, "teletraan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1898, "urbancode deploy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1899, "hashicorp waypoint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1900, "ihp cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1901, "deployplace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1902, "autorabit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1903, "visual studio code");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1904, "sublime text");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1905, "vim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1906, "notepad++");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1907, "atom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1908, "emacs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1909, "brackets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1910, "neovim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1911, "tinymce");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1912, "rstudio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1913, "codemirror");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1914, "adobe dreamweaver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1915, "textmate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1916, "codepen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1917, "gedit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1918, "vscodium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1919, "monaco editor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1920, "jsfiddle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1921, "coda 2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1922, "org mode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1923, "bbedit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1924, "ultraedit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1925, "highlight.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1926, "nova");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1927, "kakoune");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1928, "micro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1929, "coteditor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1930, "vimr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1931, "coderunner");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1932, "slap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1933, "light table");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1934, "textadept");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1935, "codelobster ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1936, "quill.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1937, "remacs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1938, "λiquid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1939, "boxer text editor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1940, "yeoman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1941, "slushjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1942, "lineman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1943, "virtualbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1944, "vmware vsphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1945, "proxmox ve");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1946, "kvm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1947, "qemu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1948, "vmware fusion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1949, "parallels desktop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1950, "xen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1951, "parallels");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1952, "vmware vsan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1953, "oracle vm server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1954, "virtuozzo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1955, "ottomatica slim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1956, "varnish");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1957, "apache traffic server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1958, "squid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1959, "section");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1960, "nuster");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1961, "apache mesos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1962, "nomad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1963, "dc/os");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1964, "yarn hadoop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1965, "mesosphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1966, "kops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1967, "apache aurora");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1968, "gardener");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1969, "elastic apache mesos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1970, "peloton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1971, "kocho");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1972, "vagrant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1973, "boot2docker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1974, "xenserver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1975, "libvirt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1976, "otto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1977, "azk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1978, "puphpet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1979, "xen orchestra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1980, "kubevirt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1981, "webpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1982, "gulp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1983, "grunt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1984, "parcel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1985, "rollup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1986, "brunch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1987, "webpacker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1988, "laravel elixir");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1989, "jetpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1990, "snowpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1991, "vite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1992, "backpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1993, "rome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1994, "system.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1995, "metro bundler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1996, "just");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1997, "pingy cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1998, "stealjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (1999, "fly.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2000, "pika pack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2001, "microbundle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2002, "maid");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2003, "sagui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2004, "amazon ecr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2005, "harbor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2006, "quay.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2007, "gandalf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2008, "kraken by uber");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2009, "amazon elastic cont...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2010, "consul");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2011, "zookeeper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2012, "eureka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2013, "etcd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2014, "keepalived");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2015, "serf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2016, "skydns");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2017, "smartstack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2018, "libraries.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2019, "baker street");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2020, "zetcd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2021, "vyne");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2022, "npm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2023, "yarn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2024, "requirejs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2025, "bower");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2026, "browserify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2027, "component");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2028, "pip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2029, "bundler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2030, "verdaccio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2031, "duo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2032, "browserify-cdn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2033, "entropic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2034, "pika.dev");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2035, "open-registry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2036, "octopus deploy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2037, "aws codedeploy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2038, "laravel forge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2039, "distelli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2040, "ploi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2041, "elasticbox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2042, "dockbit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2043, "featurepeek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2044, "launchdeck");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2045, "dockup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2046, "chrome devtools");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2047, "percy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2048, "ghost inspector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2049, "applitools");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2050, "responsively");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2051, "lightproxy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2052, "requestly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2053, "simply testable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2054, "gradle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2055, "apache maven");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2056, "cmake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2057, "sonatype nexus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2058, "jfrog artifactory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2059, "bazel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2060, "apache ant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2061, "sbt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2062, "jitpack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2063, "buck");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2064, "eventbus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2065, "pants");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2066, "please");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2067, "capsule");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2068, "mill");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2069, "aws elastic load ba...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2070, "haproxy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2071, "traefik");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2072, "envoy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2073, "digitalocean load b...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2074, "glbc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2075, "f5 big-ip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2076, "google cloud load b...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2077, "fly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2078, "node-http-proxy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2079, "pound");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2080, "seesaw");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2081, "hipache");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2082, "vulcand");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2083, "github load balance...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2084, "flexbalancer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2085, "fabio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2086, "katran");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2087, "google traffic dire...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2088, "inlets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2089, "bfe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2090, "testrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2091, "zephyr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2092, "testlink");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2093, "qtest management");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2094, "testlodge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2095, "practitest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2096, "applause");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2097, "hiptest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2098, "qmetry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2099, "kualitee");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2100, "testcaselab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2101, "webtestit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2102, "root cause");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2103, "reportportal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2104, "qase.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2105, "rails spring");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2106, "git flow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2107, "atlassian stash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2108, "pre-commit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2109, "diff so fancy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2110, "tortoisegit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2111, "git-repo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2112, "gvfs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2113, "pre-commit by yelp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2114, "ungit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2115, "working copy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2116, "hub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2117, "git lfs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2118, "gitstats");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2119, "go-git");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2120, "git extensions");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2121, "gitential");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2122, "lazygit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2123, "check it out");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2124, "gitsome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2125, "xltrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2126, "gitlint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2127, "git-fastclone");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2128, "gitql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2129, "deepgit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2130, "gitui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2131, "grv - git repositor...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2132, "git-sizer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2133, "committasks");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2134, "git-bug");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2135, "git-dit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2136, "rebase-editor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2137, "gitmask");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2138, "autogit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2139, "getgit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2140, "gitqlient");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2141, "git-workspace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2142, "gitsense");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2143, "kooder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2144, "polygit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2145, "hystrix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2146, "polly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2147, "browsersync");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2148, "codekit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2149, "livereload");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2150, "prepros");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2151, "macdown");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2152, "typora");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2153, "caret");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2154, "mou");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2155, "marp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2156, "remarkable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2157, "release");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2158, "github polls");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2159, "octotree");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2160, "devhub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2161, "github cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2162, "astral");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2163, "refined github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2164, "lgtm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2165, "octokit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2166, "gordon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2167, "devspace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2168, "codehub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2169, "ship");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2170, "octobox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2171, "gitnotifier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2172, "shrink");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2173, "github native");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2174, "remergr.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2175, "insight.io for github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2176, "travisbuddy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2177, "mergify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2178, "git history");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2179, "act");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2180, "gitrob");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2181, "gitmonitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2182, "github notifier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2183, "lepton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2184, "codenav");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2185, "deckhub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2186, "pullapprove");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2187, "gitscout");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2188, "bookmarking for github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2189, "derek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2190, "datree");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2191, "pull dog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2192, "lmvtfy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2193, "compass for github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2194, "gitpoint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2195, "gisto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2196, "pullpreview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2197, "github1s");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2198, "backhub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2199, "cashew");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2200, "gititback");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2201, "gitnews");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2202, "grape for github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2203, "next release");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2204, "github surf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2205, "vim-repo-edit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2206, "repo-peek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2207, "apache cxf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2208, "karyon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2209, "visual studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2210, "intellij idea");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2211, "android studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2212, "pycharm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2213, "xcode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2214, "phpstorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2215, "webstorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2216, "eclipse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2217, "netbeans ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2218, "rubymine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2219, "goland");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2220, "jetbrains rider");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2221, "rstudio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2222, "clion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2223, "delphi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2224, "arduino ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2225, "platformio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2226, "qt creator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2227, "deco");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2228, "adobe coldfusion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2229, "spyder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2230, "truffle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2231, "appcode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2232, "nuclide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2233, "pharo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2234, "materia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2235, "geany");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2236, "komodo ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2237, "zend");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2238, "jetbrains space");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2239, "lazarus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2240, "vim python ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2241, "continuous");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2242, "monodevelop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2243, "red hat codeready s...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2244, "codelite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2245, "haskell for mac");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2246, "oni");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2247, "wing pythonide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2248, "cursive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2249, "kdevelop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2250, "scala ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2251, "aptana studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2252, "codetasty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2253, "light table");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2254, "deepforge");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2255, "codelobster ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2256, "visual studio for mac");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2257, "carbide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2258, "leo editor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2259, "ap studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2260, "shiftedit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2261, "id3");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2262, "querystorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2263, "aquameta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2264, "kretes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2265, "spacemacs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2266, "resharper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2267, "atom-ide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2268, "kite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2269, "vim-plug");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2270, "vundle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2271, "rust-analyzer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2272, "spacevim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2273, "pathogen.vim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2274, "postsharp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2275, "neobundle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2276, "gitsavvy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2277, "ncrunch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2278, "rest client");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2279, "ionide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2280, "debug visualizer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2281, "vuln cost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2282, "tabnine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2283, "vscode dms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2284, "codeswing");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2285, "vs code theme studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2286, "tokamak");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2287, "solis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2288, "github1s");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2289, "dendron");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2290, "vschat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2291, "slack theme for vscode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2292, "chat for vscode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2293, "github surf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2294, "vim-repo-edit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2295, "repo-peek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2296, "sourcegraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2297, "fisheye");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2298, "hound by etsy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2299, "opengrok");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2300, "metacode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2301, "kooder");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2302, "xmake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2303, "biicode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2304, "junit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2305, "cucumber");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2306, "phpunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2307, "rspec");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2308, "pytest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2309, "capybara");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2310, "mockito");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2311, "robot framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2312, "react-testing-library");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2313, "xunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2314, "karate dsl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2315, "testcafe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2316, "nunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2317, "testng");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2318, "behat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2319, "codeception");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2320, "spock framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2321, "specflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2322, "behave");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2323, "detox");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2324, "selenide");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2325, "moq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2326, "unittest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2327, "inspec");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2328, "testcomplete");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2329, "scalatest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2330, "arquillian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2331, "fitnesse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2332, "phpspec");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2333, "robolectric");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2334, "poltergeist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2335, "mockk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2336, "google test");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2337, "fluent assertions");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2338, "dbunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2339, "canopy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2340, "experitest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2341, "pumba");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2342, "testsigma");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2343, "jbehave");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2344, "knapsack pro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2345, "typemock");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2346, "test studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2347, "happo.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2348, "lettuce");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2349, "kotest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2350, "atata");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2351, "kahlan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2352, "howitzer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2353, "seleniumaxedotnet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2354, "lippia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2355, "nimble framework");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2356, "recheck-web");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2357, "testery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2358, "mockolo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2359, "automqa");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2360, "equa11y");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2361, "spek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2362, "wakatime");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2363, "scope");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2364, "codealike");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2365, "waydev");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2366, "haystack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2367, "squadlytics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2368, "phantomjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2369, "puppeteer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2370, "casperjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2371, "playwright");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2372, "jsdom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2373, "splash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2374, "serverless chrome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2375, "slimerjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2376, "chromeless");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2377, "headlesstesting");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2378, "kubernetes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2379, "docker compose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2380, "helm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2381, "rancher");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2382, "docker swarm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2383, "spring cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2384, "docker machine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2385, "portainer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2386, "google cloud contai...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2387, "argo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2388, "docker hub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2389, "marathon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2390, "harbor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2391, "rancheros");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2392, "kitematic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2393, "tutum");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2394, "minikube");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2395, "skaffold");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2396, "netflix oss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2397, "compose on kubernetes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2398, "xmpp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2399, "weave");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2400, "codefresh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2401, "containerd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2402, "k3s");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2403, "lens");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2404, "lando");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2405, "clair");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2406, "testcontainers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2407, "k9s");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2408, "autofac");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2409, "flux cd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2410, "docker swarm visual...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2411, "spread");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2412, "kaniko");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2413, "tilt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2414, "apache dubbo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2415, "gitkube");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2416, "lazydocker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2417, "flocker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2418, "linuxkit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2419, "portworx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2420, "hadolint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2421, "jib");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2422, "kustomize");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2423, "castle windsor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2424, "helios");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2425, "ctop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2426, "kubedb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2427, "devspace for kubern...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2428, "kubernetes-deploy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2429, "okteto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2430, "kind");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2431, "watchtower");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2432, "dockersh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2433, "devspace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2434, "kompose");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2435, "kubespy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2436, "octant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2437, "trivy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2438, "centurion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2439, "fleet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2440, "moby");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2441, "contiv");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2442, "garden");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2443, "habitus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2444, "conduit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2445, "makisu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2446, "velero");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2447, "katacontainers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2448, "flagger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2449, "dumb-init");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2450, "dockerized");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2451, "falco security");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2452, "rancher fleet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2453, "kubectx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2454, "gatekeeper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2455, "hyscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2456, "k0s");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2457, "helmfile");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2458, "devops stack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2459, "gockerize");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2460, "bitnami stacksmith");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2461, "microcontainers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2462, "furan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2463, "simple injector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2464, "sanic for kubernetes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2465, "k3sup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2466, "dockerslim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2467, "kubevault");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2468, "cloudflow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2469, "kubestack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2470, "kube-bench");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2471, "aws copilot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2472, "nvidia container to...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2473, "hypernetes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2474, "torus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2475, "last.backend");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2476, "k8s-sidecar-injector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2477, "kubeadm-aws");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2478, "kubecost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2479, "devspace cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2480, "cycle.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2481, "cast.ai");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2482, "aws app2container");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2483, "earthly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2484, "pixie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2485, "roboll helmfile");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2486, "play with docker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2487, "cri-o");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2488, "cycle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2489, "draft");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2490, "smith");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2491, "dotmesh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2492, "titus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2493, "goldpinger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2494, "docker immutable wo...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2495, "kruise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2496, "nixery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2497, "kubestone");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2498, "micronetes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2499, "kubernetes-common-s...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2500, "oneinfra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2501, "kubetap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2502, "kube-hunter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2503, "lagoon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2504, "aws controllers for...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2505, "avatar-cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2506, "lenses");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2507, "ofelia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2508, "amazon managed serv...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2509, "otomi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2510, "powerstrip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2511, "container factory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2512, "k8guard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2513, "crashcart");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2514, "railcar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2515, "faas-netes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2516, "critical stack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2517, "powerfulseal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2518, "konstellate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2519, "gopaddle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2520, "kubeadvisor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2521, "kubenav");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2522, "maesh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2523, "xlskubectl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2524, "cluster turndown");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2525, "cape");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2526, "kopf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2527, "kubectl flame");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2528, "openyurt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2529, "headlamp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2530, "dockter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2531, "amazon eks distro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2532, "swiss army kube for...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2533, "swiss army kube");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2534, "arkade");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2535, "komodor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2536, "k8s-mirror");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2537, "docker2saas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2538, "k3d");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2539, "kctf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2540, "kubesql");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2541, "gor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2542, "closure compiler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2543, "ace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2544, "firepad");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2545, "tandem");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2546, "graphite docs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2547, "conclave");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2548, "terraform");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2549, "aws cloudformation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2550, "packer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2551, "pulumi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2552, "geoengineer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2553, "yocto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2554, "aws cloud developme...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2555, "habitat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2556, "atlas");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2557, "google cloud deploy...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2558, "azure resource manager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2559, "scalr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2560, "arvancloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2561, "hashicorp sentinel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2562, "buildroot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2563, "goformation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2564, "fugue");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2565, "sparkleformation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2566, "clutch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2567, "metamon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2568, "stacker 1");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2569, "autocloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2570, "aws proton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2571, "modernizr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2572, "babel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2573, "scala.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2574, "hermes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2575, "emscripten");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2576, "rome");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2577, "fable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2578, "sucrase");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2579, "quickjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2580, "cloudcraft");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2581, "sketchup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2582, "diagrams");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2583, "cloudmapper");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2584, "hava");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2585, "cloudockit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2586, "cloudviz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2587, "launchdarkly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2588, "split");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2589, "rollout");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2590, "configcat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2591, "airship");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2592, "unleash hosted");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2593, "flagr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2594, "bullet train");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2595, "optimizely rollouts");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2596, "ff4j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2597, "petri");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2598, "optimizely full stack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2599, "flipt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2600, "detect-secrets");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2601, "gitguardian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2602, "benchmarkdotnet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2603, "azure devops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2604, "azure devops server");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2605, "spring tools 4");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2606, "codestream");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2607, "codota");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2608, "dbforge fusion for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2609, "dbforge fusion for ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2610, "google cloud code");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2611, "mutagen");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2612, "ozcode visual studi...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2613, "devskim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2614, "eslint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2615, "sonarqube");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2616, "prettier");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2617, "code climate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2618, "codacy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2619, "rubocop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2620, "phabricator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2621, "tslint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2622, "snyk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2623, "stylelint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2624, "gerrit code review");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2625, "sonarlint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2626, "scss-lint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2627, "scrutinizer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2628, "editorconfig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2629, "swiftlint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2630, "checkstyle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2631, "amazon codeguru");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2632, "jacoco");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2633, "crucible");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2634, "pylint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2635, "phpstan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2636, "codefactor.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2637, "standard js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2638, "findbugs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2639, "coala");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2640, "veracode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2641, "hound");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2642, "brakeman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2643, "pmd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2644, "coverity scan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2645, "styleci");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2646, "sass lint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2647, "jshint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2648, "black duck");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2649, "review board");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2650, "codebeat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2651, "reviewable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2652, "bithound");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2653, "infer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2654, "pullrequest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2655, "php codesniffer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2656, "gitprime");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2657, "better code hub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2658, "reek");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2659, "quantifiedcode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2660, "pvs-studio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2661, "credo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2662, "jslint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2663, "cacher");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2664, "codenarc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2665, "sourcelevel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2666, "roslyn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2667, "pullreview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2668, "deepscan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2669, "psalm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2670, "codebrag");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2671, "sourcetrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2672, "deepsource analyzer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2673, "golangci-lint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2674, "refactor.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2675, "git-appraise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2676, "textlint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2677, "code inspector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2678, "stylefmt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2679, "reshift");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2680, "velocity by code cl...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2681, "code spotter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2682, "datree");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2683, "sider");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2684, "codegrip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2685, "fody");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2686, "ktlint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2687, "phacility");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2688, "gitcop");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2689, "gitcolony");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2690, "review assistant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2691, "code compare");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2692, "xrebel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2693, "piranha");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2694, "embold");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2695, "ndepend");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2696, "sourcery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2697, "ktfmt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2698, "mocha");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2699, "jest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2700, "cypress");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2701, "jasmine");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2702, "enzyme");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2703, "protractor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2704, "chai");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2705, "ava");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2706, "sinonjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2707, "codeceptjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2708, "qunit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2709, "ghost inspector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2710, "supertest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2711, "last-hit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2712, "majestic gui");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2713, "sorry-cypress");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2714, "vorlon.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2715, "baretest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2716, "courgette");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2717, "venus.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2718, "fpm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2719, "conan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2720, "dist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2721, "packagist");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2722, "gemfury");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2723, "aptly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2724, "packagecloud.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2725, "pypi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2726, "cloudsmith package");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2727, "packagr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2728, "repman");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2729, "aws codeartifact");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2730, "cloudrepo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2731, "docker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2732, "lxc");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2733, "lxd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2734, "studio 3t");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2735, "vagrant cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2736, "rkt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2737, "openvz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2738, "smartos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2739, "clear containers");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2740, "boxfuse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2741, "flatpak");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2742, "zerovm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2743, "fastlane");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2744, "bitrise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2745, "buddybuild");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2746, "greenhouse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2747, "codemagic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2748, "distiller");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2749, "appcircle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2750, "appium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2751, "magneto");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2752, "kobiton");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2753, "earlgrey");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2754, "pcloudy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2755, "experitest");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2756, "kiwi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2757, "apptim");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2758, "cto.ai");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2759, "relay");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2760, "devops automation f...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2761, "apexsql devops toolkit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2762, "toad devops toolkit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2763, "sleuth");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2764, "compliant database ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2765, "testflight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2766, "hockeyapp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2767, "fabric by twitter");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2768, "beta by crashlytics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2769, "testfairy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2770, "boardingbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2771, "drydock");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2772, "updraft");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2773, "apperian");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2774, "releasable");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2775, "github");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2776, "gitlab");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2777, "bitbucket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2778, "github enterprise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2779, "aws codecommit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2780, "jfrog artifactory");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2781, "gitea");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2782, "gogs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2783, "beanstalk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2784, "google cloud source...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2785, "gitbucket");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2786, "perforce");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2787, "bintray");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2788, "upsource");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2789, "azure repos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2790, "gitolite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2791, "tortoisesvn");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2792, "rhodecode");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2793, "bit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2794, "gitblit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2795, "sympli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2796, "codehub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2797, "fossil");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2798, "kallithea");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2799, "deveo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2800, "apache allura");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2801, "codebasehq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2802, "onedev");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2803, "gitorious");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2804, "gitplex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2805, "codeplane");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2806, "cornerstone 4");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2807, "radicle");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2808, "loki");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2809, "log4j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2810, "seq");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2811, "cocoalumberjack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2812, "uno");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2813, "bunyan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2814, "zap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2815, "swiftybeaver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2816, "loggr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2817, "logdevice");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2818, "willow");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2819, "castle core");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2820, "nanolog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2821, "loggie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2822, "crossplane");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2823, "cloudbolt");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2824, "nutanix beam");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2825, "komiser");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2826, "cloudmarker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2827, "sentry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2828, "trackjs");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2829, "rollbar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2830, "bugsnag");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2831, "airbrake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2832, "raygun");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2833, "honeybadger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2834, "opbeat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2835, "errbit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2836, "overops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2837, "epsagon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2838, "errorception");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2839, "fluentvalidation");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2840, "leakcanary");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2841, "stackhawk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2842, "elmah.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2843, "app enlight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2844, "exceptionless");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2845, "backtrace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2846, "ncrunch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2847, "glitchtip");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2848, "exceptiontrap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2849, "kuoll");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2850, "logify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2851, "log owl");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2852, "errorship");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2853, "pingdom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2854, "uptimerobot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2855, "better uptime");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2856, "statuscake");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2857, "updown.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2858, "site24x7");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2859, "gtmetrix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2860, "freshping");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2861, "oh dear!");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2862, "alertbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2863, "nodeping");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2864, "apex ping");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2865, "webgazer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2866, "fyipe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2867, "uptime.com");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2868, "checkly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2869, "request metrics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2870, "pingbreak");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2871, "pingometer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2872, "whatap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2873, "hyperping");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2874, "stillalive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2875, "panopta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2876, "picreel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2877, "speedfactor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2878, "vigil");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2879, "visualobserver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2880, "distill");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2881, "websitepulse");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2882, "monitoro");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2883, "awesometechstack.com");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2884, "pagerduty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2885, "opsgenie");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2886, "victorops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2887, "healthchecks.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2888, "bigpanda");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2889, "cronitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2890, "alertops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2891, "spike.sh");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2892, "netflix dispatch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2893, "squadcast");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2894, "zenduty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2895, "hosted status page");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2896, "xmatters");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2897, "firehydrant");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2898, "freshstatus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2899, "amixr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2900, "kintaba");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2901, "sendquick oncloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2902, "jeli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2903, "beats");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2904, "wireshark");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2905, "prtg");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2906, "riemann");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2907, "librenms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2908, "nagios xi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2909, "snort");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2910, "dpdk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2911, "observium");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2912, "packetbeat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2913, "pi-hole");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2914, "cisco ise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2915, "cisco dna center");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2916, "tailscale");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2917, "forescout");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2918, "perfops cli");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2919, "solarwinds npm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2920, "wireedit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2921, "cloudradar");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2922, "solarwinds nta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2923, "manageengine opmanager");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2924, "opmanager msp");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2925, "manageengine oputils");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2926, "manageengine firewa...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2927, "manageengine netflo...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2928, "nodemon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2929, "nodejs-dashboard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2930, "nodefly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2931, "nodetime");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2932, "logstash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2933, "elk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2934, "papertrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2935, "graylog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2936, "fluentd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2937, "splunk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2938, "logentries");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2939, "loggly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2940, "aws cloudtrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2941, "sumo logic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2942, "filebeat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2943, "splunk enterprise");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2944, "logdna");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2945, "logmatic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2946, "serilog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2947, "logz.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2948, "slf4j");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2949, "logback");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2950, "apache flume");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2951, "scalyr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2952, "rsyslog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2953, "scribe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2954, "timber.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2955, "sumologic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2956, "coralogix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2957, "alert logic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2958, "sematext");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2959, "bugfender");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2960, "timberio vector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2961, "humio");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2962, "logtrail");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2963, "phppgadmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2964, "devo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2965, "logagent");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2966, "fortianalyzer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2967, "bindplane");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2968, "ok log");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2969, "chaossearch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2970, "gravwell");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2971, "logit.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2972, "changefeed");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2973, "logmx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2974, "sqs-s3-logger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2975, "logsense");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2976, "streamstash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2977, "stroom");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2978, "manageengine eventl...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2979, "crashlytics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2980, "instabug");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2981, "crittercism");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2982, "signl4");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2983, "buglife");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2984, "amazon cloudwatch");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2985, "stackdriver");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2986, "digitalocean monito...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2987, "stts");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2988, "aws config");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2989, "lumigo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2990, "cloudability");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2991, "cloudcheckr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2992, "dome9");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2993, "opsee");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2994, "statusgator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2995, "autotune");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2996, "statusbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2997, "cast.ai");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2998, "chronosphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (2999, "infracost");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3000, "opsview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3001, "statusticker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3002, "metricly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3003, "cloudforecast");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3004, "aws cost anomaly de...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3005, "statuspage.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3006, "cachet");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3007, "status.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3008, "statusfy");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3009, "hund");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3010, "fyipe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3011, "checkup");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3012, "statushub");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3013, "statusentry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3014, "status list");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3015, "stashboard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3016, "staytus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3017, "decentralized statu...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3018, "instatus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3019, "statusdashboard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3020, "statuspal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3021, "statuspage generator");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3022, "pagefate");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3023, "new relic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3024, "datadog");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3025, "ruxit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3026, "appdynamics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3027, "azure application i...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3028, "dynatrace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3029, "phpmyadmin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3030, "splunk cloud");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3031, "raygun");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3032, "librato");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3033, "skylight");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3034, "scout");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3035, "appsignal");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3036, "navicat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3037, "blackfire.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3038, "instana");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3039, "aws x-ray");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3040, "honeycomb");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3041, "signalfx");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3042, "kadira");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3043, "wavefront");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3044, "stackify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3045, "keymetrics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3046, "server density");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3047, "logicmonitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3048, "dashbird");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3049, "lightstep");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3050, "instrumental");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3051, "opencensus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3052, "monitis");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3053, "thundra");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3054, "opsdash");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3055, "uptrends");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3056, "iopipe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3057, "boundary");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3058, "vividcortex");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3059, "traceview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3060, "vector");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3061, "netuitive");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3062, "opsmatic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3063, "checkly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3064, "falco");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3065, "request metrics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3066, "manageengine applic...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3067, "jennifer");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3068, "treo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3069, "apache skywalking");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3070, "catchpoint synthetics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3071, "catchpoint rum");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3072, "network polygraph");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3073, "spm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3074, "sealion");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3075, "checkbot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3076, "inspeqtor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3077, "circonus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3078, "rorvswild");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3079, "metricly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3080, "profilo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3081, "javamelody");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3082, "signoz");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3083, "pyroscope");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3084, "plumbr");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3085, "packtracker");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3086, "cloudprobes");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3087, "opstrace");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3088, "speedcurve");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3089, "atatus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3090, "qbaka");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3091, "kibana");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3092, "grafana");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3093, "prometheus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3094, "nagios");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3095, "zabbix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3096, "graphite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3097, "statsd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3098, "jaeger");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3099, "sensu");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3100, "telegraf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3101, "netdata");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3102, "supervisord");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3103, "icinga");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3104, "collectd");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3105, "zipkin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3106, "opentracing");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3107, "cacti");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3108, "munin");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3109, "sysdig");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3110, "thanos");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3111, "solarwinds");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3112, "nginx amplify");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3113, "pixi");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3114, "amazon guardduty");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3115, "metricbeat");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3116, "laravel telescope");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3117, "checkmk");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3118, "chronograf");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3119, "fabric.js");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3120, "kiali");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3121, "ambari");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3122, "monit");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3123, "azure monitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3124, "centreon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3125, "lumigo");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3126, "elastalert");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3127, "ganglia");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3128, "hosted graphite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3129, "bosun");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3130, "alerta");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3131, "sumologic");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3132, "opencensus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3133, "opentelemetry");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3134, "shinken");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3135, "cabot");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3136, "dropwizard metrics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3137, "vulcan");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3138, "rrdtool");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3139, "411");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3140, "m3");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3141, "kamon");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3142, "assertible");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3143, "fyipe");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3144, "appoptics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3145, "flapjack");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3146, "netflix flamescope");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3147, "glances");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3148, "statsite");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3149, "whatap");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3150, "monitorix");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3151, "veneur");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3152, "monitor in a box");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3153, "status list");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3154, "zenoss");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3155, "monitoror");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3156, "chronosphere");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3157, "ward");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3158, "terraboard");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3159, "pa server monitor");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3160, "blueflood");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3161, "opennms");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3162, "circonus");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3163, "searchly");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3164, "opsview");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3165, "blue matador");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3166, "amazon managed serv...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3167, "ruby server timing");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3168, "hostedmetrics");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3169, "newspoint");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3170, "storm.dev");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3171, "skedler");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3172, "xitoring");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3173, "stackstorm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3174, "neptune.io");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3175, "runbook");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3176, "gunnery");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3177, "routine ops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3178, "runops");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3179, "pm2");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3180, "supervisord");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3181, "forever");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3182, "manageengine networ...");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3183, "solarwinds ncm");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3184, "manageengine oputils");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3185, "zyxel");
INSERT INTO `matching`.`techstack` (`id`, `name`) VALUES (3186, "access point nanohd");