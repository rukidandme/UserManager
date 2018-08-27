package com.usermanager.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.omg.CORBA.portable.ApplicationException;

public class ShellCommand {

	public static String execute( String command ) throws Exception {
		
		String commandOutput = null;
		try {
			commandOutput = Execute2( command );
		}
		catch (Exception e) {
			LogManager.getLogger().error( String.format( "Exception executing command '%s'. %s", command, e.getMessage() ) );
			e.printStackTrace();
			throw new Exception( e );
		}
		
		return commandOutput;
	}

/*	public static String execute1(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}*/

	public static String Execute2( String command ) throws Exception {

		StringBuffer output = new StringBuffer();
		Process p = null;

		try {
			p = Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			throw new Exception( "IOException, executing command " + command );
		}

		//Wait to get exit value
		try {
			p.waitFor();
			final int exitValue = p.waitFor();
			
			if (exitValue == 0) {
				
				// Get any console messages.
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line = "";
				while ((line = reader.readLine())!= null) {
					output.append( line + "\n" );
				}
			}
			else {
				// Read error message sand include in thrown exception.
				
				try (final BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
					String line;
					while ((line = b.readLine())!= null) {
						output.append( line + "\n" );
					}
				} catch (final IOException e) {
					throw new Exception( "IOException reading command error. " + command  );
				}
				throw new Exception( output.toString() );
			}
		} catch (InterruptedException e) {
			throw new Exception( "InterruptedException, error waiting on thread. " + command  );
		}
		
		return output.toString();		
	}

}