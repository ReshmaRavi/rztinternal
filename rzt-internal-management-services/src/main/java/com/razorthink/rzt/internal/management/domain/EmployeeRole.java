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
@Table( name = "im_roles" )
@NamedQueries( {
		@NamedQuery( name = "EmployeeRole.findByName", query = "From EmployeeRole r WHERE r.name=:name and r.isActive is true" ),
		@NamedQuery(name="EmployeeRole.findAllRole", query="From EmployeeRole r WHERE r.isActive is true")})
public class EmployeeRole implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "r_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "r_name" )
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Column( name = "r_is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

}
