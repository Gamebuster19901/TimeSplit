package com.gamebuster19901.timesplit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	static {
		checkJavaVersion();
	}
	
	static final String VERSION = "0.0.0.0";
	static File WORKING_DIRECTORY = new File(System.getProperty("user.dir"));
	
	static {
		try {
			checkWorkingDirectory();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}
	
	/**
	 * args[0]: path of working directory
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		if(args != null && args.length > 0) {
			WORKING_DIRECTORY = new File(args[0]);
			checkWorkingDirectory();
		}
		launch(args);
	}
	
	private static void checkWorkingDirectory() throws IOException {
		if(!WORKING_DIRECTORY.exists()) {
			throw new FileNotFoundException("Unable to locate the current working directory: " + System.getProperty("user.dir"));
		}
		if(!WORKING_DIRECTORY.isDirectory()) {
			throw new IOException("Working directory is a file: " + System.getProperty("user.dir"));
		}
	}
	
	public static File getWorkingDirectory() {
		return WORKING_DIRECTORY;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		checkJavaVersion();
		
		primaryStage.setTitle("Timesplit");
		primaryStage.show();
	}
	
	private static void checkJavaVersion() {
		try {
			Method runtime_version = Runtime.class.getMethod("version");
			Object version = runtime_version.invoke(null);
			Method version_major = runtime_version.getReturnType().getMethod("major");
			int majorVersion = (int) version_major.invoke(version);
		   
			if(majorVersion < 9) {
				throw new ClassFormatError("Incompatable JVM version " + majorVersion + ". Upgrade to Java 9 or above");
			}
		} catch (Exception ex) {//ReflectiveOperationException` does not exist in Java 6 or below
			// before Java 9 system property 'java.specification.version'
			// is of the form '1.major', so return the int after '1.'
			String versionString = System.getProperty("java.specification.version");
			throw new ClassFormatError("Incompatable JVM version " + Integer.parseInt(versionString.substring(2)) + ". Upgrade to Java 9 or above");
		}
	}
	
}
