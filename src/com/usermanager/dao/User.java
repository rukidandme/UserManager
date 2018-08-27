/**
 * 
 */
package com.usermanager.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.*;

import org.apache.logging.log4j.LogManager;

import com.usermanager.utilities.ShellCommand;

/**
 * @author Paul Vasquez
 *
 */
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {
	
	/**
	 * UserID
	 */
	@XmlPath("userName/text()")
	private String userName;
	
	@XmlPath("password/text()")
	private String password;
	
	@XmlPath("fullName/text()")
	private String fullName;
		
	@XmlPath("accountActive/text()")
	private String accountActive;
	
	@XmlPath("localGroupMemberships/text()")
	private String localGroupMemberships;

	public User() {
	}

	/**
	 * Initialize and existing User object given the User Name by
	 * querying the user attributes using the 'net user' command.
	 * @param userid
	 * @throws Exception
	 */
	User( String userid ) throws Exception {
		
		/* SHELL COMMANDS */
		String commandOutput = null;
		try {
			commandOutput = ShellCommand.execute( String.format( "net user %s", userid ) );
			
			/*
			 * Get the account debug.
			 * Create list object from the user attributes. Use split method to isolate the user account attributes by splitting
			 * the output on 'new line' character.
			 */
			// TODO - This will work until Microsoft changes the output format.
			List<String> attributeslist = Arrays.asList( 
					commandOutput.split( "(\\n)|(The command completed successfully.)" ) );
			
			// Convert list to map.
			Map<String,String> map = new HashMap<String,String>();
			for (String i : attributeslist) {
				String[] x = i.split( "( ){2,}" );
				// Initialize empty attributes, like Full Name.
				map.put( x[0], 
						x.length == 1 ? "" : x[1] );
			}	
			
			// Finally, set the user objects attributes from the map.
			this.userName = map.get("User Name");
			this.fullName = map.get("Full Name");
			this.accountActive = map.get("Account active");
			this.localGroupMemberships = map.get("Local Group Memberships");
			
		} catch (Exception e) {
			LogManager.getLogger().error( String.format( "Error accessing account debugrmation for user %s. %s", userid, e.getMessage() ) );
			e.printStackTrace();
			throw new Exception( e );
		}
		
		LogManager.getLogger().debug( "Successfully looked up group members for " + userid );
	}
	
	/**
	 * Get first and last name of the user.
	 * @return
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Set the first and last name of the user.
	 * @return
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Get User ID
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set User ID
	 * @return
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAccountActive() {
		return accountActive;
	}
	
	public String getLocalGroupMemberships() {
		return localGroupMemberships;
	}

	/**
	 * Creates a new user account from the instantiated user object.
	 * 
	 */
	public void createNewUser() {

		try {
			/* To set user full name while creating the user account:
			 * net user username password /ADD /FULLNAME:"User_Fullname"
			 */
			String commandOutput = ShellCommand.execute( 
					String.format( "net user %s %s /add /fullname:'%s'",
							getUserName(), this.password, getFullName() ) );
		}
		catch ( Exception e) {
			e.printStackTrace();
		}
		
		LogManager.getLogger().debug(
				String.format( "Successfully added user. Userid:%s password:%s fullname: %s",
						getUserName(), this.password, getFullName() ) );
	}

	public void deleteUser( String username ) {

		String command = String.format( "net user %s /delete", username );
		String output = null;
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( "Unable to delete user " + username );
			e.printStackTrace();
		}

		LogManager.getLogger().debug( "Successfully deleted user " + username );
	}

	public void changePassword( String password ) {
		
		/* net user USERNAME NEWPASS */
		String commandOutput = null;
		try {
			commandOutput = ShellCommand.execute(  String.format( "net user %s %s",
					getUserName(), password ));
		} catch (Exception e) {
			LogManager.getLogger().error( "Unable to set password for user " + getUserName() );
			e.printStackTrace();
		}

		LogManager.getLogger().debug( "Successfully set password for user " + getUserName() );
	}

}
