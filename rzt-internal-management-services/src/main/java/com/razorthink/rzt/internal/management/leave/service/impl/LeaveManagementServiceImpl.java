package com.razorthink.rzt.internal.management.leave.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.razorthink.rzt.internal.management.domain.LeaveRecords;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.rzt.internal.management.leave.service.LeaveManagementService;
import com.razorthink.rzt.internal.management.leave.service.repo.LeaveManagementRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class LeaveManagementServiceImpl implements LeaveManagementService {
	
	@Autowired
	private LeaveManagementRepository leaveManagementRepo;

	@Override
	public LeaveRecords createOrUpdateLeave(LeaveRecords leave) {
		LeaveRecords leaveEntity = leaveManagementRepo.save(leave);
		if (leaveEntity == null)
			throw new DataException("data.error", "Could not save leave entity");
		return leaveEntity;
	}

	@Override
	public Boolean removeLeave(Integer id) {
		LeaveRecords leave = leaveManagementRepo.findOne(id);
		if (leave == null)
			throw new DataException("data.error", "Could not find leave entity with id: " + id);
		leave.setIsCancelled(true);
		LeaveRecords leaveEntity = leaveManagementRepo.save(leave);
		if (leaveEntity == null)
			return false;
		return true;
	}

	@Override
	public List<LeaveRecords> getAllLeaveByEmpId(Integer empId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", params);
		return leaveManagementRepo.findByNamedQueryAndParams("LeaveRecords.findLeavesById", params);
	}

	@Override
	public List<LeaveRecords> getAllLeaveByEmpIdAndLeaveType(Integer empId, String leaveType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empId", params);
		params.put("leaveType", leaveType);
		return leaveManagementRepo.findByNamedQueryAndParams("LeaveRecords.findLeavesByIdAndLeaveType", params);
	}

}
