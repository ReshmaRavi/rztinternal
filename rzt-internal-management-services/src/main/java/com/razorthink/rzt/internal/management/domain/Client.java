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
@Table( name = "im_client" )
@NamedQueries( { @NamedQuery( name = "Client.findByClientName", query = "FROM Client c WHERE c.clientName=:clientName and c.isActive is true" ) })
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String clientName;
	private Address clientAddress;
	private Boolean isActive;

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "c_id" )
	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Column( name = "c_name" )
	public String getClientName()
	{
		return clientName;
	}

	public void setClientName( String clientName )
	{
		this.clientName = clientName;
	}

	@Column( name = "c_is_active" )
	public Boolean getIsActive()
	{
		return isActive;
	}

	public void setIsActive( Boolean isActive )
	{
		this.isActive = isActive;
	}

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "c_frn_address_id", referencedColumnName = "a_id" )
	public Address getClientAddress()
	{
		return clientAddress;
	}

	public void setClientAddress( Address clientAddress )
	{
		this.clientAddress = clientAddress;
	}

}
