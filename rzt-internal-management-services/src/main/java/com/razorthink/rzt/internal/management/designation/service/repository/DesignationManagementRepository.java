package com.razorthink.rzt.internal.management.designation.service.repository;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.razorthink.rzt.internal.management.domain.Designation;
import com.razorthink.utils.spring.repo.GenericRepository;

public interface DesignationManagementRepository  extends GenericRepository<Designation, Serializable> {

}
