package com.comment.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.Util.UtilApp;

public class Main {

	public static void main(String[] args) throws SQLException {
		Scanner scanner = new Scanner(System.in);

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;

		try {
			connection = UtilApp.getJdbcConnection();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

		while (true) {
			System.out.println("***Comment System***");
			System.out.println("Select an Option");
			System.out.println("1. Enter Comment");
			System.out.println("2. View Comments");
			System.out.println("3. Remove a Comment");
			System.out.println("4. Remove all Comments");
			System.out.println("5. Exit");

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				scanner.nextLine();
				System.out.print("Enter Name: ");
				String name = scanner.nextLine();
				System.out.print("Enter Comment: ");
				String comment = scanner.nextLine();

				String sqlInsertQuery = "insert into commentstable (name, comment) values(?,?)";
				if (connection != null) {
					pstmt = connection.prepareStatement(sqlInsertQuery);
				}

				pstmt.setString(1, name);
				pstmt.setString(2, comment);

				pstmt.executeUpdate();
				System.out.println("Added the comment to the post");
				break;

			case 2:
				System.out.println("Listing All the Comments");
				String sqlSelectQuery = "Select * from commentstable";
				pstmt = connection.prepareStatement(sqlSelectQuery);
				resultSet = pstmt.executeQuery();

				while (resultSet.next()) {
					String nameString = resultSet.getString("name");
					String commeString = resultSet.getString("comment");

					System.out.println("Name: " + nameString + " Comment: " + commeString);
					System.out.println();
				}
				break;

			case 3:
				scanner.nextLine();
				System.out.print("Enter the Name to Remove the Comment: ");
				String removeName = scanner.nextLine();

				String sqlDeleteQuery = "Delete from commentstable where name = ?";
				pstmt = connection.prepareStatement(sqlDeleteQuery);
				pstmt.setString(1, removeName);
				int rowsDeleted = pstmt.executeUpdate();

				if (rowsDeleted > 0) {
					System.out.println("Comment Removed!");
				} else {
					System.out.println("Comment Not Found");
				}
				break;

			case 4:
				System.out.println("Removing All Comments");
				String sqlDeleteAll = "Delete from commentstable";
				pstmt = connection.prepareStatement(sqlDeleteAll);
				int rowsDeletedAll = pstmt.executeUpdate();

				if (rowsDeletedAll > 0) {
					System.out.println("All Comments Removed");
				} else {
					System.out.println("Comments Not Found");
				}
				break;

			case 5:
				System.err.println("Exited...");
				UtilApp.closeResources(connection, pstmt, resultSet);
				System.exit(0);
			default:
				System.out.println("Invalid Choice");
				break;
			}
		}
	}

}
