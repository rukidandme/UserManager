package com.stpeterlutheran.common.dao;

import java.util.Enumeration;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
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
}