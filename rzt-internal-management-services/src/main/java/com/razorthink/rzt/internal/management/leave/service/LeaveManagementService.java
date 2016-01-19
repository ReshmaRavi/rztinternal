package com.razorthink.rzt.internal.management.leave.service;

import java.util.List;
import com.razorthink.rzt.internal.management.domain.LeaveRecords;

public interface LeaveManagementService {

	public LeaveRecords createOrUpdateLeave( LeaveRecords leave );

	public Boolean removeLeave( Integer id );

	public List<LeaveRecords> getAllLeaveByEmpId( Integer empId );

	public List<LeaveRecords> getAllLeaveByEmpIdAndLeaveType( Integer empId, String leaveType );

}
