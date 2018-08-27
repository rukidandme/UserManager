/**
 * 
 */
package com.usermanager.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.filter.RegexFilter;
import org.omg.CORBA.portable.ApplicationException;

import com.usermanager.utilities.ShellCommand;

/**
 * @author pvasquez
 * 
 *
 */
public class Group extends User {
	
	private static String groupRegex = "^([a-zA-Z0-9]+)([a-zA-Z0-9\\-]*)\\\\*([a-zA-Z0-9\\-]*)";
	private static String headerRegex = "([.*\\n]*--+)";
	private static String footerRegex = "(\\n^The command completed successfully\\n*)";

	public Group(){
		//;
	}
}
