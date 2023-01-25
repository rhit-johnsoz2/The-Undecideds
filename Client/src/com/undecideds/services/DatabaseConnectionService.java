package com.undecideds.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionService {

	//DO NOT EDIT THIS STRING, YOU WILL RECEIVE NO CREDIT FOR THIS TASK IF THIS STRING IS EDITED
	private static final String SampleURL = "jdbc:sqlserver://${dbServer};databaseName=${dbName};user=${user};password={${pass}}";

	private static Connection connection = null;

	private static String databaseName;
	private static String serverName;

	public static void InitDatabaseConnectionService(String serverName, String databaseName) {
		DatabaseConnectionService.serverName = serverName;
		DatabaseConnectionService.databaseName = databaseName;
	}

	public static boolean connect(String user, String pass) {
		//TODO: Task 1
		//BUILD YOUR CONNECTION STRING HERE USING THE SAMPLE URL ABOVE
		String URL = SampleURL.replace("${dbServer}", serverName)
				.replace("${dbName}", databaseName)
				.replace("${user}", user)
				.replace("${pass}", pass);
		try {
			connection = DriverManager.getConnection(URL);
			return true;
		}catch (Exception e){
			System.out.println(e);
		}
		return false;
	}
	
	public static Connection getConnection(){
		return connection;
	}
	
	public static void closeConnection() {
		//TODO: Task 1
		try {
			connection.close();
		}catch (Exception e){
			System.out.println("failed to close");
		}
	}

}
