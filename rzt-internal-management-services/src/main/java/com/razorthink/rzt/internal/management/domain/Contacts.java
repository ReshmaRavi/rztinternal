package com.razorthink.rzt.internal.management.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "im_contacts")
public class Contacts implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String personamEmail;
	private String officeEmail;
	private String contactNumber;
	private String emergencyContactNum;
	private String skypeId;
	private String slackId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ec_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ec_personal_email")
	public String getPersonamEmail() {
		return personamEmail;
	}

	public void setPersonamEmail(String personamEmail) {
		this.personamEmail = personamEmail;
	}

	@Column(name = "ec_office_email")
	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	@Column(name = "ec_contact_num")
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Column(name = "ec_emergency_contact_num")
	public String getEmergencyContactNum() {
		return emergencyContactNum;
	}

	public void setEmergencyContactNum(String emergencyContactNum) {
		this.emergencyContactNum = emergencyContactNum;
	}

	@Column(name = "ec_skype_id")
	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	@Column(name = "ec_slack_id")
	public String getSlackId() {
		return slackId;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

}
