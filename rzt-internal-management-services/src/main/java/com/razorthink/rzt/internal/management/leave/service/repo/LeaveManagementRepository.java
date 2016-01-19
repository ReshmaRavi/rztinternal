package com.razorthink.rzt.internal.management.leave.service.repo;

import java.io.Serializable;
import com.razorthink.rzt.internal.management.domain.LeaveRecords;
import com.razorthink.utils.spring.repo.GenericRepository;

public interface LeaveManagementRepository extends GenericRepository<LeaveRecords, Serializable> {

}
