USE `rzt_management`;

-- -----------------------------------------------------
-- Table `rzt_management`.`im_leave_record`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rzt_management`.`im_employee_to_adress`;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_employee_to_adress` (
  `empa_id` INT(11) NOT NULL AUTO_INCREMENT,	
  `emp_id` INT(11) NOT NULL,
  `a_id` INT(11) NOT NULL,
   PRIMARY KEY (`empa_id`),
   INDEX `fk_employee_to_address_id` (`emp_id` ASC),
   CONSTRAINT `fk_employee_to_address_id`
    FOREIGN KEY (emp_id)
    REFERENCES `rzt_management`.`im_employees` (`emp_id`),
      INDEX `fk_address_to_client_id` (`a_id` ASC),
   CONSTRAINT `fk_address_to_client_id`
    FOREIGN KEY (a_id)
    REFERENCES `rzt_management`.`im_address` (`a_id`)
   );
   
   
  
  