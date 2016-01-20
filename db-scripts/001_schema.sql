-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS  `rzt_management` ;
-- -----------------------------------------------------
-- Schema rzt_management
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema rzt_management
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `rzt_management` ;
USE `rzt_management` ;



    
-- -----------------------------------------------------
-- Table `rzt_management`.`im_designation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_designation` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_designation` (
  `d_id` INT(11) NOT NULL AUTO_INCREMENT,
  `d_name` VARCHAR(100) NOT NULL,
  `d_created_at` TIMESTAMP NOT NULL,
  `d_is_active` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`d_id`)
);    
 -- -----------------------------------------------------
-- Table `rzt_management`.`im_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_address` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_address`(
  `a_id` INT(11) NOT NULL AUTO_INCREMENT,
  `a_address_line1` VARCHAR(100) NOT NULL,
  `a_address_line2` VARCHAR(100),
  `a_city` VARCHAR(100) NOT NULL,
  `a_state` VARCHAR(100) NOT NULL,
  `a_country` VARCHAR(100) NOT NULL,
  `a_postal_code` INT(10) NOT NULL,
  PRIMARY KEY (`a_id`)
  );

-- -----------------------------------------------------
-- Table `rzt_management`.`im_emp_contacts`
-- -----------------------------------------------------
 DROP TABLE IF EXISTS `rzt_management`.`im_contacts` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_contacts`(
  `ec_id` INT(11) NOT NULL AUTO_INCREMENT,
  `ec_personal_email` VARCHAR(100) NOT NULL,
  `ec_office_email` VARCHAR(100) NOT NULL,
  `ec_contact_num` VARCHAR(50) NOT NULL,
  `ec_emergency_contact_num` VARCHAR(50) NOT NULL,
  `ec_skype_id` VARCHAR(50),
  `ec_slack_id` VARCHAR(50),
   PRIMARY KEY (`ec_id`)
  );
  
-- -----------------------------------------------------
-- Table `rzt_management`.`im_employees`
-- -----------------------------------------------------
 DROP TABLE IF EXISTS `rzt_management`.`im_employees` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_employees`(
  `emp_id` INT(11) NOT NULL AUTO_INCREMENT,
  `emp_eno` VARCHAR(100) NOT NULL,
  `emp_first_name` VARCHAR(100) NOT NULL,
  `emp_last_name` VARCHAR(100) NOT NULL,
  `emp_gender` VARCHAR(10) NOT NULL,
  `emp_dob` DATE NOT NULL,
  `emp_doj` DATE NOT NULL,
  `emp_frn_designation` INT(11) NOT NULL,
  `emp_frn_contacts` INT(11) NOT NULL,
  `emp_blood_group` VARCHAR(25),
  `emp_resume` BLOB,
  `emp_is_active` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`emp_id`),
    INDEX `fk_designation_to_employee` (`emp_frn_designation` ASC),
    INDEX `fk_contacts_to_employee` (`emp_frn_contacts` ASC),
     CONSTRAINT `fk_designation_to_employee`
    FOREIGN KEY (`emp_frn_designation`)
    REFERENCES `rzt_management`.`im_designation` (`d_id`),
     CONSTRAINT `fk_contacts_to_employee`
    FOREIGN KEY (`emp_frn_contacts`)
    REFERENCES `rzt_management`.`im_contacts` (`ec_id`)
  );
  
  -- -----------------------------------------------------
-- Table `rzt_management`.`im_users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_users` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_users` (
  `u_id` INT(11) NOT NULL AUTO_INCREMENT,
  `u_emp_id` INT(11) NOT NULL,
  `u_username` VARCHAR(100) NOT NULL UNIQUE,
  `u_password` VARCHAR(250) NOT NULL,
  `u_is_admin` TINYINT(1) NULL DEFAULT NULL,
  `u_is_active` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  INDEX `fk_emp_id` (`u_emp_id` ASC),
  CONSTRAINT `fk_emp_id`
    FOREIGN KEY (`u_emp_id`)
    REFERENCES `rzt_management`.`im_employees` (`emp_id`));
    
-- -----------------------------------------------------
-- Table `rzt_management`.`im_client`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_client` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_client` (
  `c_id` INT(11) NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(100) NOT NULL,
  `c_is_active` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`c_id`)
 );
    
-- -----------------------------------------------------
-- Table `rzt_management`.`im_projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_projects` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_projects` (
  `p_id` INT(11) NOT NULL AUTO_INCREMENT,
  `p_name` VARCHAR(100) NOT NULL,
  `p_frn_client_id` INT(11) NULL DEFAULT NULL,
  `p_status` VARCHAR(100) NOT NULL,
  `p_repo` VARCHAR(150) NOT NULL,
  `p_is_active` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`), 
   INDEX `fk_client_to_project_id` (`p_frn_client_id` ASC),
  CONSTRAINT `fk_client_to_project_id`
    FOREIGN KEY (`p_frn_client_id`)
    REFERENCES `rzt_management`.`im_client` (`c_id`)
 );
 
 -- -----------------------------------------------------
-- Table `rzt_management`.`im_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_roles`;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_roles` (
  `r_id` INT(11) NOT NULL AUTO_INCREMENT,	
  `r_name` VARCHAR(50) NOT NULL ,
  `r_is_active` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`r_id`));
-- -----------------------------------------------------
-- Table `rzt_management`.`im_emp_to_projects`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rzt_management`.`im_emp_to_projects` ;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_emp_to_projects` (
  `ep_id` INT(11) NOT NULL AUTO_INCREMENT,
  `ep_frn_project` INT(11) NOT NULL,
  `ep_frn_emp` INT(11) NOT NULL,
  `ep_frn_role` INT(11) NOT NULL,
  `ep_is_active` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`ep_id`),  
  INDEX `fk_project` (`ep_frn_project` ASC),
  INDEX `fk_emp` (`ep_frn_emp` ASC),
  INDEX `fk_emp_to_role` (`ep_frn_role` ASC),
   CONSTRAINT `fk_project`
    FOREIGN KEY (`ep_frn_project`)
    REFERENCES `rzt_management`.`im_projects` (`p_id`),
     CONSTRAINT `fk_emp`
    FOREIGN KEY (`ep_frn_emp`)
    REFERENCES `rzt_management`.`im_employees` (`emp_id`),
	CONSTRAINT `fk_emp_to_role`
    FOREIGN KEY (`ep_frn_role`)
    REFERENCES `rzt_management`.`im_roles` (`r_id`)
 );