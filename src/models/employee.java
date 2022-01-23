package models;

import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.xdevapi.Statement;

public class employee {
	
    public String first_name;
    public String last_name;
    public int age;
    public String email;
    public long contact_number;
    public int dept_id;
    private Connection con;
    PreparedStatement pst;
    java.sql.Date sqlDate; 
    
    DefaultTableModel df;
    

    public employee(String first_name, String last_name, int age, String email, long contact_number, int dept_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.email = email;
        this.contact_number = contact_number;
        this.dept_id = dept_id;
        Date date=new Date();
        this.sqlDate = new java.sql.Date(date.getTime());   		
    }
    
    public employee(int id)
    {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select * from employee where emp_id = ? ";    
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			//System.out.println(rs);
			while(rs.next())
            {
		        this.first_name = rs.getString("first_name");
		        //System.out.println(first_name);
		        this.last_name = rs.getString("last_name");
		        this.age = rs.getInt("age");
		        this.email = rs.getString("email");
		        this.contact_number = rs.getLong("contact_num");
		        this.dept_id = rs.getInt("dept_id");
		        //Date date=new Date();
		        this.sqlDate = rs.getDate("hire_date"); 
            }
			System.out.println(contact_number);
	        con.close();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}    
	}
    
    public void insert() {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "INSERT INTO employee (first_name, last_name, age, email, contact_num, hire_date, dept_id, password_hash) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";    
			pst = con.prepareStatement(query);
			pst.setString(1, first_name);
			pst.setString(2, last_name);
			pst.setLong(3, age);
			pst.setString(4, email);
			pst.setLong(5, contact_number);
			pst.setDate(6, sqlDate);
			pst.setInt(7, dept_id);
			pst.setString(8, (last_name + age));

            pst.executeUpdate();
            pst.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Employee Added Succesfully");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
    public void update(int id) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "update employee set first_name = ?, last_name = ?, age = ?, email = ?, contact_num =?, hire_date = ?, dept_id = ?, password_hash = ? where emp_id =?";    
			pst = con.prepareStatement(query);
			pst.setString(1, first_name);
			pst.setString(2, last_name);
			pst.setLong(3, age);
			pst.setString(4, email);
			pst.setLong(5, contact_number);
			pst.setDate(6, sqlDate);
			pst.setInt(7, dept_id);
			pst.setString(8, (last_name + age));
			pst.setInt(9, id);

            pst.executeUpdate();
            pst.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Employee Updated Succesfully");
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
    public void delete(int id) {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "delete from employee where emp_id = ?";   
			pst = con.prepareStatement(query);
			pst.setInt(1, id);

            pst.executeUpdate();
            pst.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Employee Deleted Succesfully");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
}