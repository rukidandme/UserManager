/**
 * 
 */
package com.stpeterlutheran.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pvasquez
 *
 */
public class Group {

	private static String groupname = "";
	private static Users users = new Users();
	private static Group INSTANCE_OBJECT = null;

	protected Group( String groupname ){
		this.groupname = groupname;
		INSTANCE_OBJECT = this;
	}

	public static Group getNewInstance( String groupname ){
		return new Group( groupname );
	}

	public static Group getIntance(){
		return INSTANCE_OBJECT;
	}
	//	public void test()
	//	{
	//		//		/members = new ComTst().runQuery("InstancesOf", "Win32_Group Where LocalAccount=True", "Name" );
	//
	//				String query = "$Computer=$env:COMPUTERNAME " +
	//						"\n$ADSIComputer=[ADSI]('WinNT://$Computer,computer')" +
	//						"\n$group=$ADSIComputer.psbase.children.find('%s', 'Group')" +
	//						"\n$group.psbase.invoke('members') | ForEach{ " +
	//						"\n$_.GetType().InvokeMember('Name', 'GetProperty',  $null,  $_, $null) } ";
	//				query=String.format( query,  groupname );
	//		
	//				ComTst comtst = new ComTst();
	//				ArrayList<String> members = (<ArrayList<String>) comtst.execQuery( query, "Name" );
	//
	//		ArrayList<String> members = (<ArrayList<String>) comtst.execQuery( query, "Name" );
	//		return members;
	//	}

//	public List<User> getMembers(){
//		return users.getUsers();
//	}
	
	public ArrayList<String> getMembers() {
		//List<String> members = new ComTst().runQuery("InstancesOf", "Win32_Group Where LocalAccount=True", "Name" );

		String query = "$Computer=$env:COMPUTERNAME " +
				"\n$ADSIComputer=[ADSI]('WinNT://$Computer,computer')" +
				"\n$group=$ADSIComputer.psbase.children.find('%s', 'Group')" +
				"\n$group.psbase.invoke('members') | ForEach{ " +
				"\n$_.GetType().InvokeMember('Name', 'GetProperty',  $null,  $_, $null) } ";
		query=String.format( query,  groupname );

		ComTst comtst = new ComTst();
		ArrayList<String> members = (ArrayList<String>) comtst.execQuery( query, "Name" );
	
		return members;
	}

	public List<User> getUsers(){
		return users.getUsers();
	}

	public void setUsers( List<User> userlist ){
		this.users.setUsers( userlist );
	}
}
