package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "im_users")
@NamedQueries({ @NamedQuery(name = "Users.findByEmployeeId", query = "FROM Users u WHERE u.employeeId=:employeeId and u.isActive is true"),
		@NamedQuery(name = "Users.findByEmailId", query = "SELECT u FROM Users u, Employee e,Contacts c WHERE u.employeeId=e.id and e.contactDetails=c.id and c.officeEmail=:email and u.isActive is true"),
		@NamedQuery(name = "Users.findByUserNameAndPassword", query = "FROM Users u WHERE u.username=:username and u.password=:password and u.isActive is true")})
public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer employeeId;
	private String username;
	private String password;
	private Boolean isAdmin;
	private Boolean isActive;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "u_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "u_emp_id")
	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name="u_username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "u_password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "u_is_admin")
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "u_is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
