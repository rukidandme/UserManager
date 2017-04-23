/**
 * 
 */
package com.stpeterlutheran.usermanager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.stpeterlutheran.common.dao.Group;
import com.stpeterlutheran.common.dao.User;
import com.stpeterlutheran.common.dao.Users;
import com.stpeterlutheran.common.util.XmlUtils;

/**
 * @author pvasquez
 *
 */
public class UserManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println( "St. Peter Lutheran User Managment Tool" +
				"\nThis tool is used to manage access to the Shared folder (\\\\Office\\SharedFolder$)." +
				"Use this tool to grant or remove access to the Shared folder (\\\\Office\\SharedFolder$)" +
				"for called workers.");

		// When group is instantiated return group members.
		String groupname = "Users";
		Group group = Group.getNewInstance( groupname );
		
		ArrayList<User> userlist = new ArrayList<User>();
		//User user = new User("Jane","Doe","jdoe2");
		userlist.add( new User( "Jane","Doe","jdoe2")  );
		userlist.add( new User("Jake","Doe","jdoe3") );
		userlist.add( new User("Jock","Doe","jdoe4") );
		group.setUsers( userlist );
	
		//group.setUsers(userlist);

		Iterator<User> iter = group.getUsers().iterator();
		while( iter.hasNext() ){
			User user  = iter.next();
			System.out.println( String.format( "firstName:%s lastName:%s userId:%s",
					user.getFirstName(), user.getLastName(), user.getUserId() ));
		}
		
		System.out.println( XmlUtils.toString( XmlUtils.toDom( group.getUsers() )));
	}

}
