package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table( name = "im_emp_to_projects" )
public class EmployeeToProject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Project project;
	private Employee employee;
	private EmployeeRole role;
	private Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "ep_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ep_frn_project", referencedColumnName = "p_id" )
	public Project getProject()
	{
		return project;
	}

	public void setProject( Project project )
	{
		this.project = project;
	}

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ep_frn_emp", referencedColumnName = "emp_id" )
	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee( Employee employee )
	{
		this.employee = employee;
	}

	@OneToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ep_frn_role", referencedColumnName = "r_id" )
	public EmployeeRole getRole()
	{
		return role;
	}

	public void setRole( EmployeeRole role )
	{
		this.role = role;
	}

	@Column( name = "ep_is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

}
