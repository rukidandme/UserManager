/**
 * 
 */
package com.usermanager.controlpanel;

import com.usermanager.dao.GroupMembers;
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
		
		GroupMembers groupmembers = GroupMembers.getInstance();
		
		try {
			String result[] = null;
			groupmembers.ResolveGroupMembers( "Administrators" );
			
			System.out.println( groupmembers.getGroupMembers().toString() );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//group.AddUserToLocalGroup( "MyTestGroup", "jasonbourne" );
		//group.AddUserToLocalGroup( "MyTestGroup", "jasonbourne" );
		//group.AddUserToGroup( "MyTestGroup", "johndoe" );
		//group.GetGroupMembers( "MyTestGroup" );

	}
}
