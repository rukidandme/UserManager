package com.usermanager.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.omg.CORBA.portable.ApplicationException;

public class ShellCommand {

	public static String execute( String command ) throws Exception {
		Execute2( command );
		return "Some return result";
	}

	public static String execute1(String command) {

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

	}

	public static String Execute2( String command ) throws Exception {

		StringBuffer output = new StringBuffer();
		Process p = null;

		try {
			p = Runtime.getRuntime().exec(command);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		//Wait to get exit value
		try {
			p.waitFor();
			final int exitValue = p.waitFor();
			if (exitValue == 0) {
				System.out.println( String.format( "Executed the command: %s exit value: %s.", command, exitValue ));
				// Get any console messages.
				BufferedReader reader =
						new BufferedReader(new InputStreamReader(p.getInputStream()));

				String line = "";
				while ((line = reader.readLine())!= null) {
					output.append(line + "\n");
				}
			}
			else {
				output.append("Failed to execute the following command: " + command + " due to the following error(s):\n");
				try (final BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
					String line;
					if ((line = b.readLine()) != null)
						output.append(line + "\n");

				} catch (final IOException e) {
					e.printStackTrace();
				}
				throw new Exception( output.toString() );
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println( output.toString() );
		return output.toString();
				
	}

}