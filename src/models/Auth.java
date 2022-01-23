package models;

import gui.LoginPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;


public class Auth {
	private int userId, deptId;
	private Connection con;
	
	public Auth(String u, String p) {

		userId = Integer.parseInt(u);
		PreparedStatement stmt;
		ResultSet rs;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
	
		try {
			stmt = con.prepareStatement("select emp_id, password_hash, dept_id from employee where emp_id = ? and password_hash = ?");
			stmt.setInt(1, userId);
			stmt.setString(2, p);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				 if(userId == rs.getInt("emp_id") && p.equals(rs.getString("password_hash"))) 
				 {
					 deptId = rs.getInt("dept_id");
				 }
			}
			else {
				JOptionPane.showMessageDialog(null, "Username or Password didn't match", "Failed Authenication", JOptionPane.INFORMATION_MESSAGE);
				new LoginPage().call();
			}
		}catch(Exception e) {System.out.println("Error in accessing database");}
		System.out.println(deptId);
		
		//new employeeMenu();
		
		try {
			stmt = con.prepareStatement("select supervisor_id from department where dept_id = ?");
			stmt.setInt(1, deptId);
			rs = stmt.executeQuery();	
			
			if(rs.next()) {
				if(userId == rs.getInt("supervisor_id"))
						new Supervisor(deptId);
				else
						new employeeMenu(deptId);
			}
			
		}catch(Exception e) {System.out.println("Error in accessing database");}
	}
}
