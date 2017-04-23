package com.stpeterlutheran.common.dao;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;

public class ComTst {
	
	public static void main(String[] args) {

		System.setProperty( "jacob.dll.path","C:\\Workspaces\\luna\\UserManager\\UserManager\\resources\\lib\\jacob-1.18-x64.dll");

		ComThread.InitMTA();
		try {
			ActiveXComponent wmi = new ActiveXComponent("winmgmts:\\\\.");
			// Variant instances = wmi.invoke("InstancesOf", "Win32_SystemUsers"); 
			// Variant instances = wmi.invoke("InstancesOf", "Win32_Group Where LocalAccount=True");
			Variant instances = wmi.invoke("InstancesOf", "Win32_Group Where LocalAccount=True");


			Enumeration<Variant> en = new EnumVariant(instances.getDispatch());
			while (en.hasMoreElements())
			{
				ActiveXComponent bb = new ActiveXComponent(en.nextElement().getDispatch());
				System.out.println(bb.getPropertyAsString("Name"));
			}
		} finally {
			ComThread.Release();
		}
	}

	public ComTst()
	{
		System.setProperty( "jacob.dll.path","C:\\Workspaces\\luna\\UserManager\\UserManager\\resources\\lib\\jacob-1.18-x64.dll");
	}
	
	public List<String> runQuery( String actioncommand, String query, String filter ){
		
		List<String> resultlist = new ArrayList<String>();
		ComThread.InitMTA();
		try {
			ActiveXComponent wmi = new ActiveXComponent("winmgmts:\\\\.");
			Variant instances = wmi.invoke( actioncommand, query );
			
			Enumeration<Variant> en = new EnumVariant(instances.getDispatch());
			while (en.hasMoreElements())
			{
				ActiveXComponent bb = new ActiveXComponent(en.nextElement().getDispatch());
				String result = bb.getPropertyAsString( filter );
				System.out.println(bb.getPropertyAsString("Name"));
				resultlist.add( result );
			}
		} finally {
			ComThread.Release();
		}
		return resultlist;
	}

	public List<String> execQuery( String query, String filter ){
		
		List<String> resultlist = new ArrayList<String>();
		ComThread.InitMTA();
		try {
			ActiveXComponent wmi = new ActiveXComponent("winmgmts:\\\\.");
			//query = "SELECT * FROM Win32_PerfFormattedData_PerfOS_Processor WHERE Name='_Total'";
		    query="([ADSI]('WinNT://4JFKJC2,computer')).psbase.children.find('Users', 'Group').psbase.invoke('members') | ForEach{ $_.GetType().InvokeMember('Name', 'GetProperty',  $null,  $_, $null) }";
			Variant vcollection = wmi.invoke( "ExecQuery", query );
			
			EnumVariant enumVariant = new EnumVariant(vcollection.toDispatch());
			while (enumVariant.hasMoreElements())
			{
				Dispatch item = enumVariant.nextElement().toDispatch();
		        String result = Dispatch.call( item, filter ).toString();
		        
		        System.out.println( result );
				resultlist.add( result );
			}
		} finally {
			ComThread.Release();
		}
		return resultlist;
	}
	
	public List<String> test(){
		
		String query = "$Computer=$env:COMPUTERNAME " +
				"\n$ADSIComputer=[ADSI]('WinNT://$Computer,computer')" +
				"\n$group=$ADSIComputer.psbase.children.find('%s', 'Group')" +
				"\n$group.psbase.invoke('members') | ForEach{ " +
				"\n$_.GetType().InvokeMember('Name', 'GetProperty',  $null,  $_, $null) } ";
		query=String.format( query,  "Users" );

		
		List<String> resultlist = new ArrayList<String>();
		ComThread.InitMTA();
		try {
			ActiveXComponent wmi = new ActiveXComponent("winmgmts:\\\\.");
			//Variant instances = wmi.invoke( "IntancesOf", "$env:COMPUTERNAME" );
			Variant instances = wmi.invoke("InstancesOf", "Win32_GroupUser WHERE GroupComponent=\"Win32_Group.Domain='.',Name='Administrators'\""); 

			
			Enumeration<Variant> en = new EnumVariant(instances.getDispatch());
			while (en.hasMoreElements())
			{
				ActiveXComponent bb = new ActiveXComponent(en.nextElement().getDispatch());
				String result = bb.getPropertyAsString( "Name" );
				System.out.println(bb.getPropertyAsString("Name"));
				resultlist.add( result );
			}
		} finally {
			ComThread.Release();
		}
		return resultlist;
	}



}