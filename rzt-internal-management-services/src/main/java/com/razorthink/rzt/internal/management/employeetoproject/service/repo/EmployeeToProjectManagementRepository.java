package com.razorthink.rzt.internal.management.employeetoproject.service.repo;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.razorthink.rzt.internal.management.domain.EmployeeToProject;
import com.razorthink.utils.spring.repo.GenericRepository;

@Repository
public interface EmployeeToProjectManagementRepository extends GenericRepository<EmployeeToProject, Serializable> {

}
