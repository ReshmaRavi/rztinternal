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
@Table( name = "im_designation" )
@NamedQueries( {
		@NamedQuery( name = "Designation.findByName", query = "From Designation d WHERE d.name=:name and d.isActive is true" ),
		@NamedQuery( name = "Designation.findAllDesignation", query = "From Designation d WHERE d.isActive is true" ) })
public class Designation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Calendar createdAt;
	private Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "d_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "d_name" )
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Column( name = "d_created_at" )
	public Calendar getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt( Calendar createdAt )
	{
		this.createdAt = createdAt;
	}

	@Column( name = "d_is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

}
