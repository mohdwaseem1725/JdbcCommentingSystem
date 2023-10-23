package com.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UtilApp {

	public static Connection getJdbcConnection() throws IOException, SQLException {
		FileInputStream fileInputStream = new FileInputStream(
				"D:\\Java Training\\JdbcCommentSystem\\application.properties");
		Properties properties = new Properties();
		properties.load(fileInputStream);

		String url = properties.getProperty("url");
		String user = properties.getProperty("username");
		String password = properties.getProperty("password");

		Connection connection = DriverManager.getConnection(url, user, password);

		return connection;
	}

	public static void closeResources(Connection con, PreparedStatement pstmt, ResultSet res) throws SQLException {
		if (con != null) {
			con.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (res != null) {
			res.close();
		}
	}
}
