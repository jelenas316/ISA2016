package com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ibatis.common.jdbc.ScriptRunner;

@SpringBootApplication
public class IsaProjectApplication {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		String aSQLScriptFilePath = "src/main/resources/script.sql";

		// Create MySql Connection
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/isa2016?useSSL=false", "root", "root");

		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(con, false, false);

			// Give the input file to Reader
			Reader reader = new BufferedReader(
                               new FileReader(aSQLScriptFilePath));

			// Exctute script
			sr.runScript(reader);

		} catch (Exception e) {
			System.err.println("Failed to Execute" + aSQLScriptFilePath
					+ " The error is " + e.getMessage());
		}
		
		
		SpringApplication.run(IsaProjectApplication.class, args);
	}
}
