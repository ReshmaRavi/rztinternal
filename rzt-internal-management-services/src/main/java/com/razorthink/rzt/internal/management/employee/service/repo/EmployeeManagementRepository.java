package com.razorthink.rzt.internal.management.employee.service.repo;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.razorthink.rzt.internal.management.domain.Employee;
import com.razorthink.utils.spring.repo.GenericRepository;

@Repository
public interface EmployeeManagementRepository extends GenericRepository<Employee, Serializable> {

}
