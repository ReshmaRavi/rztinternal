package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "im_leave_record" )
@NamedQueries( {
		@NamedQuery( name = "LeaveRecords.findLeavesById", query = "FROM LeaveRecords lr where lr.employeeId=:empId and lr.isCancelled is false" ),
		@NamedQuery( name = "LeaveRecords.findLeavesByIdAndLeaveType", query = "FROM LeaveRecords lr where lr.employeeId=:empId and lr.leaveType=:leaveType and lr.isCancelled is false  " ) })
public class LeaveRecords implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer employeeId;
	private Calendar date;
	private String leaveType;
	private String leaveReason;
	private Boolean isCancelled;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "lr_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "lr_leave_date" )
	public Calendar getDate()
	{
		return date;
	}

	public void setDate( Calendar date )
	{
		this.date = date;
	}

	@Column( name = "lr_leave_type" )
	public String getLeaveType()
	{
		return leaveType;
	}

	public void setLeaveType( String leaveType )
	{
		this.leaveType = leaveType;
	}

	@Column( name = "lr_leave_reason" )
	public String getLeaveReason()
	{
		return leaveReason;
	}

	public void setLeaveReason( String leaveReason )
	{
		this.leaveReason = leaveReason;
	}

	@Column( name = "lr_emp_id" )
	public Integer getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId( Integer employeeId )
	{
		this.employeeId = employeeId;
	}

	@Column( name = "lr_leave_is_cancelled" )
	public Boolean getIsCancelled()
	{
		return isCancelled;
	}

	public void setIsCancelled( Boolean isCancelled )
	{
		this.isCancelled = isCancelled;
	}

}
