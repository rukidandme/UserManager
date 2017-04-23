/**
 * 
 */
package com.stpeterlutheran.common.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author PVASQUEZ
 *
 */
@XmlRootElement( name= "user" )
@XmlAccessorType (XmlAccessType.FIELD)
public class User {

	private String firstName = "";
	private String lastName = "";
	private String userId = "";

	public User() {
	}
	
	public User( String firstName, String lastName, String userId ){
		setFirstName(firstName);
		setLastName(lastName);
		setUserId(userId);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
