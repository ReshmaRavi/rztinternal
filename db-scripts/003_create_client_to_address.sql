USE `rzt_management`;

-- -----------------------------------------------------
-- Table `rzt_management`.`im_leave_record`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rzt_management`.`im_client_to_address`;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_client_to_address` (
  `cta_id` INT(11) NOT NULL AUTO_INCREMENT,	
  `c_id` INT(11) NOT NULL,
  `a_id` INT(11) NOT NULL,
   PRIMARY KEY (`cta_id`),
   INDEX `fk_client_id` (`c_id` ASC),
   CONSTRAINT `fk_client_id`
    FOREIGN KEY (c_id)
    REFERENCES `rzt_management`.`im_client` (`c_id`),
    INDEX `fk_address_id` (`a_id` ASC),
   CONSTRAINT `fk_address_id`
    FOREIGN KEY (a_id)
    REFERENCES `rzt_management`.`im_address` (`a_id`)
   );
   
   
  
  