/**
 * 
 */
package com.usermanager.dao;

import org.apache.logging.log4j.LogManager;

import com.usermanager.utilities.ShellCommand;

/**
 * @author pvasquez
 * 
 *
 */
public class Group extends User {

	public Group(){
		//;
	}
	
	public void AddLocalGroup( String groupname ) {

		String command = String.format( "net localgroup %s /add", groupname );
		
		String output = "";
		try {
			output = ShellCommand.execute( command );
			
		} catch (Exception e) {
			LogManager.getLogger().error( String.format( "Unable to add local group %s. %s.\n%s", groupname, e.getMessage(), output ));
			e.printStackTrace();
			return;
		}
		
		LogManager.getLogger().error( "Successfully added group " + groupname );
	}

	public void DeleteLocalGroup( String groupname ) {

		String command = String.format( "net localgroup %s /delete", groupname );
		
		String output = "";
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( String.format( "Unable to delete group %s. %s\n%s", groupname, e.getMessage(), output ) );
			e.printStackTrace();
		}

		LogManager.getLogger().error( "Successfully deleted group " + groupname );
	}

	public String GetGroupMembers( String groupname ) {

		String command = String.format( "net localgroup %s", groupname );

		String output = "";
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( String.format( "Unable to lookup group members for group %s. %s\n%s", groupname, e.getMessage(), output ) );
			e.printStackTrace();
		}
		
		LogManager.getLogger().info( "Successfully looked up group members for " + groupname );
		
		return output;
	}
	
	public void AddUserToLocalGroup( String groupname, String username ) {

		String command = String.format( "net localgroup %s %s /add", groupname, username );

		String output = "";
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger().error( String.format( "Unable to add user %s to group %s.", username, groupname ) );
			//e.printStackTrace();
			return;
		}
		
		LogManager.getLogger().error( String.format( "Successfully added user %s to group %s.", username, groupname ) );
	}

	public void RemoveUserFromLocalGroup( String groupname, String username ) {

		String command = String.format( "net localgroup %s %s /delete", groupname, username );

		String output = "";
		try {
			output = ShellCommand.execute( command );
		} catch (Exception e) {
			LogManager.getLogger( String.format( "Unable to remove user %s from group %s. %s\n%s", username, groupname, e.getMessage(), output ) );
			e.printStackTrace();
		}
		
		LogManager.getLogger( String.format( "Successfully removed user %s from group %s.", username, groupname ) );
	}
}
