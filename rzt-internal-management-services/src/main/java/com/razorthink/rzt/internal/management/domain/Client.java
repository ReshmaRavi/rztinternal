package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "im_client")
@NamedQueries({
	@NamedQuery(name = "Client.findByClientName", query = "FROM Client c WHERE c.clientName=:clientName and c.isActive is true"),
	@NamedQuery(name = "Client.findAllClients", query = "FROM Client c WHERE c.isActive is true") })
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String clientName;
	private List<Address> clientAddress;
	private Boolean isActive;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "c_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "c_name")
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	


	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name="im_client_to_address",joinColumns=@JoinColumn(name="c_id"),inverseJoinColumns=@JoinColumn(name="a_id"))
	public List<Address> getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(List<Address> clientAddress) {
		this.clientAddress = clientAddress;
	}

	@Column(name = "c_is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}



}
