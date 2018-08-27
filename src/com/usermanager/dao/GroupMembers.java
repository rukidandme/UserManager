/**
 * 
 */
package com.usermanager.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.logging.log4j.LogManager;

import com.usermanager.utilities.ShellCommand;

/**
 * @author sproc
 *
 */
@XmlRootElement( name="groupmembers" )
@XmlAccessorType (XmlAccessType.FIELD)
public class GroupMembers {
	
	private static GroupMembers INSTANCE_OBJECT = null;
	
	@XmlElement( name="user" )
	private List<User> groupmembers = null;
	
	/**
	 * Default constructor
	 */
	protected GroupMembers(){
		INSTANCE_OBJECT = this;
		this.setGroupMembers( new ArrayList<User>() );
	}

	/**
	 * Returns a new instance of GroupMembers
	 * @param path
	 * @return GroupMembers instance.
	 */
	public static GroupMembers getInstance(){
		if( INSTANCE_OBJECT == null )
			new GroupMembers();
		return INSTANCE_OBJECT;
	}
	
	public List<User> getGroupMembers()
	{
		return this.groupmembers;
	}

	public void setGroupMembers( List<User> groupmembers )
	{
		this.groupmembers = groupmembers;
	}

/*	public User findUser( String groupname, String userid )
	{
		User user = null;
		
		for( User u : this.groupmembers ) {
			if(u.getGroupName().equalsIgnoreCase( groupname ) &&
					u.getUserId().equalsIgnoreCase( userid ) )
				user = u;
		}
		
		return user;
	}*/

	
	
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

	/**
	 * Resolve group members by group name.
	 * @param groupname
	 * @throws Exception
	 */
	public void ResolveGroupMembers( String groupname ) throws Exception {

		/* SHELL COMMANDS */
		// GET USER NAMES
		String localgroupShellCmd 	= "net localgroup %s";
		String netuserShellCmd 		= "net user %s";

		String commandOutput = null;
		try {
			commandOutput = ShellCommand.execute( String.format( localgroupShellCmd, groupname ) );
			
			// Use split method to isolate the group members by splitting the output into 3 parts.
			// TODO - This will work until Microsoft changes the output format.
			String[] parsedOutput = commandOutput.split("----------*[\\n]|(The command completed successfully.\\n)");
			
			List<String> userList = Arrays.asList( 
					parsedOutput[1].split( "\\n" ));
			
			for (String userid : userList) {
				User user = new User(userid);
				getGroupMembers().add(user);	
			}
			
			/*// Use the Split method each group member to individual array elements.
			String[] resultarray = null;
			if (!parsedOutput[1].isEmpty()) {
				resultarray = parsedOutput[1].split("\\n");
			}*/
			
			// Add users to GroupMembers object.
			// Save artifact to missing artifacts
			/*for (int i = 0; i < resultarray.length; i++) {
				
				User user = new User();
				user.setUserId( resultarray[i] );
				//groupmember.setUserName( ??? );
				this.groupmembers.add( user );
			}*/
			
		} catch (Exception e) {
			
			LogManager.getLogger().error( String.format( "Unable to lookup group members for group %s. %s\n%s", groupname, e.getMessage(), commandOutput ) );
			e.printStackTrace();
			throw new Exception( e.getCause() );
		}
		
		LogManager.getLogger().info( "Successfully looked up group members for " + groupname );
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
