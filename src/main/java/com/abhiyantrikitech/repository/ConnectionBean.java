package com.abhiyantrikitech.repository;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionBean {

	String driver = "org.postgresql.Driver";

	@Value("${neondb.url}")
	private String url;

	@Value("${neondb.username}")
	private String username;

	@Value("${neondb.password}")
	private String password;



	public Connection getConnectionObj() {
	    Connection conn = null;
	   // System.out.println(url);
	   // System.out.println(username);
	   // System.out.println(password);
	    
	    try {
	        Class.forName(driver);
	        conn = DriverManager.getConnection(url, username, password);
	        System.out.println("Connected to Neon!");
	    } catch (Exception e) {
	        System.out.println("Exception in connection: " + e);
	        e.printStackTrace();
	    }
	    return conn;
	}

}
