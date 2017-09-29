package com.breaker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {

	private Connection connection;
	
	public Connection getDbCon() throws ClassNotFoundException, SQLException{
		
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/ncdc";
		String uname = "root";
		String pwd = "root";
		Class.forName(driverName);
		connection = DriverManager.getConnection(url,uname,pwd);
		return connection;
		
	}

}
