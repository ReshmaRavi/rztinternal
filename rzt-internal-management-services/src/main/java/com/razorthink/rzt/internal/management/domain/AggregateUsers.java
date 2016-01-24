package com.razorthink.rzt.internal.management.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AggregateUsers
{
    
    private Integer id;
    private String username;
    private String employeeNum;
    private String firstName;
    private String lastName;
    private Boolean isAdmin;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    @Column(name="u_username")
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    @Column(name = "emp_eno")
    public String getEmployeeNum()
    {
        return employeeNum;
    }
    public void setEmployeeNum(String employeeNum)
    {
        this.employeeNum = employeeNum;
    }
    @Column(name = "emp_first_name")
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    @Column(name = "emp_last_name")
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    @Column(name = "u_is_admin")
    public Boolean getIsAdmin()
    {
        return isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }
}
