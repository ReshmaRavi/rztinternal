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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "im_projects" )
@NamedQueries({
	@NamedQuery(name = "Project.findByName", query = "FROM Project p WHERE p.name=:projectName and p.isActive is true"),
	@NamedQuery(name = "Project.findAllProjects", query = "FROM Project p WHERE p.isActive is true") })
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Client client;
	private String status;
	private String repository;
	private Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "p_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "p_name" )
	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "p_frn_client_id", referencedColumnName = "c_id" )
	public Client getClient()
	{
		return client;
	}

	public void setClient( Client client )
	{
		this.client = client;
	}

	@Column( name = "p_status" )
	public String getStatus()
	{
		return status;
	}

	public void setStatus( String status )
	{
		this.status = status;
	}

	@Column( name = "p_repo" )
	public String getRepository()
	{
		return repository;
	}

	public void setRepository( String repository )
	{
		this.repository = repository;
	}

	@Column( name = "p_is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	
	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

	

}
