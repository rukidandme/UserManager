/**
 * 
 */
package com.usermanager.controlpanel;

import com.usermanager.dao.Group;
import com.usermanager.utilities.Configuration;

/**
 * @author sproc
 *
 */
public class Dashboard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration config = new Configuration();
		
		Group group = new Group();
		
		group.GetGroupMembers( "MyTestGroup" );
		//group.AddUserToLocalGroup( "MyTestGroup", "jasonbourne" );
		//group.AddUserToLocalGroup( "MyTestGroup", "jasonbourne" );
		//group.AddUserToGroup( "MyTestGroup", "johndoe" );
		//group.GetGroupMembers( "MyTestGroup" );

	}
}
