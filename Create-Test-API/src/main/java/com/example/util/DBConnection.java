package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=production;encrypt=true;trustServerCertificate=true";

	private static final String USER = "aditya";
	private static final String PASSWORD = "test12";

	public static Connection getConnection() throws Exception {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

}
