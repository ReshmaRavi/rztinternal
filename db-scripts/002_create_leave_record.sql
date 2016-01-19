USE `rzt_management`;

-- -----------------------------------------------------
-- Table `rzt_management`.`im_leave_record`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `rzt_management`.`im_leave_record`;

CREATE TABLE IF NOT EXISTS `rzt_management`.`im_leave_record` (
  `lr_id` INT(11) NOT NULL AUTO_INCREMENT,	
  `lr_frn_emp_id` INT(11) NOT NULL,
  `lr_leave_date` DATE NOT NULL,
  `lr_leave_type` VARCHAR(50) NOT NULL,
  `lr_leave_reason` VARCHAR(200),
  `lr_leave_is_cancelled` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`lr_id`),
   INDEX `fk_employee_to_leave_id` (`lr_frn_emp_id` ASC),
   CONSTRAINT `fk_employee_to_leave_id`
    FOREIGN KEY (lr_frn_emp_id)
    REFERENCES `rzt_management`.`im_employees` (`emp_id`)
   );
   
   
  
  