package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class product {
	public int id;
	public String name;
	public int qty;
	public float price;
	public int supplier_id;
	private Connection con;
    PreparedStatement pst;
    java.sql.Date sqlDate;
    
    public product(int num)
    {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select * from product where product_id = ? ";    
			pst = con.prepareStatement(query);
			pst.setInt(1, num);
			ResultSet rs = pst.executeQuery();
			//System.out.println(rs);
			while(rs.next())
            {
				this.id = rs.getInt("product_id");
		        this.name = rs.getString("name");
		        //System.out.println(first_name);
		        this.price = rs.getFloat("price");
		        this.qty = rs.getInt("available_qty");
		        //Date date=new Date();
		        this.sqlDate = rs.getDate("fresh_date"); 
            }
	        con.close();
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}   
    }
    
    public product(int num, int q)
    {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "select * from product where product_id = ? ";    
			pst = con.prepareStatement(query);
			pst.setInt(1, num);
			ResultSet rs = pst.executeQuery();
			//System.out.println(rs);
			while(rs.next())
            {
				this.id = rs.getInt("product_id");
		        this.name = rs.getString("name");
		        //System.out.println(first_name);
		        this.price = rs.getFloat("price");
		        this.qty = q;
		        //Date date=new Date();
		        this.sqlDate = rs.getDate("fresh_date"); 
            }
	        con.close();
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		}   
    }
    
    public void update(int consumption)
    {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen","root","root");
			
			String query = "update product set available_qty = ? where product_id = ?";    
			pst = con.prepareStatement(query);
			pst.setInt(1, (qty - consumption));
			pst.setInt(2, id);
			pst.executeUpdate();
			pst.close();
	        con.close();
	        JOptionPane.showMessageDialog(null, "Updated Succesfully");
		}
		catch(Exception e) {
			System.out.print(e);
			JOptionPane.showMessageDialog(null, "Database Connection error", "Error", JOptionPane.ERROR_MESSAGE);
		} 
    }
}

