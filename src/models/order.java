package models;

import models.product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import javax.swing.JOptionPane;

public class order {
    private int order_id;
    private int dept_id;
    private int supplier_id;
    private float amount;
    private double tax_amt;
    private double total;
    java.sql.Date order_date;
    private String mode;
    public ArrayList<product> prodList;
    private Connection con;
    PreparedStatement pst;
    java.sql.Date sqlDate;

    public order()
    {
    	prodList = new ArrayList<product>();
    	Date date=new Date();
        this.sqlDate = new java.sql.Date(date.getTime());  
        this.mode = "Online Payment"; 
    }
    
    public void insert()
    {
    	try {
    		
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query =  "INSERT INTO orders (dept_id, supplier_id, order_date, mode) VALUES (?, ?, ?, ?)"; 
			pst = con.prepareStatement(query);
			pst.setInt(1, dept_id);
			pst.setInt(2, supplier_id);
			pst.setDate(3, sqlDate);
			pst.setString(4, mode);
			pst.executeUpdate();		
			pst.close();
			
			query =  "select max(order_id) from orders"; 
			pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
            while(rs.next())
            {
            	order_id = rs.getInt("max(order_id)");
            }
            pst.close();
            con.close();
            
            addTrans();
            addProds();
//            JOptionPane.showMessageDialog(null, "Ordered Succesfully");
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    public void addProds()
    {
    	try {
    		String query;
    		
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			for(product i: prodList)
			{
				query = "INSERT INTO order_product (order_id, product_id, quantity, rate) VALUES (?, ?, ?, ?)";
				pst = con.prepareStatement(query);
				pst.setInt(1, order_id);
				pst.setInt(2, i.id);
				pst.setInt(3, i.qty);
				pst.setFloat(4, i.price);
				pst.executeUpdate();
		        pst.close();
		        
		        query = "update product set available_qty = available_qty + ? where product_id = ?";
		        pst = con.prepareStatement(query);
		        pst.setInt(1,  i.qty);
		        pst.setInt(2, i.id);
		        pst.executeUpdate();
		        pst.close();
			}   
	        con.close();
	        //JOptionPane.showMessageDialog(null, "Ordered Succesfully");
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }
    
    public void addTrans()
    {
    	try {
    		
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
	    	String query = "INSERT INTO transaction (transaction_id, amount, transaction_date, tax_amount, total_amt)VALUES (?, ?, ?, ?, ?)";    
			pst = con.prepareStatement(query);
			pst.setInt(1, order_id);
			pst.setFloat(2, amount);
			pst.setDate(3, sqlDate);
			pst.setDouble(4, tax_amt);
			pst.setDouble(5, total);
			pst.executeUpdate();
	        pst.close();
	        con.close();
	        JOptionPane.showMessageDialog(null, "Ordered Succesfully");
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	public void setAmount(float a) {
		this.amount = a;
	}
	
	public void setTax(double a) {
		this.tax_amt = a;
	}
	public void setTotal(double a) {
		this.total = a;
	}

	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
}
