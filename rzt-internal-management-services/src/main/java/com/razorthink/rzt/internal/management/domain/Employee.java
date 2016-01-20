package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "im_employees")
@NamedQueries({ @NamedQuery(name = "Employee.findByEmpNum", query = "FROM Employee e WHERE e.employeeNum=:empNum and e.isActive is true"),
		@NamedQuery(name = "Employee.findAllEmployees", query = "FROM Employee e WHERE e.isActive is true"), })
@NamedNativeQueries({ @NamedNativeQuery(name = "Employee.findAllEmployeesMinDetail", query = "select emp_eno,emp_first_name,emp_last_name from im_employees where emp_is_active=true") })
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String employeeNum;
	private String firstName;
	private String lastName;
	private String gender;
	private Calendar dateOfBirth;
	private Calendar dateOfJoining;
	private List<Address> empAddress;
	private Designation designation;
	private Contacts contactDetails;
	private String bloodGroup;
	private byte[] data;
	private Boolean isActive;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "emp_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "emp_eno", nullable = false)
	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	@Column(name = "emp_first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "emp_last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "emp_gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "emp_dob")
	public Calendar getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Calendar dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "emp_doj")
	public Calendar getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Calendar dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_frn_designation", referencedColumnName = "d_id")
	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "im_employee_to_adress", joinColumns = @JoinColumn(name = "emp_id") , inverseJoinColumns = @JoinColumn(name = "a_id") )
	public List<Address> getEmpAddress() {
		return empAddress;
	}

	public void setEmpAddress(List<Address> empAddress) {
		this.empAddress = empAddress;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "emp_frn_contacts", referencedColumnName = "ec_id")
	public Contacts getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(Contacts contactDetails) {
		this.contactDetails = contactDetails;
	}

	@Column(name = "emp_resume")
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Column(name = "emp_is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "emp_blood_group")
	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

}
