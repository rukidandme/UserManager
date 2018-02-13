/**
 * 
 */
package com.usermanager.dao;

import org.apache.logging.log4j.LogManager;

import com.usermanager.utilities.ShellCommand;

/**
 * @author sproc
 *
 */
public class User {

	private String command = "";

	public void Add( String username ) {

		command = String.format( "net user %s /add", username );
		String output = null;
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( "Unable to add user " + username );
			e.printStackTrace();
		}

		LogManager.getLogger().error( "Successfully added user " + username );
	}

	public void Delete( String username ) {

		command = String.format( "net user %s /delete", username );
		String output = null;
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( "Unable to delete user " + username );
			e.printStackTrace();
		}

		LogManager.getLogger().error( "Successfully deleted user " + username );
	}

	public void SetPassword( String username ) {

		command = String.format( "net user %s /delete", username );
		String output = null;
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( "Unable to set password for user " + username );
			e.printStackTrace();
		}

		LogManager.getLogger().error( "Successfully set password for user " + username );
	}

}
